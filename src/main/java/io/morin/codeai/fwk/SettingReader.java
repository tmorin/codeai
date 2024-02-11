package io.morin.codeai.fwk;

import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SettingReader {

  public String readString(String key) {
    return Optional
      .ofNullable(System.getenv(key))
      .orElse(System.getProperty(key));
  }

  public String readString(String key, String defaultValue) {
    return Optional
      .ofNullable(System.getenv(key))
      .orElse(System.getProperty(key, defaultValue));
  }

  public int readInt(String key, int defaultValue) {
    return Optional
      .ofNullable(System.getenv(key))
      .map(Integer::parseInt)
      .orElse(
        Integer.parseInt(
          System.getProperty(key, Integer.toString(defaultValue))
        )
      );
  }
}
