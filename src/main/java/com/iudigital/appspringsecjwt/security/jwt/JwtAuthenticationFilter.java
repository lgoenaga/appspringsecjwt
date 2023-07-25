package com.iudigital.appspringsecjwt.security.jwt;

import com.iudigital.appspringsecjwt.service.implement.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
   private final UserServiceImpl userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String username;

        if (token==null){
            filterChain.doFilter(request,response);
          return;
        }

        username = jwtService.getUsernameFromToken(token);

        if (username!=null && SecurityContextHolder.getContext().getAuthentication() ==null){
            final var userDetails = userService.loadUserByUsername(username);

            if (jwtService.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }


        filterChain.doFilter(request,response);

    }

    private String getTokenFromRequest(HttpServletRequest request) throws NullPointerException, HttpClientErrorException.Forbidden {

            final String authHeader = request.getHeader("Authorization");

            if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){

                return authHeader.substring(7);
            }else {
                return null;
            }
    }
}
