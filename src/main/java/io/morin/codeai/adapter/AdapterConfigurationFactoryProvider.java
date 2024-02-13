package io.morin.codeai.adapter;

import io.morin.codeai.api.CodeAi;
import io.morin.codeai.core.AdapterConfiguration;
import io.morin.codeai.core.AdapterConfigurationFactory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * An adapter for the {@link AdapterConfigurationFactory} interface.
 */
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdapterConfigurationFactoryProvider
  implements AdapterConfigurationFactory {

  @Override
  public AdapterConfiguration create(CodeAi.Settings settings) {
    val neo4jSettings = Neo4jSettings.builder().build();
    val langchain4jSettings = Langchain4jSettings.builder().build();
    val mistralSettings = MistralSettings.builder().build();
    val ollamaSettings = OllamaSettings.builder().build();

    val dbStatus = Neo4jDatabaseInitializer
      .builder()
      .neo4jSettings(neo4jSettings)
      .build()
      .initialize(settings);

    val scanningChecker = AdaptedScanningChecker
      .builder()
      .notYetScanned(dbStatus.equals(Neo4jDatabaseInitializer.Status.CREATED))
      .build();

    val langchain4jConfiguration = Langchain4jConfigurationFactory
      .builder()
      .langchain4jSettings(langchain4jSettings)
      .mistralSettings(mistralSettings)
      .ollamaSettings(ollamaSettings)
      .neo4jSettings(neo4jSettings)
      .build()
      .create();

    val codebaseDocumentRepository = AdaptedCodebaseDocumentRepository
      .builder()
      .embeddingStoreIngestor(langchain4jConfiguration.getIngestor())
      .build();

    val chat = AdaptedChat
      .builder()
      .assistant(langchain4jConfiguration.getAssistant())
      .build();

    return AdapterConfiguration
      .builder()
      .chat(chat)
      .scanningChecker(scanningChecker)
      .codebaseDocumentRepository(codebaseDocumentRepository)
      .build();
  }
}
