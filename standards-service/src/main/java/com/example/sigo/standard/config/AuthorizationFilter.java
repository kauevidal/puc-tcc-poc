package com.example.sigo.standard.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Component
public class AuthorizationFilter extends BasicAuthenticationFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    private final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    public AuthorizationFilter(@Lazy AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Authentication authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    public Authentication getAuthentication(HttpServletRequest request) {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String encodedSecret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));

        if (token != null) {
            try {
                Jws<Claims> claimsJws = Jwts.parserBuilder()
                        .setSigningKey(encodedSecret)
                        .build()
                        .parseClaimsJws(token.replace(tokenPrefix, ""));

                String user = claimsJws
                        .getBody()
                        .getSubject();

                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, getAuthorities(claimsJws));
                } else {
                    logger.info("Não foi possível autenticar o usuário");
                }
            } catch (JwtException e) {
                logger.info(String.format("Failed authentication: %s", e.getMessage()));
                throw new AuthenticationServiceException("Não foi possível validar o token");
            }
        }
        return null;
    }

    private List<GrantedAuthority> getAuthorities(Jws<Claims> claimsJws) {
        String authorities = claimsJws.getBody().get("authorities", String.class);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    }
}
