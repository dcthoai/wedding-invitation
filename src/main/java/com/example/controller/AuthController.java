package com.example.controller;

import com.example.dto.request.UserRequestDTO;
import com.example.entity.ResponseJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = "/login")
    public ModelAndView loginView() {
        return new ModelAndView("login");
    }

    @GetMapping(value = "/admin")
    public String admin() {
        return "Hello admin";
    }

    @GetMapping(value = "/")
    public String adminHello() {
        return "admin v2";
    }

    @PostMapping(value = "/api/auth/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO userRequest,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        /* Set user info in session when authenticated success */
        HttpSession session = request.getSession(true);
        session.setAttribute("isAuthenticated", true);
        session.setAttribute("username", userRequest.getUsername());

        return ResponseJSON.ok("Login success!");
    }

    @PostMapping(value = "/api/auth/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);

            /* Check authentication state in session */
            if (session != null && (Boolean) session.getAttribute("isAuthenticated")) {
                /* Cancel authentication info */
                SecurityContextHolder.getContext().setAuthentication(null);
                session.invalidate();

                return ResponseJSON.ok("Logout success!");
            }

            return ResponseJSON.badRequest("You was not logged in!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseJSON.badRequest("Logout error: " + e.getMessage());
        }
    }
}
