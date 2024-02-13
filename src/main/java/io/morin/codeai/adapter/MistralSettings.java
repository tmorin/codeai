package io.morin.codeai.adapter;

import io.morin.codeai.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * The settings for the Mistral integration.
 */
@Value
@Builder(toBuilder = true)
class MistralSettings {

  /**
   * The model name to use.
   */
  @NonNull
  @Builder.Default
  String modelName = SettingReader.readString(
    "codeai.mistral.model_name",
    "mistral-medium"
  );

  /**
   * The API key to use.
   */
  @NonNull
  @Builder.Default
  String apiKey = SettingReader.readString("codeai.mistral.api_key");
}
