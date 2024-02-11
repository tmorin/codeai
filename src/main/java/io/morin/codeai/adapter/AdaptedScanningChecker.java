package io.morin.codeai.adapter;

import io.morin.codeai.core.ScanningChecker;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
class AdaptedScanningChecker implements ScanningChecker {

  boolean notYetScanned;
}
