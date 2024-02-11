package io.morin.codeai.adapter;

import io.morin.codeai.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
class OllamaSettings {

  @NonNull
  @Builder.Default
  String modelName = SettingReader.readString(
    "codeai.ollama.model_name",
    "mistral"
  );

  @NonNull
  @Builder.Default
  String baseUrl = SettingReader.readString(
    "codeai.ollama.base_url",
    "http://localhost:11434"
  );
}
