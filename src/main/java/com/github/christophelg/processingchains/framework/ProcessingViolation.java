package com.github.christophelg.processingchains.framework;

/**
 * A Processing Violation is a warning/error/... message that you want<BR/>
 * to remember accross all the processing chain. <BR/>
 * 
 * @since x.x, 17 Aug 2011
 */
public class ProcessingViolation {

  private Class<?> origin;
  private ProcessingViolationLevel level;
  private ProcessingViolationCode code;
  private String message;

  /*
   * Various useful ctors
   */
  public ProcessingViolation(Class<?> origin, ProcessingViolationCode code) {
    this(origin, ProcessingViolationLevel.ERROR, code);
  }

  public ProcessingViolation(Class<?> origin, ProcessingViolationLevel level, ProcessingViolationCode code) {
    this(origin, level, code, "");
  }

  public ProcessingViolation(Class<?> origin, ProcessingViolationCode code, String message) {
    this(origin, ProcessingViolationLevel.ERROR, code, message);
  }

  /**
   * ProcessingViolation Constructor
   * 
   * @param origin
   * @param level
   * @param code
   * @param message
   */
  public ProcessingViolation(Class<?> origin, ProcessingViolationLevel level, ProcessingViolationCode code,
      String message) {
    this.origin = origin;
    this.level = level;
    this.code = code;
    this.message = message;
  }

  /**
   * Get the origin
   * 
   * @return the origin
   */
  public final Class<?> getOrigin() {
    return origin;
  }

  /**
   * Set the origin to the current object
   * 
   * @param origin the origin to set
   */
  public final void setOrigin(Class<?> origin) {
    this.origin = origin;
  }

  /**
   * Get the level
   * 
   * @return the level
   */
  public final ProcessingViolationLevel getLevel() {
    return level;
  }

  /**
   * Set the level to the current object
   * 
   * @param level the level to set
   */
  public final void setLevel(ProcessingViolationLevel level) {
    this.level = level;
  }

  /**
   * Get the code
   * 
   * @return the code
   */
  public final ProcessingViolationCode getCode() {
    return code;
  }

  /**
   * Set the code to the current object
   * 
   * @param code the code to set
   */
  public final void setCode(ProcessingViolationCode code) {
    this.code = code;
  }

  /**
   * Get the message
   * 
   * @return the message
   */
  public final String getMessage() {
    return message;
  }

  /**
   * Set the message to the current object
   * 
   * @param message the message to set
   */
  public final void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "ProcessingViolation [origin=" + origin + ", level=" + level + ", code=" + code + ", message=" + message;
  }
}
