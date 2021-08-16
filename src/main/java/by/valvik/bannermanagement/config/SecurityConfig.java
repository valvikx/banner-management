package by.valvik.bannermanagement.config;

import by.valvik.bannermanagement.security.error.JwtAuthenticationEntryPoint;
import by.valvik.bannermanagement.security.filter.JwtFilter;
import by.valvik.bannermanagement.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final int ENCODE_STRENGTH = 12;

    private static final String AUTH_PATTERN = "/api/auth";

    private static final String API_PATTERN = "/api/**";

    private final JwtProvider jwtProvider;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final UserDetailsService adminDetailsService;

    public SecurityConfig(JwtProvider jwtProvider, JwtAuthenticationEntryPoint authenticationErrorHandler,
                          @Qualifier("adminDetailsService") UserDetailsService adminDetailsService) {

        this.jwtProvider = jwtProvider;

        this.jwtAuthenticationEntryPoint = authenticationErrorHandler;

        this.adminDetailsService = adminDetailsService;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()

                    .and()

                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)

                    .and()

                .authorizeRequests().antMatchers(AUTH_PATTERN).permitAll()
                                    .antMatchers(GET, API_PATTERN).permitAll()
                                    .anyRequest().authenticated()

                    .and()

                .sessionManagement().sessionCreationPolicy(STATELESS)

                    .and()

                .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(adminDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {

        return super.authenticationManagerBean();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(ENCODE_STRENGTH);

    }

}
