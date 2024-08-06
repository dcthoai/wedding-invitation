package com.wedding.exception;

import com.wedding.entity.ResponseJSON;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> exceptionRuntimeHandler(RuntimeException exception) {
        return ResponseJSON.badRequest(exception.getMessage());
    }

    @ExceptionHandler(value = ServletException.class)
    public ResponseEntity<?> exceptionServletHandler(ServletException exception) {
        return ResponseJSON.badRequest(exception.getMessage());
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<?> exceptionIOHandler(IOException exception) {
        return ResponseJSON.badRequest(exception.getMessage());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<?> handleMissingServletRequestPartException(
            MissingServletRequestPartException ex) {
        return ResponseJSON.badRequest("Missing request part: " + ex.getRequestPartName());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseJSON.badRequest("Entity not found: " + ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        return ResponseJSON.badRequest("Argument type mismatch: " + ex.getName());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        return ResponseJSON.badRequest("HTTP method not supported: " + ex.getMethod());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(
            HttpMediaTypeNotSupportedException ex) {
        return ResponseJSON.badRequest("HTTP media type not supported: " + ex.getContentType());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        return ResponseJSON.serverError("An error occurred: " + ex.getMessage());
    }
}
