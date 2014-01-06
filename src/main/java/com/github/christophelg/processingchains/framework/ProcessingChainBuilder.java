package com.github.christophelg.processingchains.framework;

/**
 * This class instanciates 5 implementations of ProcessingChain depending on how one stacks the
 * Processor / Converter. <BR>
 * <DT><B>Modification History:</B></DT><BR>
 * <DD>12 Aug 2011 : 833797 : Creation</DD><BR>
 * 
 * @since x.x, 12 Aug 2011
 */
public class ProcessingChainBuilder {

  /**
   * Creates a Processing chain starting with a Processor.
   * 
   * @param <I> the Input type
   * @param processor the processor starting the processing chain
   * @return a single entry ProcessingChain that can be chained using one of its add methods
   */
  public static <I> ProcessingChain<I, I> build(Processor<I> processor) {
    return new NoTailProcessorPC<I>(processor);
  }

  /**
   * Creates a Processing chain starting with a Convertor. The input object will initially be
   * transformed from its type I to another object of Type O.
   * 
   * @param <I> the convertor input type
   * @param <O> the convertor output type
   * @param converter the converter starting the processing chain
   * @return a single entry ProcessingChain that can be chained using one of its add methods
   */
  public static <I, O> ProcessingChain<I, O> build(Converter<I, O> converter) {
    return new NoTailConverterPC<I, O>(converter);
  }

  private abstract static class AbstractProcessingChain<F, T> implements ProcessingChain<F, T> {
    protected ExceptionHandler<F, ProcessingException> exceptionHandler;

    public <U> ProcessingChain<F, U> add(Converter<T, U> converter) {
      return new ConverterHeadedPC<F, T, U>(this, converter);
    }

    public ProcessingChain<F, T> add(Processor<T> processor) {
      return new ProcessorHeadedPC<F, T>(this, processor);
    }

    public <V> ProcessingChain<F, V> append(ProcessingChain<T, V> processingChain) {
      return new LinkedProcessingChain<F, T, V>(this, processingChain);
    }

    /**
     * @see com.bnpparibas.cib.cibcalypso.kondorengine.pc.framework.ProcessingChain#setExceptionHandler(com.bnpparibas.cib.cibcalypso.kondorengine.pc.framework.ExceptionHandler)
     */
    public void setExceptionHandler(ExceptionHandler<F, ProcessingException> exceptionHandler) {
      this.exceptionHandler = exceptionHandler;
    }

    protected <I> void handleException(ExceptionHandler<I, Exception> processor, Exception e, Context context, F value,
        I intermediate)
        throws ProcessingException {
      boolean handled = processor.handleException(context, intermediate, e);
      if (handled) {
        return;
      }
      ProcessingException exception = convertToProcessingException(processor, e);
      if (exceptionHandler == null) {
        throw exception;
      }
      exceptionHandler.handleException(context, value, exception);
    }

    private <I> ProcessingException convertToProcessingException(ExceptionHandler<I, Exception> processor, Exception e) {
      ProcessingException exception = null;
      if (e instanceof ProcessingException) {
        exception = (ProcessingException) e;
      } else {
        exception = new ProcessingException(processor.getClass(), e);
      }
      return exception;
    }
  }

  private static class LinkedProcessingChain<I, M, O> extends AbstractProcessingChain<I, O> {
    private ProcessingChain<I, M> head;
    private ProcessingChain<M, O> tail;

    public LinkedProcessingChain(ProcessingChain<I, M> head, ProcessingChain<M, O> tail) {
      this.head = head;
      this.tail = tail;
    }

    public O process(Context context, I value) throws ProcessingException {
      O toReturn = null;
      M intermediary = head.process(context, value);
      if (intermediary != null) {
        toReturn = tail.process(context, intermediary);
      }
      return toReturn;
    }
  }

  private static class ProcessorHeadedPC<F, T> extends AbstractProcessingChain<F, T> {
    private ProcessingChain<F, T> head;
    private Processor<T> tail;

    public ProcessorHeadedPC(ProcessingChain<F, T> rest, Processor<T> processor) {
      head = rest;
      tail = processor;
    }

    public T process(Context context, F value) throws ProcessingException {
      T toReturn = head.process(context, value);
      try {
        if (toReturn != null) {
          toReturn = tail.process(context, toReturn);
        }
      } catch (Exception e) {
        handleException(tail, e, context, value, toReturn);
        return null;
      }
      return toReturn;
    }

    @Override
    public void setExceptionHandler(ExceptionHandler<F, ProcessingException> exceptionHandler) {
      super.setExceptionHandler(exceptionHandler);
      head.setExceptionHandler(exceptionHandler);
    }

  }

  private static class NoTailProcessorPC<T> extends AbstractProcessingChain<T, T> {
    private Processor<T> head;

    public NoTailProcessorPC(Processor<T> processor) {
      head = processor;
    }

    public T process(Context context, T value) throws ProcessingException {
      T toReturn = null;
      try {
        toReturn = head.process(context, value);
      } catch (Exception e) {
        handleException(head, e, context, value, value);
        return null;
      }
      return toReturn;
    }
  }

  private static class ConverterHeadedPC<F, I, T> extends AbstractProcessingChain<F, T> {
    private ProcessingChain<F, I> head;
    private Converter<I, T> tail;

    public ConverterHeadedPC(ProcessingChain<F, I> rest, Converter<I, T> converter) {
      head = rest;
      tail = converter;
    }

    public T process(Context context, F value) throws ProcessingException {
      I intermediate = head.process(context, value);
      T toReturn = null;
      try {
        if (intermediate != null) {
          toReturn = tail.convert(context, intermediate);
        }
      } catch (Exception e) {
        handleException(tail, e, context, value, intermediate);
        return null;
      }
      return toReturn;
    }

    @Override
    public void setExceptionHandler(ExceptionHandler<F, ProcessingException> exceptionHandler) {
      super.setExceptionHandler(exceptionHandler);
      head.setExceptionHandler(exceptionHandler);
    }
  }

  private static class NoTailConverterPC<F, T> extends AbstractProcessingChain<F, T> {
    private Converter<F, T> head;

    public NoTailConverterPC(Converter<F, T> converter) {
      head = converter;
    }

    public T process(Context context, F value) throws ProcessingException {
      T toReturn = null;
      try {
        toReturn = head.convert(context, value);
      } catch (Exception e) {
        handleException(head, e, context, value, value);
        return null;
      }
      return toReturn;
    }
  }
}
