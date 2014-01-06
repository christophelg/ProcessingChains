package com.github.christophelg.processingchains.framework;

/**
 * A handler that processes exceptions that arise when the <BR/>
 * ProcessingChain throws an exception on a particular processing elements.<BR/>
 * 
 * @since x.x, 22 Jul 2011
 */
public interface ExceptionHandler<T, E extends Exception> {
  /**
   * @param context
   * @param value
   * @param e
   * @return
   */
  boolean handleException(Context context, T value, E e);
}
