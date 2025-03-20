package dev.pretti.prtminetreasures.configs.types;


import dev.pretti.prtminetreasures.configs.interfaces.IConfigSetup;
import dev.pretti.prtminetreasures.configs.interfaces.IMessagesConfig;
import dev.pretti.prtminetreasures.configs.loaders.MessagesLoader;
import dev.pretti.prtminetreasures.configs.setups.DefaultConfigSetup;

import java.util.List;

public class MessagesConfig implements IMessagesConfig
{
  private String       noPermissionMessage;
  private String       reloadMessage;
  private String       reloadErrorMessage;
  private String       playerOnlyMessage;
  private List<String> helpMessages;

  private String       noHandItemMessage;
  private String       loreFormatMessage;
  private String       loreFormatEmptyMessage;
  private String       metaFormatMessage;
  private String       metaFormatEmptyMessage;
  private String       enchantFormatMessage;
  private String       enchantFormatEmptyMessage;
  private String       flagFormatMessage;
  private String       flagFormatEmptyMessage;
  private String       nameMessage;
  private String       nameEmptyMessage;
  private List<String> infoMessage;

  private String       blockNoLookMessage;
  private List<String> blockInfoMessage;

  private String       crateInventoryTitleMessage;
  private String       crateOwnerOnlyMessage;
  private String       crateEmptyMessage;
  private String       crateSpawnActionBarMessage;
  private List<String> crateSpawnTitleMessage;
  private List<String> crateSpawnChatMessage;
  private String       crateCollectActionBarMessage;
  private List<String> crateCollectTitleMessage;
  private List<String> crateCollectChatMessage;
  private String       crateTimerActionBarMessage;


  /**
   * Método de carregamento das mensagens
   */
  @Override
  public boolean load(IConfigSetup configSetup)
  {
    if(configSetup instanceof DefaultConfigSetup)
      {
        DefaultConfigSetup defaultConfigSetup = (DefaultConfigSetup) configSetup;
        return (new MessagesLoader(defaultConfigSetup).load(this));
      }
    return false;
  }

  /**
   * Retornos das mensagens
   */
  public String getNoPermissionMessage()
  {
    return noPermissionMessage;
  }

  public void setNoPermissionMessage(String noPermissionMessage)
  {
    this.noPermissionMessage = noPermissionMessage;
  }

  public String getReloadMessage()
  {
    return reloadMessage;
  }

  public void setReloadMessage(String reloadMessage)
  {
    this.reloadMessage = reloadMessage;
  }

  public String getReloadErrorMessage()
  {
    return reloadErrorMessage;
  }

  public void setReloadErrorMessage(String reloadErrorMessage)
  {
    this.reloadErrorMessage = reloadErrorMessage;
  }

  @Override
  public String getPlayerOnlyMessage()
  {
    return playerOnlyMessage;
  }

  @Override
  public void setPlayerOnlyMessage(String playerOnlyMessage)
  {
    this.playerOnlyMessage = playerOnlyMessage;
  }

  public List<String> getHelpMessages()
  {
    return helpMessages;
  }

  public void setHelpMessages(List<String> helpMessage)
  {
    this.helpMessages = helpMessage;
  }

  /**
   * Item info
   */
  @Override
  public String getItemNoHandItemMessage()
  {
    return noHandItemMessage;
  }

  @Override
  public void setItemNoHandItemMessage(String noHandItemMessage)
  {
    this.noHandItemMessage = noHandItemMessage;
  }

  @Override
  public String getItemLoreFormatMessage()
  {
    return loreFormatMessage;
  }

  @Override
  public void setItemLoreFormatMessage(String loreFormatMessage)
  {
    this.loreFormatMessage = loreFormatMessage;
  }

  @Override
  public String getItemLoreFormatEmptyMessage()
  {
    return loreFormatEmptyMessage;
  }

  @Override
  public void setItemLoreFormatEmptyMessage(String loreFormatEmptyMessage)
  {
    this.loreFormatEmptyMessage = loreFormatEmptyMessage;
  }

  @Override
  public String getItemMetaFormatMessage()
  {
    return metaFormatMessage;
  }

  @Override
  public void setItemMetaFormatMessage(String metaFormatMessage)
  {
    this.metaFormatMessage = metaFormatMessage;
  }

  @Override
  public String getItemMetaFormatEmptyMessage()
  {
    return metaFormatEmptyMessage;
  }

  @Override
  public void setItemMetaFormatEmptyMessage(String metaFormatEmptyMessage)
  {
    this.metaFormatEmptyMessage = metaFormatEmptyMessage;
  }

  @Override
  public String getItemEnchantFormatMessage()
  {
    return enchantFormatMessage;
  }

  @Override
  public void setItemEnchantFormatMessage(String enchantFormatMessage)
  {
    this.enchantFormatMessage = enchantFormatMessage;
  }

  @Override
  public String getItemEnchantFormatEmptyMessage()
  {
    return enchantFormatEmptyMessage;
  }

  @Override
  public void setItemEnchantFormatEmptyMessage(String enchantFormatEmptyMessage)
  {
    this.enchantFormatEmptyMessage = enchantFormatEmptyMessage;
  }

  @Override
  public String getItemFlagFormatMessage()
  {
    return flagFormatMessage;
  }

  @Override
  public void setItemFlagFormatMessage(String flagFormatMessage)
  {
    this.flagFormatMessage = flagFormatMessage;
  }

  @Override
  public String getItemFlagFormatEmptyMessage()
  {
    return flagFormatEmptyMessage;
  }

  @Override
  public void setItemFlagFormatEmptyMessage(String flagFormatEmptyMessage)
  {
    this.flagFormatEmptyMessage = flagFormatEmptyMessage;
  }

  @Override
  public String getItemNameFormatMessage()
  {
    return nameMessage;
  }

  @Override
  public void setItemNameFormatMessage(String nameMessage)
  {
    this.nameMessage = nameMessage;
  }

  @Override
  public String getItemNameFormatEmptyMessage()
  {
    return nameEmptyMessage;
  }

  @Override
  public void setItemNameFormatEmptyMessage(String nameEmptyMessage)
  {
    this.nameEmptyMessage = nameEmptyMessage;
  }

  @Override
  public List<String> getItemInfoMessage()
  {
    return infoMessage;
  }

  @Override
  public void setItemInfoMessage(List<String> infoMessage)
  {
    this.infoMessage = infoMessage;
  }

  /**
   * Block info
   */
  @Override
  public String getBlockNoLookMessage()
  {
    return blockNoLookMessage;
  }

  @Override
  public void setBlockNoLookMessage(String noLookMessage)
  {
    this.blockNoLookMessage = noLookMessage;
  }

  @Override
  public List<String> getBlockInfoMessage()
  {
    return blockInfoMessage;
  }

  @Override
  public void setBlockInfoMessage(List<String> infoMessage)
  {
    this.blockInfoMessage = infoMessage;
  }

  /**
   * Crates
   */
  @Override
  public String getCrateInventoryTitleMessage()
  {
    return crateInventoryTitleMessage;
  }

  @Override
  public void setCrateInventoryTitleMessage(String name)
  {
    this.crateInventoryTitleMessage = name;
  }

  @Override
  public String getCrateOwnerOnlyMessage()
  {
    return crateOwnerOnlyMessage;
  }

  @Override
  public void setCrateOwnerOnlyMessage(String name)
  {
    this.crateOwnerOnlyMessage = name;
  }

  @Override
  public String getCrateEmptyMessage()
  {
    return crateEmptyMessage;
  }

  @Override
  public void setCrateEmptyMessage(String name)
  {
    this.crateEmptyMessage = name;
  }

  @Override
  public String getCrateSpawnActionBarMessage()
  {
    return crateSpawnActionBarMessage;
  }

  @Override
  public void setCrateSpawnActionBarMessage(String name)
  {
    this.crateSpawnActionBarMessage = name;
  }

  @Override
  public List<String> getCrateSpawnTitleMessage()
  {
    return crateSpawnTitleMessage;
  }

  @Override
  public void setCrateSpawnTitleMessage(List<String> name)
  {
    this.crateSpawnTitleMessage = name;
  }

  @Override
  public List<String> getCrateSpawnChatMessage()
  {
    return crateSpawnChatMessage;
  }

  @Override
  public void setCrateSpawnChatMessage(List<String> name)
  {
    this.crateSpawnChatMessage = name;
  }

  @Override
  public String getCrateCollectActionBarMessage()
  {
    return crateCollectActionBarMessage;
  }

  @Override
  public void setCrateCollectActionBarMessage(String name)
  {
    this.crateCollectActionBarMessage = name;
  }

  @Override
  public List<String> getCrateCollectTitleMessage()
  {
    return crateCollectTitleMessage;
  }

  @Override
  public void setCrateCollectTitleMessage(List<String> name)
  {
    this.crateCollectTitleMessage = name;
  }

  @Override
  public List<String> getCrateCollectChatMessage()
  {
    return crateCollectChatMessage;
  }

  @Override
  public void setCrateCollectChatMessage(List<String> name)
  {
    this.crateCollectChatMessage = name;
  }

  @Override
  public String getCrateTimerActionBarMessage()
  {
    return crateTimerActionBarMessage;
  }

  @Override
  public void setCrateTimerActionBarMessage(String name)
  {
    this.crateTimerActionBarMessage = name;
  }
}
