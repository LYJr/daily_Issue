package com.example.daily_issue.checklist.common.advice;

import com.example.daily_issue.checklist.common.exception.ObjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ObjectNotFoundControllerAdvice {
    @ExceptionHandler(ObjectNotFoundException.class)

    public ResponseEntity objectNotFoundException() {
        return ResponseEntity.notFound().build();
    }
}
