package ru.job4j.accidents.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource ds;

    /**
     * Метод configure(auth) содержит описание, как искать пользователей.
     * У каждого пользователя есть роль. По роли мы определяем,
     * что пользователь может делать.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(ds)
                .usersByUsernameQuery(
                        "select username, password, enabled "
                        + "from users "
                        + "where username = ?")
                .authoritiesByUsernameQuery(
                        " select u.username, a.authority "
                                + "from authorities as a, users as u "
                                + "where u.username = ? and u.authority_id = a.id");
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
