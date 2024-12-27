package dev.pretti.prtminetreasures.versions;

import dev.pretti.prtminetreasures.versions.interfaces.IInventoryVersion;
import dev.pretti.prtminetreasures.versions.loaders.InventoryVersionLoader;

public class VersionsManager
{
  private static VersionsManager instance;

  private final IInventoryVersion inventoryVersion;

  public VersionsManager()
  {
    this.inventoryVersion = new InventoryVersionLoader().getVersion();

    instance = this;
  }

  /**
   * retorna a instância
   */
  public static VersionsManager getInstance()
  {
    return instance;
  }

  /**
   * Retorna as versões
   */
  public IInventoryVersion getInventoryVersion()
  {
    return inventoryVersion;
  }
}
