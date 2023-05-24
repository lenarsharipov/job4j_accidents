package ru.job4j.accidents.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Метод configure(auth) содержит описание, как искать пользователей.
     * В этом примере мы загружаем их в память.
     * У каждого пользователя есть роль. По роли мы определяем,
     * что пользователь может делать.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("user").password(passwordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("123456"))
                .roles("USER", "ADMIN");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Метод configure(http) содержит описание доступов и
     * конфигурирование страницы входа в приложение.
     *
     * - Ссылки, которые доступны всем:
     *  .antMatchers("/login")
     *  .permitAll()
     *
     * - Ссылки доступны только пользователем с ролями ADMIN, USER.
     *  .antMatchers("/**")
     *  .hasAnyRole("ADMIN", "USER")
     *
     * - Настройка формы авторизации.
     *  .formLogin()
     *  .loginPage("/login")
     *  .defaultSuccessUrl("/")
     *  .failureUrl("/login?error=true")
     *  .permitAll()
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/register")
                .permitAll()
                .antMatchers("/**")
                .hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .csrf()
                .disable();
    }

}
