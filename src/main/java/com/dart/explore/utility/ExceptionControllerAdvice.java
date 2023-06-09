package com.dart.explore.utility;

import com.dart.explore.exception.DartExploreException;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    private static final Log logger = LogFactory.getLog(ExceptionControllerAdvice.class);

    private ResponseEntity<ErrorInfo> createErrorResponse(HttpStatus status, String message, Exception exception) {
        logger.error(message, exception);
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

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> validationHandler(Exception exception) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Error validating your input. Make sure your request is valid.", exception);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Error: " + ex.getMessage(), ex);
    }
}