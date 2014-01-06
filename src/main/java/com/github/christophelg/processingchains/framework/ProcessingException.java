package com.github.christophelg.processingchains.framework;

/**
 *
 */
public class ProcessingException extends Exception {

  private static final long serialVersionUID = 2647589947778231555L;

  private final Class<?> exceptionSource;

  /**
   * ProcessingException Constructor
   */
  public ProcessingException(Class<?> exceptionSource, String message, Throwable cause) {
    super(message, cause);
    this.exceptionSource = exceptionSource;
  }

  /**
   * ProcessingException Constructor
   * 
   * @param message
   */
  public ProcessingException(Class<?> exceptionSource, String message) {
    this(exceptionSource, message, null);
  }

  /**
   * ProcessingException Constructor
   * 
   * @param cause
   */
  public ProcessingException(Class<?> exceptionSource, Throwable cause) {
    this(exceptionSource, null, cause);
  }

  /**
   * Get the exceptionSource
   * 
   * @return the exceptionSource
   */
  public Class<?> getExceptionSource() {
    return exceptionSource;
  }

  @Override
  public String toString() {
    return "From:" + exceptionSource.getName() + "," + super.toString();
  }
}
