package com.dallasbymetro.backend.utility;

import com.dallasbymetro.backend.exception.DartExploreException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    private static final Log logger = LogFactory.getLog(ExceptionControllerAdvice.class);

    private ResponseEntity<ErrorInfo> createErrorResponse(HttpStatus status, String message, Exception exception) {
        if(status.is5xxServerError())
            logger.error(message, exception);
        else
            logger.error(message);
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage(message);
        errorInfo.setErrorCode(status.value());
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong, please check the log.", exception);
    }

    @ExceptionHandler(DartExploreException.class)
    public ResponseEntity<ErrorInfo> dartExploreExceptionHandler(DartExploreException exception) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> methodArgumentHandler(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String message = String.join(", ", errorMessages);
        return createErrorResponse(HttpStatus.BAD_REQUEST, message,
                new Exception(String.join(", ", errorMessages)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> constraintViolationHandler(ConstraintViolationException exception) {
        List<String> errorMessages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        String message = String.join(", ", errorMessages);
        return createErrorResponse(HttpStatus.BAD_REQUEST, message,
                exception);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorInfo> notReadableHandler(HttpMessageNotReadableException exception) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
    }
}