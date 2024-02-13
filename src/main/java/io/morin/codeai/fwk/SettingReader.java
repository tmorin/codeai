package io.morin.codeai.fwk;

import java.util.Optional;
import lombok.experimental.UtilityClass;

/**
 * This class is used to read settings from the environment variables or system properties.
 * It is a utility class and cannot be instantiated.
 */
@UtilityClass
public class SettingReader {

  /**
   * Reads a string from the environment variables or system properties.
   *
   * @param key the key to read
   * @return the value of the key
   */
  public String readString(String key) {
    return Optional
      .ofNullable(System.getenv(key))
      .orElse(System.getProperty(key));
  }

  /**
   * Reads a string from the environment variables or system properties.
   *
   * @param key          the key to read
   * @param defaultValue the default value to return if the key is not found
   * @return the value of the key
   */
  public String readString(String key, String defaultValue) {
    return Optional
      .ofNullable(System.getenv(key))
      .orElse(System.getProperty(key, defaultValue));
  }

  /**
   * Reads an integer from the environment variables or system properties.
   *
   * @param key the key to read
   * @return the value of the key
   */
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
