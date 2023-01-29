package me.seantwiehaus.zbbp.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import me.seantwiehaus.zbbp.dto.response.ExceptionResponse;
import me.seantwiehaus.zbbp.exception.BadRequestException;
import me.seantwiehaus.zbbp.exception.InternalServerException;
import me.seantwiehaus.zbbp.exception.ResourceConflictException;
import me.seantwiehaus.zbbp.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URISyntaxException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(URISyntaxException.class)
  protected ResponseEntity<ExceptionResponse> handleURISyntaxException(URISyntaxException exception,
                                                                       HttpServletRequest request) {
    String message = "Unable to create URI for: " + exception.getInput()
            + ". Reason: " + exception.getReason()
            + ". Message: " + exception.getMessage();
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ExceptionResponse(500, message, formatFullPath(request)));
  }

  @ExceptionHandler(InternalServerException.class)
  protected ResponseEntity<ExceptionResponse> handleInternalServerException(InternalServerException exception,
                                                                            HttpServletRequest request) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ExceptionResponse(500, exception.getMessage(), formatFullPath(request)));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  protected ResponseEntity<ExceptionResponse> handleNotFoundException(ResourceNotFoundException exception,
                                                                      HttpServletRequest request) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ExceptionResponse(404, exception.getMessage(), formatFullPath(request)));
  }

  @ExceptionHandler(ResourceConflictException.class)
  protected ResponseEntity<ExceptionResponse> handleResourceConflictException(ResourceConflictException exception,
                                                                              HttpServletRequest request) {
    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ExceptionResponse(409, exception.getMessage(), formatFullPath(request)));
  }

  @ExceptionHandler(BadRequestException.class)
  protected ResponseEntity<ExceptionResponse> handleResourceConflictException(BadRequestException exception,
                                                                              HttpServletRequest request) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ExceptionResponse(400, exception.getMessage(), formatFullPath(request)));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
          MethodArgumentNotValidException exception, HttpServletRequest request) {
    String message = exception.getAllErrors()
            .stream()
            .map(ObjectError::toString)
            .collect(Collectors.joining(", "));
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ExceptionResponse(400, message, formatFullPath(request)));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<ExceptionResponse> handleConstraintViolationException(
          ConstraintViolationException exception, HttpServletRequest request) {
    String message = exception.getConstraintViolations()
            .stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ExceptionResponse(400, message, formatFullPath(request)));
  }

  private String formatFullPath(HttpServletRequest request) {
    if (request.getQueryString() == null) {
      return request.getContextPath() + request.getServletPath();
    }
    return request.getContextPath() + request.getServletPath() + "?" + request.getQueryString();
  }
}
