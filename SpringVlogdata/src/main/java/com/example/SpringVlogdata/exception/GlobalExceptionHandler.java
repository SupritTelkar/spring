package com.example.SpringVlogdata.exception;

import com.example.SpringVlogdata.Dto.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice  //to handle exception globally
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    //ResponseEntityExceptionHandler refer java bean validation notes
    //handle sepecific exception :-BlogAPi exception and resource not found exception
    //handle global exception
    @ExceptionHandler(ResourceNotExcep.class) //secific exception handling
    public ResponseEntity<ErrorDetails> handleResourceNotFound(ResourceNotExcep excep, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),excep.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class) //specific exception handling
    public ResponseEntity<ErrorDetails> handleBlogApiFound(BlogApiException excep, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),excep.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception excep, WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),excep.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
