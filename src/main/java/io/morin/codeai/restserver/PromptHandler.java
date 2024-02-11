package io.morin.codeai.restserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.morin.codeai.api.CodeAi;
import io.morin.codeai.api.SendMessageCommand;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class PromptHandler implements HttpHandler {

  @NonNull
  CodeAi codeAi;

  @NonNull
  ContentTypeParser contentTypeParser = ContentTypeParser.builder().build();

  @Override
  @SneakyThrows
  public void handle(HttpExchange exchange) {
    log.info("Handling request {}", exchange.getRequestURI());

    if (!"POST".equals(exchange.getRequestMethod())) {
      log.warn("Unsupported method {}", exchange.getRequestMethod());
      exchange.sendResponseHeaders(405, 0);
      return;
    }

    val contentType = contentTypeParser.parse(
      exchange.getRequestHeaders().getFirst("Content-Type")
    );
    log.debug("Content type is {}", contentType);

    if (!"text/plain".equals(contentType.getValue())) {
      log.warn("Unsupported content type {}", contentType);
      exchange.sendResponseHeaders(415, 0);
      try (
        OutputStreamWriter writer = new OutputStreamWriter(
          exchange.getResponseBody(),
          contentType.getCharset()
        )
      ) {
        writer.write("Unsupported content type\n");
      }
      return;
    }

    val prompt = new String(
      exchange.getRequestBody().readAllBytes(),
      StandardCharsets.UTF_8
    );
    log.debug("Prompt is {}", prompt);

    val command = SendMessageCommand.builder().prompt(prompt).build();
    log.debug("Command is {}", command);

    val result = codeAi.execute(
      SendMessageCommand.builder().prompt(prompt).build()
    );
    log.debug("Result is {}", result);

    log.debug("Sending response");
    exchange.sendResponseHeaders(200, 0);
    try (
      OutputStreamWriter writer = new OutputStreamWriter(
        exchange.getResponseBody(),
        contentType.getCharset()
      )
    ) {
      writer.write(result.getAnswer());
    }
  }
}
