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
class Neo4jSettings {

  /**
   * The host to use.
   */
  @NonNull
  @Builder.Default
  String host = SettingReader.readString("codeai.neo4j.host", "localhost");

  /**
   * The port to use.
   */
  @Builder.Default
  int port = SettingReader.readInt("codeai.neo4j.port", 7687);
}
