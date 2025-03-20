package dev.pretti.prtminetreasures.structs;

import dev.pretti.prtminetreasures.crates.interfaces.ICrate;

public class CratePlayerStats
{
  public final long      interactData;
  public final ICrate<?> interactCrate;

  public CratePlayerStats(long interactData, ICrate<?> interactCrate)
  {
    this.interactData  = interactData;
    this.interactCrate = interactCrate;
  }
}
