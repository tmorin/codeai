package io.morin.codeai.api;

import java.nio.file.Path;
import java.util.Set;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The CodeAi interface is the facade for the CodeAI API.
 */
public interface CodeAi {
  /**
   * Execute the Send Message Command.
   * <p>
   * The command handling is synchronous and will block until the command is completed.
   *
   * @param sendMessageCommand the send message command
   * @return the send message result
   */
  SendMessageResult execute(SendMessageCommand sendMessageCommand);

  /**
   * Execute the Stream Message Command.
   * <p>
   * The command handling is asynchronous and will return a StreamMessageResult immediately.
   * The provided OutputStream will be used to stream the result of the command.
   *
   * @param sendMessageCommand the send message command
   */
  void execute(StreamMessageCommand sendMessageCommand);

  /**
   * The settings for the codebase.
   */
  @Value
  @Builder(toBuilder = true)
  class Settings {

    /**
     * The default block list.
     */
    public static final Set<String> DEFAULT_BLOCK_LIST = Set.of(
      "/node_modules/",
      "/.git/",
      "/target/"
    );

    /**
     * The default allow list.
     */
    public static final Set<String> DEFAULT_ALLOW_LIST = Set.of(
      "text",
      "application"
    );

    /**
     * The name of the codebase.
     */
    @NonNull
    String codebaseName;

    /**
     * The path to the codebase.
     */
    @NonNull
    Path codebasePath;

    /**
     * The force codebase scanning flag.
     */
    boolean forceCodebaseScanning;

    /**
     * The blocked paths.
     */
    @NonNull
    @Builder.Default
    Set<String> blockedPaths = DEFAULT_BLOCK_LIST;

    /**
     * The allowed mime types.
     */
    @NonNull
    @Builder.Default
    Set<String> allowedMimeTypes = DEFAULT_ALLOW_LIST;
  }
}
