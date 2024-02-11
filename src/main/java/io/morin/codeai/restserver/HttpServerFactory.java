package io.morin.codeai.restserver;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class HttpServerFactory {

  @NonNull
  @Builder.Default
  RestServerSettings restServerSettings = RestServerSettings.builder().build();

  @NonNull
  PromptHandler promptHandler;

  @SneakyThrows
  HttpServer create() {
    log.info(
      "Creating REST server on {}:{}",
      restServerSettings.getHost(),
      restServerSettings.getPort()
    );

    val httpServer = HttpServer.create();

    httpServer.createContext("/prompt", promptHandler);

    httpServer.bind(
      new InetSocketAddress(
        restServerSettings.getHost(),
        restServerSettings.getPort()
      ),
      0
    );

    return httpServer;
  }
}
