package com.wedding.exception;

import com.wedding.entity.ResponseJSON;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.security.SignatureException;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<?> exceptionUserNotFoundHandler(UsernameNotFoundException exception) {
        return ResponseJSON.badRequest(exception.getMessage());
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<?> exceptionBadCredentialsHandler(BadCredentialsException exception) {
        return ResponseJSON.badRequest(exception.getMessage() + ". Invalid username or password!");
    }

    @ExceptionHandler(value = LockedException.class)
    public ResponseEntity<?> exceptionLockedHandler(LockedException exception) {
        return ResponseJSON.accessDenied(exception.getMessage() + ". Your account has been locked!");
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<?> exceptionAuthenticationHandler(AuthenticationException exception) {
        return ResponseJSON.badRequest(exception.getMessage() + ". Server error when authenticate!");
    }

    @ExceptionHandler(value = AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<?> exceptionAuthenticationCredentialsHandler(
            AuthenticationCredentialsNotFoundException exception) {
        return ResponseJSON.badRequest(exception.getMessage());
    }

    @ExceptionHandler(value = SignatureException.class)
    public ResponseEntity<?> exceptionSignatureHandler(SignatureException exception) {
        return ResponseJSON.badRequest("Invalid token: " + exception.getMessage());
    }
}
