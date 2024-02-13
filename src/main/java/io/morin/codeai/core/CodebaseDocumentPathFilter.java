package io.morin.codeai.core;

import io.morin.codeai.api.CodeAi;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * A filter for codebase documents based on their path.
 */
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class CodebaseDocumentPathFilter implements Predicate<Path> {

  /**
   * The list of paths to block.
   */
  @NonNull
  @Builder.Default
  Set<String> blockedList = CodeAi.Settings.DEFAULT_BLOCK_LIST;

  /**
   * Tests whether the given path is blocked.
   *
   * @param path the path to test
   * @return {@code true} if the path is not blocked, {@code false} otherwise
   */
  @Override
  public boolean test(@NonNull Path path) {
    return blockedList.stream().noneMatch(path.toString()::contains);
  }
}
