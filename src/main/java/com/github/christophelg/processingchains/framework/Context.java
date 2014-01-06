package com.github.christophelg.processingchains.framework;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.github.christophelg.processingchains.component.archiver.OnDiskArchiver;

/**
 * Context object used alongside the {@link ProcessingChain} It carries additionnal data alongside
 * the main Processed/Converted object along the chain. It contains at least the
 * {@link KondorDexCorrelationId} that represents the processed object from dex. Any
 * converter/processor can enrich it using its {@link #put(String, Object)} method. <br>
 * All processing errors are stored in the {@link Context} with the
 * {@link #add(ProcessingViolation)}
 * 
 * @since x.x, 17 Aug 2011
 */
public class Context {
  /**
   * Key for the message {@link AlticaFlow}
   */
  public static final String ATK_FLOW_KEY = "ATK_FLOW";

  /**
   * Key used to transfer the {@link DexNotificationMessageBean} to the
   * {@link DexNotificationSender}
   */
  public static final String DEX_NOTIFICATION_KEY = "DEX_NOTIFICATION";

  /**
   * FileName of the last archive filed using an {@link OnDiskArchiver} earlier in the chain
   */
  public static final String ARCHIVE_FILE_NAME_KEY = "ARCHIVE_FILE_NAME";

  /**
   * Key used for an Mapped values
   */
  public static final String MAPPED_VALUES_KEY = "MAPPED_VALUES";

  private Map<String, Object> objects = new HashMap<String, Object>();
  private Map<ProcessingViolationCode, ProcessingViolation> violations =
      new HashMap<ProcessingViolationCode, ProcessingViolation>();
  private CorrelationId correlationId;

  public Context(CorrelationId cId) {
    correlationId = cId;
  }

  /**
   * Add a new violation.
   * 
   * @param pv the new violation to be added
   */
  public void add(ProcessingViolation pv) {
    if (pv.getCode() == InternalProcessingViolationCode.UNEXPECTED_ERROR) {
      ProcessingViolation previousUnexpected =
          violations.get(InternalProcessingViolationCode.UNEXPECTED_ERROR);
      if (previousUnexpected != null) {
        // Don't replace the previous unexpected error with a new one.
        return;
      }
    }

    violations.put(pv.getCode(), pv);
  }

  /**
   * Get all the violations encountered during the context life.
   * 
   * @return the list of {@link ProcessingViolation} added with {@link #add(ProcessingViolation)} in
   *         the {@link ProcessingChain}
   */
  public Collection<ProcessingViolation> getViolations() {
    return violations.values();
  }

  /**
   * Get the {@link CorrelationId} that identifies the message being processed in this context
   * 
   * @return
   */
  public CorrelationId getCorrelationId() {
    return correlationId;
  }

  /**
   * Add a new object to the context. Allowing it to be retrieved later using {@link #get(String)}
   * 
   * @param key the key identifying the value
   * @param value the value to store in the context
   */
  public void put(String key, Object value) {
    objects.put(key, value);
  }

  /**
   * Retrieve a value stored in the context using {@link #put(String, Object)}
   * 
   * @param key
   * @return the object associated with the input key or null if none exists
   */
  public Object get(String key) {
    return objects.get(key);
  }
}
