package com.project.Precision_pros.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.project.Precision_pros.security.jwt.AuthEntryPointJwt;
import com.project.Precision_pros.security.jwt.AuthTokenFilter;
import com.project.Precision_pros.security.jwt.CustomAccessDeniedHandler;
import com.project.Precision_pros.security.services.UserDetailsServiceImpl;

import java.util.Arrays; // Ensure this import is present

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;
  
  @Autowired
  private CustomAccessDeniedHandler customAccessDeniedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }
  
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
	http.csrf(csrf -> csrf.disable())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler).accessDeniedHandler(customAccessDeniedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> 
          auth.requestMatchers("/api/auth/**","/ws/**","/api/chat/rooms/**").permitAll()
              .anyRequest().authenticated()
        );
    
    // This line MUST be uncommented and active for CORS to work
    http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      
      // >>>>>> THIS IS THE ABSOLUTELY CRITICAL FIX: EACH ORIGIN IS A SEPARATE STRING LITERAL <<<<<<
      // Each URL must be enclosed in its own double quotes and separated by commas.
      configuration.setAllowedOrigins(Arrays.asList(
          "http://localhost:4200",     // If your main frontend runs here
          "http://127.0.0.1:4200",     // If it uses the IP variant
          "http://localhost:5500",     // Live Server default (localhost)
          "http://127.0.0.1:5500",     // Live Server default (IP variant)
          "http://localhost:5501",     // Live Server second instance (localhost)
          "http://127.0.0.1:5501"      // Live Server second instance (IP variant)
      ));
      // >>>>>> END CRITICAL FIX <<<<<<
      
      configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")); 
      configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With", "remember-me"));
      configuration.setAllowCredentials(true); 
      configuration.setMaxAge(3600L); 
      
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration); // Apply this CORS config to all paths
      return source;
  }
}