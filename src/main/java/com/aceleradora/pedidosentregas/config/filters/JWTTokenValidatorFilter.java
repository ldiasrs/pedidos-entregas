package com.aceleradora.pedidosentregas.config.filters;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aceleradora.pedidosentregas.config.filters.JWTTokenGeneratorFilter;
import com.aceleradora.pedidosentregas.controller.PathMappings;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import static com.aceleradora.pedidosentregas.config.filters.JWTTokenGeneratorFilter.JWT_KEY_THAT_SHOULD_BE_ON_ENV;
import static com.aceleradora.pedidosentregas.controller.PathMappings.getFullPath;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String jwt = request.getHeader(JWTTokenGeneratorFilter.JWT_HEADER);
        if (null != jwt) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(
                        JWT_KEY_THAT_SHOULD_BE_ON_ENV.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");
                Authentication auth = new UsernamePasswordAuthenticationToken(username,null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Token received!", e);
            }
        }
        chain.doFilter(request, response);
    }


    /**
     * Vai validar o filter para todos os paths menos /auth
     */
    @Override protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals(getFullPath(PathMappings.AUTH_MAPPING));
    }


}
