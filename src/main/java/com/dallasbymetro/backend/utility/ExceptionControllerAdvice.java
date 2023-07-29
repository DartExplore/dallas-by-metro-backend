package com.dallasbymetro.backend.utility;

import com.dallasbymetro.backend.exception.DartExploreException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    private static final Log logger = LogFactory.getLog(ExceptionControllerAdvice.class);

    private ResponseEntity<ErrorInfo> createErrorResponse(HttpStatus status, String message, Exception exception,
                                                          boolean logException) {
        if(logException)
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
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong, please check the log.",
                exception, true);
    }

    @ExceptionHandler(DartExploreException.class)
    public ResponseEntity<ErrorInfo> dartExploreExceptionHandler(DartExploreException exception) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), exception, false);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> methodArgumentHandler(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String message = String.join(", ", errorMessages);
        return createErrorResponse(HttpStatus.BAD_REQUEST, message,
                new Exception(String.join(", ", errorMessages)), false);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorInfo> constraintViolationHandler(ConstraintViolationException exception) {
        List<String> errorMessages = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        String message = String.join(", ", errorMessages);
        return createErrorResponse(HttpStatus.BAD_REQUEST, message, exception, false);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorInfo> missingRequestParameterHandler(MissingServletRequestParameterException exception) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), exception, false);
    }
}