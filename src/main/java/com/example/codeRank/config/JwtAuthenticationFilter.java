package com.example.codeRank.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * Skip JWT validation for public endpoints
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // Debug logging - see what path is being requested
        System.out.println("🔍 JwtFilter - Checking path: " + path);

        // Skip filter for registration and login endpoints
        boolean shouldSkip = path.equals("/api/auth/register") ||
                             path.equals("/api/auth/login");

        System.out.println("🔍 JwtFilter - Should skip filter? " + shouldSkip);

        return shouldSkip;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("⚠️ JwtFilter - doFilterInternal() CALLED for path: " + request.getRequestURI());

        try {
            String jwt = request.getHeader("Authorization");

            if (jwt == null || jwt.trim().isEmpty()){
                System.out.println("❌ JwtFilter - No Authorization header found");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Missing Authorization header");
                return;
            }

            System.out.println("🔑 JwtFilter - Token received: " + jwt.substring(0, Math.min(jwt.length(), 20)) + "...");

            Claims claims = tokenProvider.validateToken(jwt);
            System.out.println("✅ JwtFilter - Token valid, Claims: " + claims);
            System.out.println("👤 JwtFilter - Username from token: " + claims.getSubject());

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                new ArrayList<>()
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("✅ JwtFilter - Authentication set in SecurityContext");

             filterChain.doFilter(request, response);

        } catch (Exception ex) {
            System.out.println("❌ JwtFilter - Exception occurred: " + ex.getMessage());
            logger.error("Could not set user authentication in security context", ex);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token: " + ex.getMessage());
        }
    }
}

