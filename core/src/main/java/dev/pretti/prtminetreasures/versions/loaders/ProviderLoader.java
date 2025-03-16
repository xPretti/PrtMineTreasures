package dev.pretti.prtminetreasures.versions.loaders;

import dev.pretti.prtminetreasures.utils.SystemUtils;
import dev.pretti.prtminetreasures.utils.VersionsProviderUtils;
import dev.pretti.prtminetreasures.versions.providers.IBlockfaceProvider;
import dev.pretti.prtminetreasures.versions.providers.IInventoryProvider;

public class ProviderLoader
{
  public IInventoryProvider getInventoryProvider()
  {
    int version = SystemUtils.getServerVersionInt();
    if(version == 108)
      {
        return getInventoryProvider("v1_8");
      }
    else if(version >= 109)
      {
        return getInventoryProvider("v1_9");
      }
    return null;
  }

  public IBlockfaceProvider getBlockfaceProvider()
  {
    int version = SystemUtils.getServerVersionInt();
    if(version >= 108 && version < 113)
      {
        return getBlockfaceVersion("v1_8");
      }
    else if(version >= 113)
      {
        return getBlockfaceVersion("v1_13");
      }
    return null;
  }

  /**
  * Classes
  */

  public IInventoryProvider getInventoryProvider(String version)
  {
    return VersionsProviderUtils.getClassVersion(version, "InventoryProvider", IInventoryProvider.class);
  }

  public IBlockfaceProvider getBlockfaceVersion(String version)
  {
    return VersionsProviderUtils.getClassVersion(version, "BlockfaceProvider", IBlockfaceProvider.class);
  }
}
