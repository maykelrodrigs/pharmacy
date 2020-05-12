package io.github.maykelrodrigs.pharmacy.controller.exception;

import io.github.maykelrodrigs.pharmacy.service.exception.BusinessException;
import io.github.maykelrodrigs.pharmacy.service.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({BusinessException.class})
    public final ResponseEntity<Object> bussinessHandleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        return httpStatusCode(ex, headers, request);
    }

    protected ResponseEntity<Object> httpStatusCode(Exception ex, HttpHeaders headers, WebRequest request) {
        if (ex instanceof EntityNotFoundException) {
            return handleExceptionInternal(ex, null, headers, HttpStatus.NOT_FOUND, request);
        } else if (ex instanceof BusinessException) {
            return handleExceptionInternal(ex, null, headers, HttpStatus.BAD_REQUEST, request);
        } else {
            return handleExceptionInternal(ex, null, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (Objects.isNull(body))
            body = ApiError.builder()
                    .type(((ServletWebRequest)request).getRequest().getRequestURI())
                    .status(status)
                    .message(ex.getMessage())
                    .build();

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();

        List<ApiError.Field> problemFields = bindingResult.getFieldErrors().stream()
                .map(fieldError -> {
                    String message = messageSource.getMessage(fieldError, Locale.ENGLISH);
                    return ApiError.Field.builder()
                            .name(fieldError.getField())
                            .message(message)
                            .build();
                })
                .collect(Collectors.toList());

        ApiError body = ApiError.builder()
                .type(((ServletWebRequest)request).getRequest().getRequestURI())
                .status(status)
                .message("validation field(s) failed")
                .fields(problemFields)
                .build();

        return handleExceptionInternal(ex, body, headers, status, request);

    }

}
