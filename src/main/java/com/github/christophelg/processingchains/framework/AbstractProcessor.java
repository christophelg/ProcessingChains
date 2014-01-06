package com.github.christophelg.processingchains.framework;

import java.util.LinkedList;
import java.util.List;

/**
 * This abstract class implements the logic of multiple exception handler dispatchers.
 * 
 * @since x.x, 12 Aug 2011
 */
public abstract class AbstractProcessor<I> implements Processor<I> {

  private List<ExceptionHandler<I, Exception>> handlers = new LinkedList<ExceptionHandler<I, Exception>>();

  public Processor<I> addExceptionHandler(ExceptionHandler<I, Exception> eh) {
    handlers.add(eh);
    return this;
  }

  /**
   * @see com.bnpparibas.cib.cibcalypso.kondorengine.pc.framework.ExceptionHandler#handleException(Context,
   *      java.lang.Object, java.lang.Exception)
   */
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
