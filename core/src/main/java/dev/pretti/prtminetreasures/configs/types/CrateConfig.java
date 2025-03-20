package dev.pretti.prtminetreasures.configs.types;


import dev.pretti.prtminetreasures.configs.interfaces.IConfigSetup;
import dev.pretti.prtminetreasures.configs.interfaces.ICrateConfig;
import dev.pretti.prtminetreasures.configs.loaders.CrateLoader;
import dev.pretti.prtminetreasures.configs.setups.DefaultConfigSetup;
import dev.pretti.prtminetreasures.datatypes.SoundType;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

public class CrateConfig implements ICrateConfig
{
  private Material  blockType;
  private int       destroySeconds;
  private boolean   ownerOnly;
  private int       inventoryRows;
  private SoundType openSound;
  private SoundType closeSound;

  boolean spawnActionBarMessage;
  boolean spawnTitleMessage;
  boolean spawnChatMessage;
  boolean collectActionBarMessage;
  boolean collectTitleMessage;
  boolean collectChatMessage;
  boolean timerActionBarMessage;
  int     titleFadeIn;
  int     titleFadeOut;
  int     titleStay;

  /**
   * Método de carregamento
   */
  @Override
  public boolean load(IConfigSetup configSetup)
  {
    if(configSetup instanceof DefaultConfigSetup)
      {
        DefaultConfigSetup defaultConfigSetup = (DefaultConfigSetup) configSetup;
        return new CrateLoader(defaultConfigSetup).load(this);
      }
    return false;
  }

  /**
   * Retornos e Definições
   */
  @Override
  public Material getBlockType()
  {
    return blockType;
  }

  @Override
  public void setBlockType(Material blockType)
  {
    this.blockType = blockType;
  }

  @Override
  public int getDestroySeconds()
  {
    return destroySeconds;
  }

  @Override
  public void setDestroySeconds(int destroySeconds)
  {
    this.destroySeconds = destroySeconds;
  }

  @Override
  public boolean isOwnerOnly()
  {
    return ownerOnly;
  }

  @Override
  public void setOwnerOnly(boolean ownerOnly)
  {
    this.ownerOnly = ownerOnly;
  }

  @Override
  public int getInventoryRows()
  {
    return inventoryRows;
  }

  @Override
  public void setInventoryRows(int rows)
  {
    this.inventoryRows = rows;
  }

  @Override
  public @Nullable SoundType getOpenSound()
  {
    return openSound;
  }

  @Override
  public void setOpenSound(@Nullable SoundType openSound)
  {
    this.openSound = openSound;
  }

  @Override
  public @Nullable SoundType getCloseSound()
  {
    return closeSound;
  }

  @Override
  public void setCloseSound(@Nullable SoundType closeSound)
  {
    this.closeSound = closeSound;
  }

  @Override
  public boolean isSpawnActionBarMessage()
  {
    return spawnActionBarMessage;
  }

  @Override
  public void setSpawnActionBarMessage(boolean spawnActionBarMessage)
  {
    this.spawnActionBarMessage = spawnActionBarMessage;
  }

  @Override
  public boolean isSpawnTitleMessage()
  {
    return spawnTitleMessage;
  }

  @Override
  public void setSpawnTitleMessage(boolean spawnTitleMessage)
  {
    this.spawnTitleMessage = spawnTitleMessage;
  }

  @Override
  public boolean isSpawnChatMessage()
  {
    return spawnChatMessage;
  }

  @Override
  public void setSpawnChatMessage(boolean spawnChatMessage)
  {
    this.spawnChatMessage = spawnChatMessage;
  }

  @Override
  public boolean isCollectActionBarMessage()
  {
    return collectActionBarMessage;
  }

  @Override
  public void setCollectActionBarMessage(boolean collectActionBarMessage)
  {
    this.collectActionBarMessage = collectActionBarMessage;
  }

  @Override
  public boolean isCollectTitleMessage()
  {
    return collectTitleMessage;
  }

  @Override
  public void setCollectTitleMessage(boolean collectTitleMessage)
  {
    this.collectTitleMessage = collectTitleMessage;
  }

  @Override
  public boolean isCollectChatMessage()
  {
    return collectChatMessage;
  }

  @Override
  public void setCollectChatMessage(boolean collectChatMessage)
  {
    this.collectChatMessage = collectChatMessage;
  }

  @Override
  public boolean isTimerActionBarMessage()
  {
    return timerActionBarMessage;
  }

  @Override
  public void setTimerActionBarMessage(boolean timerActionBarMessage)
  {
    this.timerActionBarMessage = timerActionBarMessage;
  }

  @Override
  public int getTitleFadeIn()
  {
    return titleFadeIn;
  }

  @Override
  public void setTitle(int titleFadeIn, int titleFadeOut, int titleStay)
  {
    this.titleFadeIn = titleFadeIn;
    this.titleFadeOut = titleFadeOut;
    this.titleStay = titleStay;
  }

  @Override
  public int getTitleFadeOut()
  {
    return titleFadeOut;
  }

  @Override
  public int getTitleStay()
  {
    return titleStay;
  }
}
