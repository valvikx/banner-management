package by.valvik.bannermanagement.security.filter;

import by.valvik.bannermanagement.security.jwt.JwtProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class JwtFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {

        this.jwtProvider = jwtProvider;

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        Optional<String> jwt = jwtProvider.resolveJwt(httpServletRequest);

        jwt.ifPresent(t -> {

            if (jwtProvider.isJwtValid(t)) {

                Authentication authentication = jwtProvider.getAuthentication(t);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {

                SecurityContextHolder.clearContext();

            }

        });

        chain.doFilter(request, response);

    }

}
