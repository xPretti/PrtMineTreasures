package dev.pretti.prtminetreasures.versions;

import dev.pretti.prtminetreasures.versions.providers.IBlockfaceProvider;
import dev.pretti.prtminetreasures.versions.providers.IInventoryProvider;
import dev.pretti.prtminetreasures.versions.loaders.ProviderLoader;

public class VersionsManager
{
  private static VersionsManager instance;

  private final IInventoryProvider inventoryVersion;
  private final IBlockfaceProvider blockfaceVersion;

  public VersionsManager()
  {
    this.inventoryVersion = new ProviderLoader().getInventoryProvider();
    this.blockfaceVersion = new ProviderLoader().getBlockfaceProvider();

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

  public IBlockfaceProvider getBlockfaceVersion()
  {
    return blockfaceVersion;
  }
}
