package spring6restmvc.springframework.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spring6restmvc.springframework.exceptions.NotFoundException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    ResponseEntity handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }
}
