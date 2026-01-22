package com.cinema.carrito.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList; // Para autoridades vacías por ahora

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                logger.warn("Invalid JWT token", e);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Validar la firma y la expiración del token
            if (jwtUtil.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, new ArrayList<>()); // Sin autoridades por ahora
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        String method = request.getMethod();

        logger.info("Checking path: {} with method: {}", path, method);

        // Excluir solicitudes GET de la validación JWT
        if (HttpMethod.GET.matches(method)) {
            return true;
        }

        // Excluir solicitudes POST y PUT específicas de la validación JWT
            if (HttpMethod.POST.matches(method)) {
                if ("/purchase/create-pending".equals(path)) {
                    return true;
                }
            } else if (HttpMethod.PUT.matches(method)) {
                if ("/purchase/editStatus/status".equals(path)) {
                    return true;
                }
            }
        return false;
    }
}
