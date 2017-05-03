package com.appsay.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by appsay02 on 03-05-2017.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
   /* @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e) {
        return e.getMessage();
    }*/

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(),3000,ex.getMessage(),ex.getMessage(),null);
        ResponseEntity<ErrorResponse> responseEntity= new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
        return responseEntity;
    }

}

