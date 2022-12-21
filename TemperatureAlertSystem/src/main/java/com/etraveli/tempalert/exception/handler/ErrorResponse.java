package com.etraveli.tempalert.exception.handler;

@lombok.AllArgsConstructor @lombok.Builder @lombok.NoArgsConstructor
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL)

public class ErrorResponse   {

  private String code;

  private String message;

  public ErrorResponse code(String code) {
    this.code = code;
    return this;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ErrorResponse message(String message) {
    this.message = message;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

