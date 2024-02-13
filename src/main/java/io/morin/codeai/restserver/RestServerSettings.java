package io.morin.codeai.restserver;

import io.morin.codeai.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * This class represents the settings of the REST server.
 */
@Value
@Builder(toBuilder = true)
class RestServerSettings {

  /**
   * The host of the REST server.
   */
  @NonNull
  @Builder.Default
  String host = SettingReader.readString("codeai.restserver.host", "localhost");

  /**
   * The port of the REST server.
   */
  @Builder.Default
  int port = SettingReader.readInt("codeai.restserver.port", 9090);
}
