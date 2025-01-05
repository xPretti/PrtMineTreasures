package dev.pretti.prtminetreasures.versions;

import dev.pretti.prtminetreasures.versions.providers.IInventoryProvider;
import dev.pretti.prtminetreasures.versions.loaders.InventoryProviderLoader;

public class VersionsManager
{
  private static VersionsManager instance;

  private final IInventoryProvider inventoryVersion;

  public VersionsManager()
  {
    this.inventoryVersion = new InventoryProviderLoader().getVersion();

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
  public IInventoryProvider getInventoryVersion()
  {
    return inventoryVersion;
  }
}
