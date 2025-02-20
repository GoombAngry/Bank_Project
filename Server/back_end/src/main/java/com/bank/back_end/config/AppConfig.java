package com.bank.back_end.config;

import com.bank.back_end.model.Cliente;
import com.bank.back_end.model.Token;
import com.bank.back_end.repository.ClienteRepository;
import com.bank.back_end.repository.TokenRepository;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AppConfig{
    @Autowired
    private ClienteRepository clienteRepository;

    public AppConfig() {
    }

    public AppConfig(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            Cliente c = this.clienteRepository.findUserByDni(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(c.getDni_cliente())
                    .password(c.getPassword())
                    .build();
        };
    }

    /**
     * - En este metodo se realiza la autenticacion con los datos obtenidos pasados por el usuario y
     * los que acreditan que es el (su password y dni que son obtenidos en el metodo userDetailsService() )
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        // Recibido los datos  pasados al UsernamePasswordAuthenticationToken() y los recogemos
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // Se busca si existe ese dni que proporcionamos y si existe guardamos tambien la password y posteriormente se comprueban los datos que pasamos con los de la base de datos para validar el login o no.
        authProvider.setPasswordEncoder(passwordEncoder()); // La forma en la que se esta encryptando las password
        return authProvider;
    }


    /**
     * - Este metodo se encargará de delegar la autenticación al authenticationProvider
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    // Para encryptar las password
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
