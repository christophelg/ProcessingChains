Processing Chains
=================

Processing Chains is a simple framework to construct components and then assemble those components in a 
'processing chain'.

Motivation
----------

In the financial world, it is common to have applications that listen to stream of data, for example:
quotes or trades data.
The handling of those streams of data will require that each item of data go through a certain number of
steps like parsing, filtering, validation, etc...
As your business requirements evolve more processing steps need to be added to handle the streams of data.
The Processing Chains framework helps to structure and organize those steps.

Solution
--------

The idea is to create components that can be re-use accross processing chains in a **type-safe** manner.
Typically a component will either convert the item of data or perform some business action, so the framework
exposes 2 simple interfaces.

```java
public interface Processor<I> {
  I process(Context context, I value) throws Exception;
}

public interface Converter<I, O> {
  O convert(Context context, I value) throws Exception;
}
```

Examples
--------

Look in the unittests folder for some basic example.

Related works
-------------

If you want to look at more advanced frameworks

disruptor:
http://lmax-exchange.github.io/disruptor/


CSP:
http://www.cs.kent.ac.uk/projects/ofa/jcsp/
