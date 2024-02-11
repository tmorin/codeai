package io.morin.codeai.core;

import io.morin.codeai.api.CodeAi;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class CodeAiCoreFactory {

  Chat chat;

  CodebaseDocumentRepository codebaseDocumentRepository;

  ScanningChecker scanningChecker;

  @NonNull
  CodeAiCore create(@NonNull CodeAi.Settings settings) {
    val codebaseDocumentFactory = CodebaseDocumentFactory
      .builder()
      .codebasePath(settings.getCodebasePath())
      .build();

    val codebaseScanner = CodebaseScanner
      .builder()
      .codebaseDocumentFactory(codebaseDocumentFactory)
      .codebaseDocumentRepository(codebaseDocumentRepository)
      .codebaseDocumentPathFilter(
        CodebaseDocumentPathFilter
          .builder()
          .blockedList(settings.getBlockedPaths())
          .build()
      )
      .codebaseDocumentTypeFilter(
        CodebaseDocumentTypeFilter
          .builder()
          .allowedList(settings.getAllowedMimeTypes())
          .build()
      )
      .build();

    if (
      scanningChecker.isNotYetScanned() || settings.isForceCodebaseScanning()
    ) {
      codebaseScanner.scan(settings);
    }

    return CodeAiCore.builder().chat(chat).build();
  }
}
