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
  private List<String> helpMessages;


  /**
   * Método de carregamento das mensagens
   */
  @Override
  public boolean load(IConfigSetup configSetup)
  {
    if(configSetup instanceof DefaultConfigSetup)
      {
        DefaultConfigSetup defaultConfigSetup = (DefaultConfigSetup) configSetup;
        return (new MessagesLoader(defaultConfigSetup, defaultConfigSetup).load(this));
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

  public List<String> getHelpMessages()
  {
    return helpMessages;
  }

  public void setHelpMessages(List<String> helpMessage)
  {
    this.helpMessages = helpMessage;
  }
}
