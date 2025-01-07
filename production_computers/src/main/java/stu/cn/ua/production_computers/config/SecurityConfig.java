package stu.cn.ua.production_computers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Дозволяємо доступ без авторизації
                        .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
                        // Доступ для ролі ADMIN
                        .requestMatchers(
                                "/clients/**",
                                "/components/**",
                                "/orders/add",
                                "/orders/edit/**",
                                "/order-items/add",
                                "/order-items/edit/**"
                        ).hasRole("ADMIN")
                        // Доступ для ролей USER і ADMIN
                        .requestMatchers(
                                "/orders",
                                "/order-items",
                                "/components",
                                "/clients"
                        ).hasAnyRole("USER", "ADMIN")
                        // Інші запити вимагають аутентифікації
                        .anyRequest().authenticated()
                )
                // Налаштування форми входу
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/orders", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                // Налаштування виходу з облікового запису
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                // Налаштування обробки виключень
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            String redirectUrl = "/login?unauthorized=true";
                            response.sendRedirect(redirectUrl);
                        })
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
