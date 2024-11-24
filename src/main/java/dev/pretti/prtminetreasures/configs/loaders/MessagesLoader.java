package dev.pretti.prtminetreasures.configs.loaders;

import dev.pretti.prtminetreasures.configs.interfaces.IConfig;
import dev.pretti.prtminetreasures.configs.interfaces.IConfigSetup;
import dev.pretti.prtminetreasures.configs.interfaces.IMessagesConfig;
import dev.pretti.prtminetreasures.configs.loaders.interfaces.IConfigLoader;
import dev.pretti.prtminetreasures.utils.FileConfigurationUtils;

public class MessagesLoader implements IConfigLoader
{
  private final IConfigSetup config;
  private final IConfigSetup defaultConfig;

  /**
   * Construtor da classe
   */
  public MessagesLoader(IConfigSetup config, IConfigSetup defaultConfig)
  {
    this.config        = config;
    this.defaultConfig = defaultConfig;
  }

  /**
   * Método de carregamento
   * - Retorna a estrutura contendo as mensagens.
   */
  @Override
  public boolean load(IConfig outputConfig)
  {
    if(outputConfig != null)
      {
        if(outputConfig instanceof IMessagesConfig)
          {
            IMessagesConfig output = (IMessagesConfig) outputConfig;
            output.setNoPermissionMessage(FileConfigurationUtils.getString(config.getConfig(), defaultConfig.getConfig(), "messages.no-permission"));
            output.setReloadMessage(FileConfigurationUtils.getString(config.getConfig(), defaultConfig.getConfig(), "messages.reload"));
            output.setReloadErrorMessage(FileConfigurationUtils.getString(config.getConfig(), defaultConfig.getConfig(), "messages.reload-error"));
            output.setHelpMessages(FileConfigurationUtils.getStringList(config.getConfig(), defaultConfig.getConfig(), "messages.help"));
            return true;
          }
      }
    return false;
  }
}
