package com.github.christophelg.processingchains.framework;

import java.util.LinkedList;
import java.util.List;

/**
 * This abstract class implements the logic of multiple exception handler dispatchers.
 * 
 * @since x.x, 22 Jul 2011
 */
public abstract class AbstractConverter<I, O> implements Converter<I, O> {

  private List<ExceptionHandler<I, Exception>> handlers = new LinkedList<ExceptionHandler<I, Exception>>();

  public Converter<I, O> addExceptionHandler(ExceptionHandler<I, Exception> eh) {
    handlers.add(eh);
    return this;
  }

  public boolean handleException(Context context, I value, Exception e) {
    if (handlers.isEmpty()) {
      return false;
    }

    for (ExceptionHandler<I, Exception> handler : handlers) {
      handler.handleException(context, value, e);
    }
    return true;
  }
}
