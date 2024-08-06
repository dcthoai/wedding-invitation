package com.wedding.exception;

import com.wedding.entity.ResponseJSON;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Value("${resources.static.url}")
    private String urlRedirect;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        if ("GET".equals(request.getMethod()))
            response.sendRedirect(urlRedirect + "login");
        else {
            String responseJson = ResponseJSON.toJson(false, authException.getMessage());
            response.setContentType("application/json");
            response.getWriter().write(responseJson);
        }
    }
}
