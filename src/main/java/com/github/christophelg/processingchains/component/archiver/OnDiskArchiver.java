package com.github.christophelg.processingchains.component.archiver;

import java.io.File;

import com.github.christophelg.processingchains.framework.AbstractProcessor;
import com.github.christophelg.processingchains.framework.Context;
import com.google.common.io.Files;

/**
 * Save the input value on disk.
 * 
 * 
 */
public class OnDiskArchiver<I> extends AbstractProcessor<I> {

  private ObjectArchiver<I> archiver;
  private FileProvider<I> fileProvider;

  public OnDiskArchiver(ObjectArchiver<I> archiver, FileProvider<I> fp) {
    this.archiver = archiver;
    fileProvider = fp;
  }

  /**
   * Generates the string representation for value using the class {@link ObjectArchiver}. Writes
   * the string to the file in the archive directory from {@link #getArchiveDirectory()}. With the
   * file name from {@link #getArchiveFileName(Object, Context)}
   * 
   * @param context
   * @param value
   * @return
   * @throws Exception
   * @see com.bnpparibas.cib.cibcalypso.kondorengine.pc.framework.Processor#process(com.bnpparibas.cib.cibcalypso.kondorengine.pc.framework.Context,
   *      java.lang.Object)
   */
  public I process(Context context, I value) throws Exception {
    File file = fileProvider.getFile(context, value, archiver);
    String contentToArchive = archiver.toArchiveFormat(value);
    Files.write(contentToArchive.getBytes(), file);

    context.put(Context.ARCHIVE_FILE_NAME_KEY, file.getPath());
    return value;
  }
}
