package io.morin.codeai.core;

/**
 * The checker for scanning.
 */
public interface ScanningChecker {
  /**
   * @return true if the codebase is not yet scanned
   */
  boolean isNotYetScanned();
}
