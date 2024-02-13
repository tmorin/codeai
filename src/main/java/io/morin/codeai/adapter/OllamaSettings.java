package io.morin.codeai.adapter;

import io.morin.codeai.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The settings for the Neo4j integration.
 */
@Value
@Builder(toBuilder = true)
class OllamaSettings {

  /**
   * The model name to use.
   */
  @NonNull
  @Builder.Default
  String modelName = SettingReader.readString(
    "codeai.ollama.model_name",
    "mistral"
  );

  /**
   * The base URL to use.
   */
  @NonNull
  @Builder.Default
  String baseUrl = SettingReader.readString(
    "codeai.ollama.base_url",
    "http://localhost:11434"
  );
}
