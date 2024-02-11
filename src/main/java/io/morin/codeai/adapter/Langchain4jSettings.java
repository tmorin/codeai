package io.morin.codeai.adapter;

import io.morin.codeai.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
class Langchain4jSettings {

  enum Integration {
    MISTRAL,
    OLLAMA,
  }

  @NonNull
  @Builder.Default
  Integration integration = Integration.valueOf(
    SettingReader.readString(
      "codeai.langchain4j.integration",
      Integration.MISTRAL.name()
    )
  );

  @Builder.Default
  int dimension = SettingReader.readInt("codeai.langchain4j.dimension", 384);

  @Builder.Default
  int maxMemoryMessages = SettingReader.readInt(
    "codeai.langchain4j.max_memory_messages",
    20
  );

  @Builder.Default
  int maxSegmentSizeInChars = SettingReader.readInt(
    "codeai.langchain4j.max_segment_size_in_chars",
    500
  );

  @Builder.Default
  int maxOverlapSizeInChars = SettingReader.readInt(
    "codeai.langchain4j.max_overlap_size_in_chars",
    0
  );
}
