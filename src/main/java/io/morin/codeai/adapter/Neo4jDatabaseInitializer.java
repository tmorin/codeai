package io.morin.codeai.adapter;

import static org.neo4j.configuration.GraphDatabaseSettings.DEFAULT_DATABASE_NAME;

import io.morin.codeai.api.CodeAi;
import java.nio.file.Path;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.configuration.connectors.BoltConnector;
import org.neo4j.configuration.helpers.SocketAddress;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;

/**
 * An initializer for the Neo4J database.
 */
@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Neo4jDatabaseInitializer {

  @NonNull
  @Builder.Default
  Neo4jSettings neo4jSettings = Neo4jSettings.builder().build();

  private static void registerShutdownHook(
    final DatabaseManagementService managementService
  ) {
    // Registers a shutdown hook for the Neo4j instance so that it
    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
    // running application).
    Runtime
      .getRuntime()
      .addShutdownHook(
        new Thread() {
          @Override
          public void run() {
            managementService.shutdown();
          }
        }
      );
  }

  /**
   * Initializes the Neo4J database.
   *
   * @param settings the settings provided by the CodeAi instance.
   * @return the status of the initialization.
   */
  Status initialize(CodeAi.Settings settings) {
    val databasePath = Path.of("target/neo4j", settings.getCodebaseName());

    val alreadyInitialized = databasePath.toFile().exists();

    log.info("start the Neo4J database: {}", databasePath);
    val managementService = new DatabaseManagementServiceBuilder(databasePath)
      .setConfig(BoltConnector.enabled, true)
      .setConfig(
        BoltConnector.listen_address,
        new SocketAddress(neo4jSettings.getHost(), neo4jSettings.getPort())
      )
      .build();
    managementService.database(DEFAULT_DATABASE_NAME);
    registerShutdownHook(managementService);
    log.info("Neo4J database started");

    return alreadyInitialized ? Status.ALREADY_EXISTS : Status.CREATED;
  }

  enum Status {
    CREATED,
    ALREADY_EXISTS,
  }
}
