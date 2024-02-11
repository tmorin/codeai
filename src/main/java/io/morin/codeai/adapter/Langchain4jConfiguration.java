package io.morin.codeai.adapter;

import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder(toBuilder = true)
class Langchain4jConfiguration {

  @NonNull
  Assistant assistant;

  @NonNull
  EmbeddingStoreIngestor ingestor;
}
