package com.example.sigo.admin.config;

import com.example.sigo.admin.model.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Component
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    @Value("${jwt.time.expiration.ms}")
    private long expirationTime;

    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    public LoginFilter(@Lazy AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        logger.info("Iniciando login");

        try {
            ServletInputStream inputStream = request.getInputStream();

            UserEntity user = new ObjectMapper().readValue(inputStream, UserEntity.class);

            logger.info(String.format("Usuario: %s",  user.getUsername()));

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            Collections.emptyList()
                    )
            );
        } catch (IOException e) {
            logger.info(String.format("Erro ao ler request de login %s", e.getMessage()));
            throw new IllegalStateException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {

        response.addHeader(HttpHeaders.AUTHORIZATION, tokenPrefix + " " + createToken(authentication));
    }

    public String createToken(Authentication authentication) {

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim("authorities", getAuthority(authentication.getAuthorities()))
                .signWith(key)
                .compact();
    }

    private String getAuthority(Collection<? extends GrantedAuthority> authorities) {

        return authorities.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User must have a role"))
                .getAuthority();
    }
}
