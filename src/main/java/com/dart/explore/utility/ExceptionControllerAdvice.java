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
import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    private static Log logger = LogFactory.getLog(ExceptionControllerAdvice.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception){
        logger.error(exception.getMessage(), exception);
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage("Something went wrong, please check the log.");
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DartExploreException.class)
    public ResponseEntity<ErrorInfo> dartExploreExceptionHandler(DartExploreException exception){
        logger.error(exception.getMessage(), exception);
        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage(exception.getMessage());
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        error.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> validationHandler(Exception exception){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrorMessage("Error validating your input. Make sure your request is valid.");
        errorInfo.setErrorCode(HttpStatus.BAD_REQUEST.value());
        errorInfo.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
