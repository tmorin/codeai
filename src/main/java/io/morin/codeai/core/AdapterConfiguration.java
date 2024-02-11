package io.morin.codeai.core;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class AdapterConfiguration {

  @NonNull
  ScanningChecker scanningChecker;

  @NonNull
  Chat chat;

  @NonNull
  CodebaseDocumentRepository codebaseDocumentRepository;
}
