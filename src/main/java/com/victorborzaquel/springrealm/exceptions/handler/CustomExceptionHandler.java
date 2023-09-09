package com.victorborzaquel.springrealm.exceptions.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.victorborzaquel.springrealm.exceptions.base.AppException;
import com.victorborzaquel.springrealm.exceptions.dto.ResponseExceptionDTO;
import com.victorborzaquel.springrealm.exceptions.mapper.ExceptionMapper;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ResponseExceptionDTO> handleException(AppException e) {
    return new ResponseEntity<>(ExceptionMapper.INSTANCE.toResponse(e), new HttpHeaders(), e.getHttpStatus());
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
    AppException appException = new AppException(httpStatus, "Invalid params");

    return new ResponseEntity<>(ExceptionMapper.INSTANCE.toResponse(appException), new HttpHeaders(), httpStatus);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
        .collect(Collectors.toList());
    HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
    AppException appException = new AppException(errors, httpStatus, "Invalid params");

    return new ResponseEntity<>(ExceptionMapper.INSTANCE.toResponse(appException), new HttpHeaders(), httpStatus);
  }

}
