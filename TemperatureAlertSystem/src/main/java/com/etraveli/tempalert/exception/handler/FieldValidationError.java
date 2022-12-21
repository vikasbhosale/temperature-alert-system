package com.etraveli.tempalert.exception.handler;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An error entity which can be used at the API level for error responses
 */
@lombok.AllArgsConstructor @lombok.Builder @lombok.NoArgsConstructor
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)

public class FieldValidationError   {

  @JsonProperty("fieldName")
  private String fieldName;

  @JsonProperty("errorMessage")
  private String errorMessage;

  public FieldValidationError fieldName(String fieldName) {
    this.fieldName = fieldName;
    return this;
  }

  /**
   * Field name which failed to validate.
   * @return fieldName
  */
  
  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public FieldValidationError errorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
    return this;
  }

  /**
   * Describe field validation error.  
   * @return errorMessage
  */
  
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FieldValidationError fieldValidationError = (FieldValidationError) o;
    return Objects.equals(this.fieldName, fieldValidationError.fieldName) &&
        Objects.equals(this.errorMessage, fieldValidationError.errorMessage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fieldName, errorMessage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FieldValidationError {\n");
    sb.append("    fieldName: ").append(toIndentedString(fieldName)).append("\n");
    sb.append("    errorMessage: ").append(toIndentedString(errorMessage)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

