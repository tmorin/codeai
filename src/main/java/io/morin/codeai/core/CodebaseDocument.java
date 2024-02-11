package io.morin.codeai.core;

import java.nio.file.Path;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CodebaseDocument {

  Path absolutePath;
  Path relativePath;
  String mimeType;
}
