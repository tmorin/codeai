package io.morin.codeai.restserver;

import java.nio.charset.Charset;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
class ContentType {

  @NonNull
  @Builder.Default
  String value = "text/plain";

  @NonNull
  @Builder.Default
  String charset = Charset.defaultCharset().name();
}
