package dev.pretti.prtminetreasures.configs.loaders;

import dev.pretti.prtminetreasures.configs.interfaces.IConfig;
import dev.pretti.prtminetreasures.configs.interfaces.IMessagesConfig;
import dev.pretti.prtminetreasures.configs.loaders.interfaces.IConfigLoader;
import dev.pretti.prtminetreasures.configs.setups.DefaultConfigSetup;
import dev.pretti.prtminetreasures.utils.FileConfigurationUtils;
import dev.pretti.prtminetreasures.utils.ReplaceUtils;

public class MessagesLoader implements IConfigLoader
{
  private final DefaultConfigSetup config;

  /**
   * Construtor da classe
   */
  public MessagesLoader(DefaultConfigSetup config)
  {
    this.config = config;
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
            output.setNoPermissionMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.no-permission")));
            output.setReloadMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.reload")));
            output.setReloadErrorMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.reload-error")));
            output.setHelpMessages(ReplaceUtils.toColorMessage(FileConfigurationUtils.getStringList(config.getConfig(), config.getDefaultConfig(), "messages.help")));

            output.setNoHandItemMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.no-hand-item")));
            output.setLoreFormatMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.lore-format")));
            output.setLoreFormatEmptyMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.lore-format-empty")));
            output.setNameFormatMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.name-format")));
            output.setNameFormatEmptyMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.name-format-empty")));
            output.setInfoMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getStringList(config.getConfig(), config.getDefaultConfig(), "messages.item-info.info-format")));

            return true;
          }
      }
    return false;
  }
}
