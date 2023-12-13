package com.potapova.helpdesk.security.filter;

import com.potapova.helpdesk.security.service.CustomUserDetailService;
import com.potapova.helpdesk.security.service.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = jwtUtils.getTokenFromHttpRequest(request);
        if (token.isPresent() && jwtUtils.validateToken(token.get())) {
            Optional<String> login = jwtUtils.getLoginFromJwt((token.get()));
            if (login.isPresent()) {
                UserDetails userDetails = customUserDetailService.loadUserByUsername(login.get());
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        }

        filterChain.doFilter(request, response);
    }
}
