package com.github.christophelg.processingchains.component.archiver;

import com.github.christophelg.processingchains.framework.Context;

/**
 * Used in conjunction with {@link OnDiskArchiver} to archive processed files on disk. <BR>
 * Each implementation of this interface transform a java object into a text format to be saved on
 * disk.
 * 
 * @param <I> the object to archive type
 */
public interface ObjectArchiver<I> {
  /**
   * Transform the object to archive into its "archive format", a string representation of itself.
   * 
   * @param input the object to archive
   * @return the string representing the input
   * @throws Exception if the transformation fails
   */
  public String toArchiveFormat(I input) throws Exception;

  /**
   * Get the file extension
   * 
   * @param context the processing chain context
   * @param input the object to archive
   * 
   * @return a string with the file extension
   */
  public String getFileExtension(Context context, I input);
}
