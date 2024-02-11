package io.morin.codeai.core;

import lombok.NonNull;

public interface CodebaseDocumentRepository {
  void persist(@NonNull CodebaseDocument... codebaseDocuments);
}
