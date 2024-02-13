package io.morin.codeai.core;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The configuration provided by the adapter implementation.
 */
@Value
@Builder(toBuilder = true)
public class AdapterConfiguration {

  /**
   * The adapted ScanningChecker instance.
   */
  @NonNull
  ScanningChecker scanningChecker;

  /**
   * The adapted Chat instance.
   */
  @NonNull
  Chat chat;

  /**
   * The adapted CodebaseDocumentRepository instance.
   */
  @NonNull
  CodebaseDocumentRepository codebaseDocumentRepository;
}
