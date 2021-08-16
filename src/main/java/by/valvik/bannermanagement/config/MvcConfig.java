package by.valvik.bannermanagement.config;

import by.valvik.bannermanagement.interceptor.PathIdHandlerInterceptor;
import by.valvik.bannermanagement.validation.IdValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static java.util.Collections.singletonList;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String API_PATTERN = "/api/*/{id}";

    private static final String CORS_PATTERN = "/api/**";

    private static final String ALLOWED_HEADER = "*";

    private static final String ALLOWED_METHOD = "*";

    @Value("${cors.allowed-origin}")
    private String allowedOrigin;

    private final IdValidator idValidator;

    public MvcConfig(IdValidator idValidator) {

        this.idValidator = idValidator;

    }

    @Bean
    HandlerInterceptor pathVariableInterceptor() {

        return new PathIdHandlerInterceptor(idValidator);

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(pathVariableInterceptor())
                .addPathPatterns(API_PATTERN);

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(singletonList(allowedOrigin));

        configuration.addAllowedHeader(ALLOWED_HEADER);

        configuration.addAllowedMethod(ALLOWED_METHOD);

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();

        configurationSource.registerCorsConfiguration(CORS_PATTERN, configuration);

        return configurationSource;

    }

}
