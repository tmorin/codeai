package io.morin.codeai.adapter;

import io.morin.codeai.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
class MistralSettings {

  @NonNull
  @Builder.Default
  String modelName = SettingReader.readString(
    "codeai.mistral.model_name",
    "mistral-medium"
  );

  @NonNull
  @Builder.Default
  String apiKey = SettingReader.readString("codeai.mistral.api_key");
}
