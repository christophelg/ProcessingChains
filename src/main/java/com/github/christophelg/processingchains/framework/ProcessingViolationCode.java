package com.github.christophelg.processingchains.framework;

/**
 * This interface exists so that end-users of the framework can define their own set of violation
 * codes. Typically, one will create an enum, that implements this interface, with all the possible
 * violations. Then it will use those violations in its {@link Processor}, {@link Converter}.
 * 
 * @since x.x, 17 Aug 2011
 */
public interface ProcessingViolationCode {

  /**
   * Get the message
   * 
   * @return the message
   */
  public String getMessage();

  /**
   * Get the errorType
   * 
   * @return the errorType
   */
  public char getErrorType();

  /**
   * This method me
   * <P>
   * <B>Precondition:</B> TODO<BR>
   * <B>Postcondition:</B> TODO
   * </P>
   * 
   * @return
   */
  public String name();
}
