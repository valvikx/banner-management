package by.valvik.bannermanagement.security.error;

import by.valvik.bannermanagement.error.SecurityMessage;
import by.valvik.bannermanagement.message.MessageProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String AUTH_FAILED = "auth.failed";

    private static final String AUTH_REQUIRED = "auth.required";

    private final ObjectMapper objectMapper;

    private final MessageProvider messageProvider;

    private final Map<Class<? extends AuthenticationException>, String> messages =
            Map.of(BadCredentialsException.class, AUTH_FAILED,
                   InsufficientAuthenticationException.class, AUTH_REQUIRED);

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper, MessageProvider messageProvider) {

        this.objectMapper = objectMapper;

        this.messageProvider = messageProvider;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType(APPLICATION_JSON_VALUE);

        response.setStatus(SC_UNAUTHORIZED);

        String code = messages.get(authException.getClass());

        String message = messageProvider.getMessage(code);

        objectMapper.writeValue(response.getOutputStream(), new SecurityMessage(message));

    }

}
