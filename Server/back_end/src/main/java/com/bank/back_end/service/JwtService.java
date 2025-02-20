package com.bank.back_end.service;

import com.bank.back_end.model.Cliente;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.accessTokenExpiration}")
    private long jwtTokenExpiration;

    @Value("${jwt.refreshTokenExpiration}")
    private long jwtRefreshTokenExpiration;


    public String generateToken(Cliente cliente) {
        return this.buildToken(cliente,this.jwtTokenExpiration);
    }

    public String generateRefreshToken(Cliente cliente) {
        return this.buildToken(cliente,this.jwtRefreshTokenExpiration);
    }

    private String buildToken(Cliente cliente,long expiration){
        /*
                return Jwts.builder()
                .id(String.valueOf(cliente.getId_cliente()))
                .claims(Map.of("name",cliente.getNombre()))
                .subject(cliente.getCorreo_electronico())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
         */
        return Jwts.builder()
                .id(String.valueOf(cliente.getId_cliente()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Este metodo verificara la integridad del token y extraera el email que contiene
     * @param token
     * @return Email Cliente
     */
    public String extractId_Client(String token) {
        Claims t = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return t.getId();
    }
    /*
    public String extractEmailClient(String token) {
        Claims t = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return t.getSubject();
    }
    */
    private Date extractExpiration(String token){
        Claims t = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return t.getExpiration();
    }
    public boolean isValidToken(String token,Cliente cliente){
        return ((this.extractId_Client(token).equals(String.valueOf(cliente.getId_cliente()))) && (!isTokenExpired(token)));
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
}
