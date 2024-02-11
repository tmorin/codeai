package io.morin.codeai.adapter;

import io.morin.codeai.fwk.SettingReader;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
class Neo4jSettings {

  @NonNull
  @Builder.Default
  String host = SettingReader.readString("codeai.neo4j.host", "localhost");

  @Builder.Default
  int port = SettingReader.readInt("codeai.neo4j.port", 7687);
}
