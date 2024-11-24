package dev.pretti.prtminetreasures.configs.types;


import dev.pretti.prtminetreasures.configs.interfaces.IConfigSetup;
import dev.pretti.prtminetreasures.configs.interfaces.IOptionsConfig;
import dev.pretti.prtminetreasures.configs.loaders.OptionsLoader;
import dev.pretti.prtminetreasures.configs.setups.DefaultConfigSetup;

public class OptionsConfig implements IOptionsConfig
{
  private int    maxDurability;
  private double durabilityMinChance;

  /**
   * Método de carregamento
   */
  @Override
  public boolean load(IConfigSetup configSetup)
  {
    if(configSetup instanceof DefaultConfigSetup)
      {
        DefaultConfigSetup defaultConfigSetup = (DefaultConfigSetup) configSetup;
        return new OptionsLoader(defaultConfigSetup, defaultConfigSetup).load(this);
      }
    return false;
  }

  /**
   * Retornos e Definições
   */
  @Override
  public int getMaxDurability()
  {
    return this.maxDurability;
  }

  @Override
  public void setMaxDurability(int maxDurability)
  {
    this.maxDurability = maxDurability;
  }

  @Override
  public double getDurabilityMinChance()
  {
    return this.durabilityMinChance;
  }

  @Override
  public void setDurabilityMinChance(double durabilityMinChance)
  {
    this.durabilityMinChance = durabilityMinChance;
  }
}
