package by.valvik.bannermanagement.interceptor;

import by.valvik.bannermanagement.validation.IdValidator;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

public class PathIdHandlerInterceptor implements HandlerInterceptor {

    private static final String ID = "id";

    private final IdValidator idValidator;

    public PathIdHandlerInterceptor(IdValidator idValidator) {

        this.idValidator = idValidator;

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Map<String, String> attributes = (Map<String, String>) request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        String id = attributes.get(ID);

        return idValidator.isValid(id);

    }

}
