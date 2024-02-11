package io.morin.codeai.adapter;

import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.morin.codeai.core.CodebaseDocument;
import io.morin.codeai.core.CodebaseDocumentRepository;
import java.util.Arrays;
import java.util.Objects;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class AdaptedCodebaseDocumentRepository implements CodebaseDocumentRepository {

  @NonNull
  @Builder.Default
  TextDocumentParser textDocumentParser = new TextDocumentParser();

  @NonNull
  EmbeddingStoreIngestor embeddingStoreIngestor;

  @Override
  public void persist(@NotNull @NonNull CodebaseDocument... codebaseDocuments) {
    log.info("persist documents: {}", (Object) codebaseDocuments);
    val documents = Arrays
      .stream(codebaseDocuments)
      .map(codebaseDocument -> {
        try {
          val document = FileSystemDocumentLoader.loadDocument(
            codebaseDocument.getAbsolutePath(),
            textDocumentParser
          );
          document
            .metadata()
            .add(
              "relative_file_path",
              codebaseDocument.getRelativePath().toString()
            )
            .add("mime_type", codebaseDocument.getMimeType());
          return document;
        } catch (RuntimeException runtimeException) {
          log.trace("Error loading document", runtimeException);
          return null;
        }
      })
      .filter(Objects::nonNull)
      .toList();

    log.info("ingest documents: {}", documents.size());
    embeddingStoreIngestor.ingest(documents);
  }
}
