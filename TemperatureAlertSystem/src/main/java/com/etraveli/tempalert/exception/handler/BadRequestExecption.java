package com.etraveli.tempalert.exception.handler;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author VBhosale
 *
 */
public class BadRequestExecption extends RuntimeException {
  /**
   * 
   */
  private static final long serialVersionUID = 3645217024576228545L;

  private final ErrorCodeEnum errorCodeEnum;

  /**
   * @param errorCodeEnum
   */
  public BadRequestExecption(ErrorCodeEnum errorCodeEnum) {
    super();
    this.errorCodeEnum = errorCodeEnum;
  }



  /**
   * @param errorCodeEnum
   * @param keepLogin
   */
  public BadRequestExecption(ErrorCodeEnum errorCodeEnum, boolean keepLogin) {
    this.errorCodeEnum = errorCodeEnum;
  }



  /**
   * @return the errorCodeEnum
   */
  public ErrorCodeEnum getErrorCodeEnum() {
    return errorCodeEnum;
  }


  /**
   * Gets or Sets code
   */
  public enum ErrorCodeEnum {
	  ER101("ER101", "User with Email id already exist Failure"),
    ER102("ER102", "Request Validation Failure"),
    ER103("ER103", "Id cannot be Zero OR Null"),
    ER104("ER104", "No user exist with ID");

    private String code;
    private String message;

    ErrorCodeEnum(String code, String message) {
      this.code = code;
      this.message = message;
    }

    /**
     * @return the code
     */
    String getCode() {
      return code;
    }

    /**
     * @param code the code to set
     */
    void setCode(String code) {
      this.code = code;
    }

    /**
     * @return the message
     */
    String getMessage() {
      return message;
    }

    /**
     * @param message the message to set
     */
    void setMessage(String message) {
      this.message = message;
    }

    /**
     * @param code
     * @return
     */
    public static ErrorCodeEnum findByErrorCode(String code) {
      return Arrays
          .stream(values()).filter(currentValue -> Optional.of(currentValue)
              .map(ErrorCodeEnum::getCode).filter(errorCode -> errorCode.equals(code)).isPresent())
          .findFirst().orElse(null);
    }

  }


}
