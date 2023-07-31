package com.example.damnbreadback.config;

import com.example.damnbreadback.exception.CustomAccessDeniedHandler;
import com.example.damnbreadback.exception.EntryPointUnauthorizedHandler;
import com.example.damnbreadback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    @Value("${JWT.SECRET}")
    private String secretKey;

    EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(HttpMethod.POST, "/login", "/signup", "/signup/**", "/damnrank/detail");
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf()
                .ignoringRequestMatchers("/damnrank/detail")
                .and()
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/damnrank").permitAll()
//                        .requestMatchers("/mypage").hasAnyRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                );


//        http.authorizeHttpRequests((requests) -> {
//            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.requestMatchers(HttpMethod.POST,"/mypage").hasAnyRole("USER")
//                    .requestMatchers(HttpMethod.GET,"/**").hasAnyRole("USER").anyRequest()).authenticated();
//        });
        http.formLogin(Customizer.withDefaults());
        http.addFilterBefore(new JwtFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class);

        return (SecurityFilterChain)http.build();

    }


}