package io.morin.codeai.adapter;

import io.morin.codeai.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The settings for the Langchain4j integration.
 */
@Value
@Builder(toBuilder = true)
class Langchain4jSettings {

  /**
   * The integration to use.
   */
  @NonNull
  @Builder.Default
  Integration integration = Integration.valueOf(
    SettingReader.readString(
      "codeai.langchain4j.integration",
      Integration.MISTRAL.name()
    )
  );

  /**
   * The dimension of the embeddings.
   */
  @Builder.Default
  int dimension = SettingReader.readInt("codeai.langchain4j.dimension", 384);

  /**
   * The maximum number of messages to keep in memory.
   */
  @Builder.Default
  int maxMemoryMessages = SettingReader.readInt(
    "codeai.langchain4j.max_memory_messages",
    20
  );

  /**
   * The maximum segment size in characters.
   */
  @Builder.Default
  int maxSegmentSizeInChars = SettingReader.readInt(
    "codeai.langchain4j.max_segment_size_in_chars",
    500
  );

  /**
   * The maximum overlap size in characters.
   */
  @Builder.Default
  int maxOverlapSizeInChars = SettingReader.readInt(
    "codeai.langchain4j.max_overlap_size_in_chars",
    0
  );

  /**
   * The supported Langchain4j integrations.
   */
  enum Integration {
    MISTRAL,
    OLLAMA,
  }
}
