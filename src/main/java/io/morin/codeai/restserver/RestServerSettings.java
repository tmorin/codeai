package io.morin.codeai.restserver;

import io.morin.codeai.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
class RestServerSettings {

  @NonNull
  @Builder.Default
  String host = SettingReader.readString("codeai.restserver.host", "localhost");

  @Builder.Default
  int port = SettingReader.readInt("codeai.restserver.port", 9090);
}
