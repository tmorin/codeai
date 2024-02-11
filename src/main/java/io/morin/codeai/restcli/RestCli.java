package io.morin.codeai.restcli;

import io.morin.codeai.fwk.SettingReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Scanner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
public class RestCli {

  @SuppressWarnings({ "java:S106" })
  @SneakyThrows
  public static void main(String[] args) {
    try {
      val scanner = new Scanner(System.in);

      while (true) {
        log.info("Please input a line");
        val prompt = scanner.nextLine();

        if (prompt.equals("exit")) {
          break;
        }

        val url = URI.create(
          SettingReader.readString(
            "codeai.rest_cli.prompt_url",
            "http://localhost:9090/prompt"
          )
        );

        val con = (HttpURLConnection) url.toURL().openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty(
          "Content-Type",
          String.format(
            "text/plain; charset=%s",
            Charset.defaultCharset().name()
          )
        );
        con.setDoOutput(true);
        con.getOutputStream().write(prompt.getBytes());
        con.getOutputStream().close();

        val responseCode = con.getResponseCode();
        if (responseCode == 200) {
          con.getInputStream().transferTo(System.out);
        } else {
          log.error(
            "Failed to send prompt with {}, try again! :)",
            responseCode
          );
        }
        System.out.println();
        System.out.println();

        con.disconnect();
      }
    } catch (IllegalStateException e) {
      log.info("System.in was closed; exiting");
    }
  }
}
