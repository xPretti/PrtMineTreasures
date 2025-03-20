package dev.pretti.prtminetreasures.crates.interfaces;

import org.jetbrains.annotations.NotNull;

public interface ICrateManager
{
  void init();

  void deinit();

  void onUpdate(@NotNull ICrate<?> crate);
}
