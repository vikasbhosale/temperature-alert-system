package com.etraveli.tempalert.exception.handler;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An error entity which can be used at the API level for error responses
 */
@lombok.AllArgsConstructor @lombok.Builder @lombok.NoArgsConstructor
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)

public class ValidationErrorResponse   {

  @JsonProperty("code")
  private String code;

  @JsonProperty("message")
  private String message;

  @JsonProperty("fieldValidationErrors")
  private List<FieldValidationError> fieldValidationErrors = null;

  public ValidationErrorResponse code(String code) {
    this.code = code;
    return this;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ValidationErrorResponse message(String message) {
    this.message = message;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ValidationErrorResponse fieldValidationErrors(List<FieldValidationError> fieldValidationErrors) {
    this.fieldValidationErrors = fieldValidationErrors;
    return this;
  }

  public ValidationErrorResponse addFieldValidationErrorsItem(FieldValidationError fieldValidationErrorsItem) {
    if (this.fieldValidationErrors == null) {
      this.fieldValidationErrors = new ArrayList<>();
    }
    this.fieldValidationErrors.add(fieldValidationErrorsItem);
    return this;
  }

  /**
   * Array of field error.
   * @return fieldValidationErrors
  */
  public List<FieldValidationError> getFieldValidationErrors() {
    return fieldValidationErrors;
  }

  public void setFieldValidationErrors(List<FieldValidationError> fieldValidationErrors) {
    this.fieldValidationErrors = fieldValidationErrors;
  }

}

