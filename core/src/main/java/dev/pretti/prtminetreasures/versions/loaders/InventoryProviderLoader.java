package dev.pretti.prtminetreasures.versions.loaders;

import dev.pretti.prtminetreasures.utils.SystemUtils;
import dev.pretti.prtminetreasures.utils.VersionsProviderUtils;
import dev.pretti.prtminetreasures.versions.providers.IInventoryProvider;

public class InventoryProviderLoader
{
  public IInventoryProvider getVersion()
  {
    int version = SystemUtils.getServerVersionInt();
    if(version == 108)
      {
        return VersionsProviderUtils.getInventoryVersion("v1_8");
      }
    else if(version >= 109)
      {
        return VersionsProviderUtils.getInventoryVersion("v1_9");
      }
    return null;
  }
}
