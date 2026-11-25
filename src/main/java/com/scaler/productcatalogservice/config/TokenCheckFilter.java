package com.scaler.productcatalogservice.config;

import com.scaler.productcatalogservice.dtos.AuthUserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenCheckFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate;
    private final String authServiceUrl;
    public TokenCheckFilter(RestTemplateBuilder restTemplateBuilder, @Value("${auth.service.url}")  String authServiceUrl) {
        this.restTemplate = restTemplateBuilder.build();
        this.authServiceUrl = authServiceUrl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("TokenCheckFilter runs");
        if(request.getMethod().equals("GET")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No bearer auth header found");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        AuthUserDto authUserDto;
        try{
            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", authHeader);
            headers.setBearerAuth(authHeader.substring("Bearer ".length()));

            ResponseEntity<AuthUserDto> authUserDtoResponseEntity = restTemplate.exchange(authServiceUrl+"/auth/validate", HttpMethod.GET, new HttpEntity<>(headers),AuthUserDto.class);
            authUserDto = authUserDtoResponseEntity.getBody();
            System.out.println("Email : " + authUserDto.getEmail());
        }catch (HttpClientErrorException e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        if(authUserDto == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        if(request.getMethod().equals("DELETE") && !authUserDto.getRole().equals("ADMIN")) {
            System.out.println("Role : " + authUserDto.getRole());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
