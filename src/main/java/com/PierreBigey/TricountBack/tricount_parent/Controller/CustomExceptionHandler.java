package com.PierreBigey.TricountBack.tricount_parent.Controller;

import com.PierreBigey.TricountBack.tricount_parent.Exception.ResourceNotFoundException;
import com.PierreBigey.TricountBack.tricount_parent.Exception.UserNotInGroupException;
import com.PierreBigey.TricountBack.tricount_parent.Utils.ErrorResponse;
import org.hibernate.PropertyValueException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserNotInGroupException.class)
    public ResponseEntity<ErrorResponse> handleUserNotInGroupException(UserNotInGroupException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"UserNotInGroupException",
                ex.getMessage(), "");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"ResourceNotFoundException",
                ex.getMessage(), "");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorResponse> handlePSQLException(PSQLException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"PSQLException state : "
                +ex.getSQLState(), ex.getMessage(),"");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ErrorResponse> handlePropertyValueException(PropertyValueException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"PropertyValueException", ex.getMessage(),ex.getPropertyName());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
