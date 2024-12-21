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
  private String       nameMessage;
  private String       nameEmptyMessage;
  private List<String> infoMessage;


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

  @Override
  public String getNoHandItemMessage()
  {
    return noHandItemMessage;
  }

  @Override
  public void setNoHandItemMessage(String noHandItemMessage)
  {
    this.noHandItemMessage = noHandItemMessage;
  }

  @Override
  public String getLoreFormatMessage()
  {
    return loreFormatMessage;
  }

  @Override
  public void setLoreFormatMessage(String loreFormatMessage)
  {
    this.loreFormatMessage = loreFormatMessage;
  }

  @Override
  public String getLoreFormatEmptyMessage()
  {
    return loreFormatEmptyMessage;
  }

  @Override
  public void setLoreFormatEmptyMessage(String loreFormatEmptyMessage)
  {
    this.loreFormatEmptyMessage = loreFormatEmptyMessage;
  }

  @Override
  public String getMetaFormatMessage()
  {
    return metaFormatMessage;
  }

  @Override
  public void setMetaFormatMessage(String metaFormatMessage)
  {
    this.metaFormatMessage = metaFormatMessage;
  }

  @Override
  public String getMetaFormatEmptyMessage()
  {
    return metaFormatEmptyMessage;
  }

  @Override
  public void setMetaFormatEmptyMessage(String metaFormatEmptyMessage)
  {
    this.metaFormatEmptyMessage = metaFormatEmptyMessage;
  }

  @Override
  public String getNameFormatMessage()
  {
    return nameMessage;
  }

  @Override
  public void setNameFormatMessage(String nameMessage)
  {
    this.nameMessage = nameMessage;
  }

  @Override
  public String getNameFormatEmptyMessage()
  {
    return nameEmptyMessage;
  }

  @Override
  public void setNameFormatEmptyMessage(String nameEmptyMessage)
  {
    this.nameEmptyMessage = nameEmptyMessage;
  }

  @Override
  public List<String> getInfoMessage()
  {
    return infoMessage;
  }

  @Override
  public void setInfoMessage(List<String> infoMessage)
  {
    this.infoMessage = infoMessage;
  }
}
