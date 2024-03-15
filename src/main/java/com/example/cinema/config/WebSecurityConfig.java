package com.example.cinema.config;

import com.example.cinema.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebMvc
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers(HttpMethod.POST, "/user/register", "/user/confirm-register", "/user/login", "/user/forgot-password", "/user/confirm-new-password","payment")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET,"movie/top-movie","movie/cinema-movie","movie/all","cinema/get-by-schedule-movie","room/get-by-schedule-movie","schedule/get-scheduledto","food/get-fooddto","bill/bill-create","vnpay-payment")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/admin/get/all-user","/admin/get/userbyid","admin/food-bestsell7day","admin/cinema-revenue").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/admin/cinema/create","/admin/room/create","admin/food/create","admin/seat/create","admin/movie/create","admin/schedule-create").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/admin/cinema/update","/admin/room/update","admin/food/update","admin/seat/update","admin/movie/update","admin/schedule-update").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/admin/cinema/delete","/admin/room/delete","admin/food/delete","admin/seat/delete","admin/movie/delete","admin/schedule-delete").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/user/change-password").hasAnyRole("ADMIN", "USER")
                                .anyRequest().authenticated()

                );
        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return http.build();
    }
}
