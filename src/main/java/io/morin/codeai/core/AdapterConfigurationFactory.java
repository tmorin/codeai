package io.morin.codeai.core;

import io.morin.codeai.api.CodeAi;

public interface AdapterConfigurationFactory {
  AdapterConfiguration create(CodeAi.Settings settings);
}
