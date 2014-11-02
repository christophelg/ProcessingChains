package com.github.christophelg.processingchains.framework;

/**
 * This interface tags value that will serve as Identifier for the message being processed in this
 * context This is our equivalent of the JMS attribute: JMSCorrelationID.
 * 
 * @since x.x, 23 Dec 2011
 */
public interface CorrelationId {

  String getCorrelationId();

  String getObjectId();

}
