package io.morin.codeai.adapter;

import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.neo4j.Neo4jEmbeddingStore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.GraphDatabase;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Langchain4jConfigurationFactory {

  @NonNull
  @Builder.Default
  MistralSettings mistralSettings = MistralSettings.builder().build();

  @NonNull
  @Builder.Default
  OllamaSettings ollamaSettings = OllamaSettings.builder().build();

  @NonNull
  @Builder.Default
  Neo4jSettings neo4jSettings = Neo4jSettings.builder().build();

  @NonNull
  @Builder.Default
  Langchain4jSettings langchain4jSettings = Langchain4jSettings
    .builder()
    .build();

  private ChatLanguageModel createChatLanguageModel() {
    log.info(
      "create the LLM configuration: {}",
      langchain4jSettings.getIntegration()
    );
    return switch (langchain4jSettings.getIntegration()) {
      case MISTRAL -> MistralAiChatModel
        .builder()
        .apiKey(mistralSettings.getApiKey())
        .modelName(mistralSettings.getModelName())
        .build();
      case OLLAMA -> OllamaChatModel
        .builder()
        .baseUrl(ollamaSettings.getBaseUrl())
        .modelName(ollamaSettings.getModelName())
        .build();
    };
  }

  Langchain4jConfiguration create() {
    log.info("create the embedding configuration");
    val embeddingModel = new AllMiniLmL6V2QuantizedEmbeddingModel();

    val neo4jStore = Neo4jEmbeddingStore
      .builder()
      .driver(
        GraphDatabase.driver(
          String.format(
            "bolt://%s:%d",
            neo4jSettings.getHost(),
            neo4jSettings.getPort()
          )
        )
      )
      .dimension(langchain4jSettings.getDimension())
      .build();

    val ingestor = EmbeddingStoreIngestor
      .builder()
      .documentSplitter(DocumentSplitters.recursive(500, 0))
      .embeddingModel(embeddingModel)
      .embeddingStore(neo4jStore)
      .build();

    log.info("create the assistant service");
    val assistant = AiServices
      .builder(Assistant.class)
      .contentRetriever(
        EmbeddingStoreContentRetriever
          .builder()
          .embeddingModel(embeddingModel)
          .embeddingStore(neo4jStore)
          .build()
      )
      .chatLanguageModel(createChatLanguageModel())
      .chatMemoryProvider(memoryId ->
        MessageWindowChatMemory.withMaxMessages(
          langchain4jSettings.getMaxMemoryMessages()
        )
      )
      .build();

    return Langchain4jConfiguration
      .builder()
      .ingestor(ingestor)
      .assistant(assistant)
      .build();
  }
}
