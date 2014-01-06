package com.github.christophelg.processingchains.component.archiver;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import com.github.christophelg.processingchains.framework.Context;

/**
 * Specialization of ObjectArchiver to support serialization to XML given a JAXBContext. Using
 * {@link #setEnclosingElement(String, String)()} one can specify the enclosing XML element that
 * should wrap the actual object.
 * 
 * @since x.x, 11 Oct 2011
 */
public class XmlArchiver<T> implements ObjectArchiver<T> {

  private String fileExt;
  private JAXBContext jaxbContext;
  private boolean shouldWrap;
  private QName enclosingName;

  public XmlArchiver(JAXBContext context, String extension) {
    fileExt = extension;
    jaxbContext = context;
  }

  public XmlArchiver(JAXBContext context) {
    this(context, "xml");
  }

  public String getFileExtension(Context context, T input) {
    return fileExt;
  }

  public XmlArchiver<T> setEnclosingElement(String url, String name) {
    shouldWrap = true;
    enclosingName = new QName(url, name);
    return this;
  }

  public String toArchiveFormat(T input) throws Exception {

    StringWriter stringWriter = new StringWriter();
    Marshaller marshaller = jaxbContext.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    if (shouldWrap) {
      @SuppressWarnings("unchecked")
      JAXBElement<T> element = new JAXBElement<T>(enclosingName, (Class<T>) input.getClass(), input);
      marshaller.marshal(element, stringWriter);
    } else {
      marshaller.marshal(input, stringWriter);
    }
    return stringWriter.toString();
  }
}
