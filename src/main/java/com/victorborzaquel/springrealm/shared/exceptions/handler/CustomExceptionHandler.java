package com.victorborzaquel.springrealm.shared.exceptions.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.victorborzaquel.springrealm.shared.exceptions.base.AppException;
import com.victorborzaquel.springrealm.shared.exceptions.dto.ResponseExceptionDTO;
import com.victorborzaquel.springrealm.shared.exceptions.mapper.ExceptionMapper;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ResponseExceptionDTO> handleException(AppException e) {
    return new ResponseEntity<>(ExceptionMapper.toResponse(e), new HttpHeaders(), e.getHttpStatus());
  }

  @ExceptionHandler(HttpMessageConversionException.class)
  public ResponseEntity<ResponseExceptionDTO> handleHttpMessageConversionException(HttpMessageConversionException ex) {
    String errorMessage = "Error in JSON conversion";
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    AppException appException = new AppException(List.of(ex.getMessage()), httpStatus, errorMessage);

    return new ResponseEntity<>(ExceptionMapper.toResponse(appException), new HttpHeaders(), httpStatus);
  }

  @ExceptionHandler(InvalidDataAccessApiUsageException.class)
  public ResponseEntity<ResponseExceptionDTO> handleHttpMessageConversionException(
      InvalidDataAccessApiUsageException ex) {
    String errorMessage = "Error in database access";
    HttpStatus httpStatus = HttpStatus.CONFLICT;
    AppException appException = new AppException(List.of(ex.getMessage()), httpStatus, errorMessage);

    return new ResponseEntity<>(ExceptionMapper.toResponse(appException), new HttpHeaders(), httpStatus);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
    AppException appException = new AppException(httpStatus, "Invalid params");

    return new ResponseEntity<>(ExceptionMapper.toResponse(appException), new HttpHeaders(), httpStatus);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatusCode status, WebRequest request) {
    List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
        .collect(Collectors.toList());
    HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
    AppException appException = new AppException(errors, httpStatus, "Invalid params");

    return new ResponseEntity<>(ExceptionMapper.toResponse(appException), new HttpHeaders(), httpStatus);
  }

}
