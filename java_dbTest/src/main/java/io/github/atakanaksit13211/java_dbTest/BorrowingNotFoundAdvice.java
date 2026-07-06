package io.github.atakanaksit13211.java_dbTest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class BorrowingNotFoundAdvice {

    @ExceptionHandler(BorrowingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String borrowingNotFoundHandler(BorrowingNotFoundException ex) {
        return ex.getMessage();
    }
}
