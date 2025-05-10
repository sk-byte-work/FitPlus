package com.example.fitplus;

import com.example.fitplus.exceptions.FitPlusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FitPlusException.class)
    public ResponseEntity<Map> handleFitPlusException(FitPlusException exception) throws Exception {
        Map errorMap = ApplicationUtil.getResponseMap(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}
