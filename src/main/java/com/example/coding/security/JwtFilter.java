package com.example.coding.security;

import com.example.coding.service.JwtService;
import com.example.coding.service.TokenBlackListService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
@Component
public class JwtFilter extends OncePerRequestFilter {
    private  final JwtService jwtService;
    private final TokenBlackListService tokenBlackListService;
    public JwtFilter(JwtService jwtService, TokenBlackListService tokenBlackListService) {
        this.jwtService = jwtService;
        this.tokenBlackListService = tokenBlackListService;
    }
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
                                    throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        if(authHeader==null||!authHeader.startsWith("Bearer ")){filterChain.doFilter(request,response);
        return;}
        String token=authHeader.substring(7);
        if(tokenBlackListService.isBlacklisted(token)){
            filterChain.doFilter(request,response);
            return;
        }
      try{
         String username=jwtService.extractUsername(token);
         if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null
                 && jwtService.isValid(token,username)){
             String role=jwtService.extractRoleFromToken(token);

         var authentication=new UsernamePasswordAuthenticationToken(username,
                 null,
                List.of(new SimpleGrantedAuthority("ROLE_"+role)));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
   catch (Exception e) {

 }
filterChain.doFilter(request,response);

    }
}
