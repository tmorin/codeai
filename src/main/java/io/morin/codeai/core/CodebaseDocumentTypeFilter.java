package io.morin.codeai.core;

import io.morin.codeai.api.CodeAi;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class CodebaseDocumentTypeFilter implements Predicate<Path> {

  @NonNull
  @Builder.Default
  Set<String> allowedList = CodeAi.Settings.DEFAULT_ALLOW_LIST;

  @Override
  @SneakyThrows
  public boolean test(@NonNull Path path) {
    return Optional
      .ofNullable(Files.probeContentType(path))
      .filter(type ->
        allowedList.stream().map(v -> v + "/").anyMatch(type::startsWith)
      )
      .isPresent();
  }
}
