package dev.pretti.prtminetreasures.configs.types;


import dev.pretti.prtminetreasures.configs.interfaces.IConfigSetup;
import dev.pretti.prtminetreasures.configs.interfaces.IOptionsConfig;
import dev.pretti.prtminetreasures.configs.loaders.OptionsLoader;
import dev.pretti.prtminetreasures.configs.setups.DefaultConfigSetup;

public class OptionsConfig implements IOptionsConfig
{
  private boolean removeVanillaDrops;
  private int     treasuresLimit;
  private boolean dropToInventory;
  private boolean discardExcess;

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
  public boolean isRemoveVanillaDrops()
  {
    return removeVanillaDrops;
  }

  @Override
  public void setRemoveVanillaDrops(Boolean removeVanillaDrops)
  {
    this.removeVanillaDrops = removeVanillaDrops;
  }

  @Override
  public int getTreasuresLimit()
  {
    return treasuresLimit;
  }

  @Override
  public void setTreasuresLimit(int treasuresLimit)
  {
    this.treasuresLimit = treasuresLimit;
  }

  @Override
  public boolean isDropToInventory()
  {
    return dropToInventory;
  }

  @Override
  public void setDropToInventory(boolean dropToInventory)
  {
    this.dropToInventory = dropToInventory;
  }

  @Override
  public boolean isDiscardExcess()
  {
    return discardExcess;
  }

  @Override
  public void setDiscardExcess(boolean discardExcess)
  {
    this.discardExcess = discardExcess;
  }
}
