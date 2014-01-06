package com.github.christophelg.processingchains.framework;


public enum InternalProcessingViolationCode implements ProcessingViolationCode {
  UNEXPECTED_ERROR("A technical error has occured. Please check logs", 'T');

  private final String message;
  private final char errorType;

  InternalProcessingViolationCode(String message, char errorType) {
    this.message = message;
    this.errorType = errorType;
  };

  /**
   * @see com.github.christophelg.processingchains.framework.ProcessingViolationCode#getErrorType()
   */
  public char getErrorType() {
    return errorType;
  }

  /**
   * @see com.github.christophelg.processingchains.framework.ProcessingViolationCode#getMessage()
   */
  public String getMessage() {
    return message;
  }

}
