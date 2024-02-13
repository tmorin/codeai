package io.morin.codeai.adapter;

import io.morin.codeai.core.ScanningChecker;
import lombok.Builder;
import lombok.Value;

/**
 * An adapter for the {@link ScanningChecker} interface.
 */
@Value
@Builder(toBuilder = true)
class AdaptedScanningChecker implements ScanningChecker {

  boolean notYetScanned;
}
