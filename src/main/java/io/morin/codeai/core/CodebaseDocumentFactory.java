package io.morin.codeai.core;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class CodebaseDocumentFactory implements Function<Path, CodebaseDocument> {

  @NonNull
  Path codebasePath;

  @NonNull
  @Override
  @SneakyThrows
  public CodebaseDocument apply(Path absolutePath) {
    log.info("create document: {}", absolutePath);
    return CodebaseDocument
      .builder()
      .absolutePath(absolutePath)
      .relativePath(codebasePath.relativize(absolutePath))
      .mimeType(Files.probeContentType(absolutePath))
      .build();
  }
}
