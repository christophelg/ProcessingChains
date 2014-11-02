package com.github.christophelg.processingchains.framework;

import junit.framework.TestCase;

public class ProcessingChainTest extends TestCase {

  public void testProcessor() throws ProcessingException {
    ProcessingChain<String, String> toTest = ProcessingChainBuilder.build(new TestProcessor());
    String result = toTest.process(new Context(null), "1");
    assertTrue(result.equals("11"));
  }

  public static class TestProcessor implements Processor<String> {

    public boolean handleException(Context context, String value, Exception e) {
      return false;
    }

    public String process(Context context, String value) throws Exception {
      return "1" + value;
    }
  }

  public void testConverter() throws ProcessingException {
    ProcessingChain<String, Integer> toTest =
        ProcessingChainBuilder.build(new TestProcessor()).add(new TestConverter());
    Integer result = toTest.process(new Context(null), "1");
    assertTrue(result == 11);
  }

  public static class TestConverter implements Converter<String, Integer> {

    public boolean handleException(Context context, String value, Exception e) {
      System.err.println("doh!");
      return false;
    }

    public Integer convert(Context context, String value) throws Exception {
      return Integer.valueOf(value);
    }
  }

}
