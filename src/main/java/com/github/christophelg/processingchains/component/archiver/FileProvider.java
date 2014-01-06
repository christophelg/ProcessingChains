package com.github.christophelg.processingchains.component.archiver;

import java.io.File;

import com.github.christophelg.processingchains.framework.Context;

public interface FileProvider<I> {

  File getFile(Context context, I value, ObjectArchiver<I> oa) throws Exception;

}
