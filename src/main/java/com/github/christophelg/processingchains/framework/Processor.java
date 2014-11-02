package com.github.christophelg.processingchains.framework;

/**
 * A processor executes some processing against a value of type I (input) and returns the modified
 * value.<BR/>
 * 
 * @since x.x, 21 Jul 2011
 */
public interface Processor<I> extends ExceptionHandler<I, Exception> {

  I process(Context context, I value) throws Exception;

}
