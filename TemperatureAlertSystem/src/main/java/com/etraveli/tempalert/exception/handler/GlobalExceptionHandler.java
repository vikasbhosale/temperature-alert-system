package com.etraveli.tempalert.exception.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = BadRequestExecption.class)
	public ResponseEntity<ErrorResponse> handleBadRequestExecption(HttpServletRequest req, BadRequestExecption ex) {
		log.error("BadRequest Exception {} caught in {} path", ex.getMessage(), req.getServletPath());
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCodeEnum().getCode(),
				ex.getErrorCodeEnum().getMessage());
		return new ResponseEntity<>(errorResponse, httpStatus);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest req) {
		log.error("MethodArgumentNotValid Exception {} caught in {} path", ex.getMessage(), req.getContextPath());
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		List<FieldValidationError> fieldValidationErrors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			fieldValidationErrors.add(new FieldValidationError(fieldName, errorMessage));
		});
		ValidationErrorResponse errorResponse = buildErrorResponse(BadRequestExecption.ErrorCodeEnum.ER102,
				fieldValidationErrors);
		return new ResponseEntity<>(errorResponse, httpStatus);
	}

	private ValidationErrorResponse buildErrorResponse(BadRequestExecption.ErrorCodeEnum errorCodeEnum,
			List<FieldValidationError> errors) {
		ValidationErrorResponse errorResponse = buildErrorResponse(errorCodeEnum);
		errorResponse.setFieldValidationErrors(errors);
		return errorResponse;
	}

	private ValidationErrorResponse buildErrorResponse(BadRequestExecption.ErrorCodeEnum errorCodeEnum) {
		ValidationErrorResponse errorResponse = new ValidationErrorResponse();
		errorResponse.setCode(errorCodeEnum.getCode());
		errorResponse.setMessage(errorCodeEnum.getMessage());
		return errorResponse;
	}
}
