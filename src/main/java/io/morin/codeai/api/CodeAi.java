package io.morin.codeai.api;

import java.nio.file.Path;
import java.util.Set;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

public interface CodeAi {
  SendMessageResult execute(SendMessageCommand sendMessageCommand);

  @Value
  @Builder(toBuilder = true)
  class Settings {

    public static final Set<String> DEFAULT_BLOCK_LIST = Set.of(
      "/node_modules/",
      "/.git/",
      "/target/"
    );

    public static final Set<String> DEFAULT_ALLOW_LIST = Set.of(
      "text",
      "application"
    );

    @NonNull
    String codebaseName;

    @NonNull
    Path codebasePath;

    boolean forceCodebaseScanning;

    @NonNull
    @Builder.Default
    Set<String> blockedPaths = DEFAULT_BLOCK_LIST;

    @NonNull
    @Builder.Default
    Set<String> allowedMimeTypes = DEFAULT_ALLOW_LIST;
  }
}
