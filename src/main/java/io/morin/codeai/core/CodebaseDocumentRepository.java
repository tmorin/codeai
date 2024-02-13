package io.morin.codeai.core;

import lombok.NonNull;

/**
 * The repository for the {@link CodebaseDocument}.
 */
public interface CodebaseDocumentRepository {
  /**
   * Persists the given {@link CodebaseDocument}s.
   *
   * @param codebaseDocuments the documents to persist
   */
  void persist(@NonNull CodebaseDocument... codebaseDocuments);
}
