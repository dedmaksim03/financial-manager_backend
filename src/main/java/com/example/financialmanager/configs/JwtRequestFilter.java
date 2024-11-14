package com.example.financialmanager.configs;

import com.example.financialmanager.entities.Role;
import com.example.financialmanager.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String userName = null;
        String jwtToken = null;

        if (authHeader != null && authHeader.startsWith("Bearer ") && !authHeader.equals("Bearer null")){
            System.out.println(authHeader);
            jwtToken = authHeader.substring(7);
            try{
                userName = jwtTokenUtils.getUserName(jwtToken);
            } catch (ExpiredJwtException e){
                log.debug("Время жизни токена вышло");
            } catch (SignatureException e) {
                log.debug("Неверная подпись");
            }
        }


        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            List<String> roles = new ArrayList<>();
            roles.add(jwtTokenUtils.getRoles(jwtToken));
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userName,
                    null,
                    roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
            System.out.println(userName + jwtTokenUtils.getRoles(jwtToken));
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }
}
