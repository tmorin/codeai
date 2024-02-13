package io.morin.codeai.api;

/**
 * The CodeAiFactory interface is the main entry point for the CodeAI API.
 * <p>
 * An instance of this interface can be obtained from the ServiceLoader by calling {@code ServiceLoader.load(CodeAiFactory.class)}.
 */
public interface CodeAiFactory {
  /**
   * Create a new CodeAi instance.
   *
   * @param settings the settings
   * @return the CodeAi instance
   */
  CodeAi create(CodeAi.Settings settings);
}
