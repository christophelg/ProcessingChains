package com.github.christophelg.processingchains.framework;

/**
 * Represents a chain of {@link Converter} and {@link Processor}. <br>
 * A processing chain main purpose is to transform an input object of a Type I to an output object
 * of another type O. This transformation is splitted in multiple simple steps chained using this
 * API. To build a Processing chain, the {@link ProcessingChainBuilder} creates the beginning of the
 * chain. <br>
 * After creating a chain, it can be expanded using either {@link #add(Converter)} or
 * {@link #add(Processor)} depending on the next step goal. <br>
 * Two independant but compatible chains (regarding their input and ouput type) can be linked as one
 * chain using the {@link #append(ProcessingChain)} method. <br>
 * The chain is executed by the {@link #process(Context, Object)} method that takes the process
 * context and input value as parameters. <br>
 * If no exception arise, the process result will be returned. Otherwise, there is 3 possible
 * scenarios :
 * <ul>
 * <li>The processor or converter where the exception arise handled the exception within
 * {@link Processor#handleException(Context, Object, Exception)} or
 * {@link Converter#handleException(Context, Object, Exception)}. In this case, the exception won't
 * be rethrown.</li>
 * <li>If a global exception handler was set on this chain using the
 * {@link #setExceptionHandler(ExceptionHandler)}, then this handler will manage the exception</li>
 * <li>Otherwise, the exception will be encapsulated in a {@link ProcessingException} and be thrown
 * by the {@link #process(Context, Object)} method</li>
 * </ul>
 * 
 * @since x.x, 21 Jul 2011
 */
public interface ProcessingChain<I, O> {

  /**
   * Append a converter to the current chain. The new return chain will represent the current chain
   * appended with a converter.
   * 
   * @param <U> the converter output type
   * @param converter the converter to add at the end of the current chain
   * @return a new chain, representing the current chain with a new convert step at its end
   */
  <U> ProcessingChain<I, U> add(Converter<O, U> converter);

  /**
   * Append a processor to the current chain. The new return chain will represent the current chain
   * appended with a processor.
   * 
   * @param processor the processor to add at the end of the current chain
   * @return a new chain, representing the current chain with a new processor step at its end
   */
  ProcessingChain<I, O> add(Processor<O> processor);

  /**
   * Join a trailing processing chain to the current one. The return processing chain will represent
   * the union of both chains.
   * 
   * @param <V> the new processing chain output type
   * @param processingChain the processing chain to add at the end of the current one
   * @return the union of the current processing chain and the one given as a parameter
   */
  <V> ProcessingChain<I, V> append(ProcessingChain<O, V> processingChain);

  /**
   * Execute the chain. Call the successive {@link Converter#convert(Context, Object)} and
   * {@link Processor#process(Context, Object)} to transform value to the output result. The context
   * will be transmitted during the complete chain execution.
   * 
   * @param context the execution context
   * @param value the input value to be processed and converted
   * @return the converted value
   * @throws ProcessingException in case of unhandled exception during
   */
  O process(Context context, I value) throws ProcessingException;

  /**
   * Set a global exception handler that will be called if one step of the chain throws an
   * exception.
   * 
   * @param exceptionHandler the global exception handler to attach to all of the chain steps
   */
  void setExceptionHandler(ExceptionHandler<I, ProcessingException> exceptionHandler);

}
