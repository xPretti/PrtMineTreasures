package dev.pretti.prtminetreasures.integrations.types;

import de.tr7zw.changeme.nbtapi.NBT;
import dev.pretti.prtminetreasures.configs.interfaces.IDependenciesConfig;
import dev.pretti.prtminetreasures.integrations.base.Integration;
import dev.pretti.prtminetreasures.utils.LogUtils;

public class NbtApiIntegration extends Integration
{
  private final IDependenciesConfig dependenciesConfig;

  /**
   * Construtor da classe
   */
  public NbtApiIntegration(IDependenciesConfig dependenciesConfig)
  {
    super("NBTAPI", false);
    this.dependenciesConfig = dependenciesConfig;
  }

  /**
   * Implementação do carregamento
   */
  @Override
  public boolean load()
  {
    if(!dependenciesConfig.useServerNBT())
      {
        if(tryLoadShaded())
          {
            return true;
          }
      }
    boolean loadByPlugins = super.load();
    if(!isLoaded() && dependenciesConfig.useServerNBT())
      {
        if(tryLoadShaded())
          {
            return true;
          }
      }
    return loadByPlugins;
  }

  private boolean tryLoadShaded()
  {
    if(NBT.preloadApi())
      {
        LogUtils.logSuccess(String.format("%s successful shaded integration.", getLibraryName()));
        setLoaded(true);
        return true;
      }
    LogUtils.logWarn(String.format("%s unsuccessful shaded integration.", getLibraryName()));
    return false;
  }

}
