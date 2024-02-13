package io.morin.codeai.core;

import java.nio.file.Path;
import lombok.Builder;
import lombok.Value;

/**
 * A document in the codebase.
 */
@Value
@Builder(toBuilder = true)
public class CodebaseDocument {

  /**
   * The absolute path of the document.
   */
  Path absolutePath;

  /**
   * The relative path of the document.
   */
  Path relativePath;

  /**
   * The mime type of the document.
   */
  String mimeType;
}
