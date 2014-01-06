package com.github.christophelg.processingchains.framework;

/**
 * A converter converts a value of type I (input) into a value of type O (output) given a context.<BR/>
 * 
 * @since x.x, 21 Jul 2011
 */
public interface Converter<I, O> extends ExceptionHandler<I, Exception> {

  O convert(Context context, I value) throws Exception;

}
