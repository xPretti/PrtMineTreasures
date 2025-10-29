package dev.pretti.prtminetreasures.configs.types;


import dev.pretti.prtminetreasures.configs.interfaces.IConfigSetup;
import dev.pretti.prtminetreasures.configs.interfaces.IDependenciesConfig;
import dev.pretti.prtminetreasures.configs.loaders.DependenciesLoader;
import dev.pretti.prtminetreasures.configs.setups.DefaultConfigSetup;

public class DependenciesConfig implements IDependenciesConfig
{
  private boolean useServerNBT;

  /**
   * Método de carregamento
   */
  @Override
  public boolean load(IConfigSetup configSetup)
  {
    if(configSetup instanceof DefaultConfigSetup)
      {
        DefaultConfigSetup defaultConfigSetup = (DefaultConfigSetup) configSetup;
        return new DependenciesLoader(defaultConfigSetup).load(this);
      }
    return false;
  }


  /**
   * Retornos e Definições
   */
  @Override
  public boolean useServerNBT()
  {
    return useServerNBT;
  }

  @Override
  public void setUseServerNBT(Boolean serverNBT)
  {
    this.useServerNBT = serverNBT;
  }
}
