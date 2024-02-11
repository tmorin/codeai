package io.morin.codeai.core;

import dev.langchain4j.data.document.parser.TextDocumentParser;
import io.morin.codeai.api.CodeAi;
import java.nio.file.Files;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class CodebaseScanner {

  @NonNull
  @Builder.Default
  CodebaseDocumentPathFilter codebaseDocumentPathFilter =
    CodebaseDocumentPathFilter.builder().build();

  @NonNull
  @Builder.Default
  CodebaseDocumentTypeFilter codebaseDocumentTypeFilter =
    CodebaseDocumentTypeFilter.builder().build();

  @NonNull
  @Builder.Default
  TextDocumentParser textDocumentParser = new TextDocumentParser();

  @NonNull
  CodebaseDocumentFactory codebaseDocumentFactory;

  @NonNull
  CodebaseDocumentRepository codebaseDocumentRepository;

  @SneakyThrows
  void scan(CodeAi.Settings settings) {
    try (val stream = Files.walk(settings.getCodebasePath())) {
      log.info("scan documents from: {}", settings.getCodebasePath());
      stream
        .filter(Files::isRegularFile)
        .filter(codebaseDocumentPathFilter)
        .filter(codebaseDocumentTypeFilter)
        .map(codebaseDocumentFactory)
        .forEach(codebaseDocumentRepository::persist);
    }
  }
}
