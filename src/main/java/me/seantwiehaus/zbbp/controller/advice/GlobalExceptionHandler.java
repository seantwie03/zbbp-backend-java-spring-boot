package me.seantwiehaus.zbbp.controller.advice;

import me.seantwiehaus.zbbp.exception.InternalServerException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URISyntaxException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(URISyntaxException.class)
    protected InternalServerException handleURISyntaxException(URISyntaxException uriSyntaxException) {
        return new InternalServerException("Unable to create URI for: " + uriSyntaxException.getInput()
                + ". Reason: " + uriSyntaxException.getReason()
                + ". Message: " + uriSyntaxException.getMessage());
    }
}
