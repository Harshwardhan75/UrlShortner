package com.urlshortner.configuration.JWT;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JWTHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String token = null;

        String auth = request.getHeader("Authorization");

        if(auth!=null && auth.startsWith("Bearer")){
            token = auth.substring(7);

            try{
                username = jwtHelper.getUsernameFromToken(token);
            } catch (MalformedJwtException e) {
                logger.info("Malformed JWT Token: {}",e.getMessage());
            } catch (ExpiredJwtException e) {
                logger.info("Expired JWT Token: {}",e.getMessage());
            }
            catch (IllegalArgumentException e){
                logger.info("Illegal JWT Token: {}",e.getMessage());
            } catch (Exception e) {
                logger.info("Some Exception Occurred: {}",e.getMessage());
            }
        }

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            boolean validateToken = jwtHelper.validateToken(token,userDetails);

            if(validateToken){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }else{
            logger.info("Validation Failed");
        }

        filterChain.doFilter(request,response);
    }
}
