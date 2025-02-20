package com.bank.back_end.config;

import com.bank.back_end.model.Cliente;
import com.bank.back_end.model.Token;
import com.bank.back_end.repository.ClienteRepository;
import com.bank.back_end.repository.TokenRepository;
import com.bank.back_end.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
            ) throws ServletException, IOException {


        if(request.getServletPath().contains("/auth")){
            filterChain.doFilter(request,response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);
        if(SecurityContextHolder.getContext().getAuthentication() != null){
            return;
        }
        Token tokenSearch = this.tokenRepository.searchTokenByValueToken(token).orElse(null);
        if(tokenSearch == null || tokenSearch.isExpired() || tokenSearch.isRevoked()){
            filterChain.doFilter(request,response);
            return;

        }
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(tokenSearch.getCliente().getDni_cliente());
        Optional<Cliente> clienteSearch = this.clienteRepository.findUserByDni(userDetails.getUsername());
        if(clienteSearch.isEmpty()){
            filterChain.doFilter(request,response);
            return;
        }

        boolean isValidToken = this.jwtService.isValidToken(token,clienteSearch.get());
        if(!isValidToken){
            return;
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request,response);
    }
}
