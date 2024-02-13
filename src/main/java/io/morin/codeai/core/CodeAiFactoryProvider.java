package io.morin.codeai.core;

import io.morin.codeai.api.CodeAi;
import io.morin.codeai.api.CodeAiFactory;
import java.util.ServiceLoader;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * The provider for the {@link CodeAiFactory} when using the ServiceLoader API.
 */
@Slf4j
public class CodeAiFactoryProvider implements CodeAiFactory {

  @Override
  public @NonNull CodeAi create(@NonNull CodeAi.Settings settings) {
    log.debug(
      "Loading adapter configuration factory and creating adapter configuration"
    );
    val adapterConfiguration = ServiceLoader
      .load(AdapterConfigurationFactory.class)
      .findFirst()
      .map(factory -> factory.create(settings))
      .orElseThrow(() ->
        new IllegalStateException("No adapter configuration factory found")
      );

    return CodeAiCoreFactory
      .builder()
      .chat(adapterConfiguration.getChat())
      .codebaseDocumentRepository(
        adapterConfiguration.getCodebaseDocumentRepository()
      )
      .scanningChecker(adapterConfiguration.getScanningChecker())
      .build()
      .create(settings);
  }
}
