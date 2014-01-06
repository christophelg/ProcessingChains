package com.github.christophelg.processingchains.component.archiver;

import com.github.christophelg.processingchains.framework.Context;

/**
 * Simple implementation for a string. It only returns the string and the file extension is given
 * during the object construction
 */
public class StringArchiver implements ObjectArchiver<String> {
  private final String fileExtension;

  public StringArchiver(String fileExtension) {
    this.fileExtension = fileExtension;
  }

  /**
   * @see com.bnpparibas.cib.cibcalypso.kondorengine.pc.component.archiver.ObjectArchiver#toArchiveFormat(java.lang.Object)
   */
  public String toArchiveFormat(String input) throws Exception {
    return input;
  }

  /**
   * @see com.bnpparibas.cib.cibcalypso.kondorengine.pc.component.archiver.ObjectArchiver#getFileExtension(com.bnpparibas.cib.cibcalypso.kondorengine.pc.framework.Context,
   *      java.lang.Object)
   */
  public String getFileExtension(Context context, String input) {
    return fileExtension;
  }
}
