package com.cinema.security.service.impl;

import com.cinema.security.entity.User;
import com.cinema.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${SECURITY_SECRET}")
    private String secretKey;

    @Value("${api.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${api.security.jwt.refresh-token-expiration}")
    private long refreshExpiration;


    public String extractUsername(String token){
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    public String generateToken (User user){

        return buildToken(user, jwtExpiration);
    }

    public String generateRefreshToken(User user){

        return buildToken(user, refreshExpiration);
    }

    private String buildToken (User user, long expiration){

        return Jwts.builder()
                .id(user.getId().toString())
                .claims(Map.of("name", user.getUsername()))
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();

    }

    public boolean isTokenValid (String token, UserDetails userDetails){
        String username = extractUsername(token);
        return(username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired (String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }

    /**
     * Obtiene la clave de firma utilizada para verificar la validez del token JWT.
     *
     * @return Clave de firma.
     */
    private SecretKey getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
