package io.morin.codeai.restserver;

import io.morin.codeai.api.CodeAi;
import io.morin.codeai.api.CodeAiFactory;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class RestServer {

  @SneakyThrows
  public static void main(String[] args) {
    System.setProperty(
      "org.apache.lucene.store.MMapDirectory.enableMemorySegments",
      "false"
    );

    if (args.length != 1) {
      log.error("Usage: RestServer <codebase path>");
      System.exit(1);
    }

    val codebasePath = Path.of(args[0]);
    if (!codebasePath.toFile().exists()) {
      log.error("Codebase path does not exist: {}", codebasePath);
      System.exit(1);
    }
    if (!codebasePath.toFile().isDirectory()) {
      log.error("Codebase path is not a directory: {}", codebasePath);
      System.exit(1);
    }

    log.info("discovering CodeAI settings for codebase: {}", codebasePath);
    val codeAiPropertiesPath = codebasePath.resolve(
      ".codeai/settings.properties"
    );
    if (!codeAiPropertiesPath.toFile().exists()) {
      if (codeAiPropertiesPath.toFile().getParentFile().mkdirs()) {
        log.trace(
          "Created directory: {}",
          codeAiPropertiesPath.toFile().getParent()
        );
      }
      if (codeAiPropertiesPath.toFile().createNewFile()) {
        log.trace("Created file: {}", codeAiPropertiesPath);
      }
    }

    log.info("loading CodeAI settings from: {}", codeAiPropertiesPath);
    val codeAiProperties = new Properties();
    codeAiProperties.load(new FileReader(codeAiPropertiesPath.toFile()));
    val propertyNames = codeAiProperties.stringPropertyNames();

    val codeAiSettingsBuilder = CodeAi.Settings.builder();
    codeAiSettingsBuilder.codebasePath(codebasePath);
    codeAiSettingsBuilder.codebaseName(
      Optional
        .ofNullable(codeAiProperties.getProperty("codebaseName"))
        .orElse(codebasePath.getFileName().toString())
    );

    if (propertyNames.contains("forceCodebaseScanning")) {
      codeAiSettingsBuilder.forceCodebaseScanning(
        Boolean.parseBoolean(
          codeAiProperties.getProperty("forceCodebaseScanning")
        )
      );
    }

    if (propertyNames.contains("blockedPaths")) {
      codeAiSettingsBuilder.blockedPaths(
        Set.of(codeAiProperties.getProperty("blockedPaths").split(","))
      );
    }

    if (propertyNames.contains("allowedMimeTypes")) {
      codeAiSettingsBuilder.allowedMimeTypes(
        Set.of(codeAiProperties.getProperty("allowedMimeTypes").split(","))
      );
    }

    val codeAiSettings = codeAiSettingsBuilder.build();

    log.info("starting REST server for codebase: {}", codebasePath);
    ServiceLoader
      .load(CodeAiFactory.class)
      .findFirst()
      .map(factory -> factory.create(codeAiSettings))
      .map(codeAi -> PromptHandler.builder().codeAi(codeAi).build())
      .map(promptHandler ->
        HttpServerFactory
          .builder()
          .promptHandler(promptHandler)
          .build()
          .create()
      )
      .orElseThrow(() -> new IllegalStateException("No CodeAiFactory found"))
      .start();
  }
}
