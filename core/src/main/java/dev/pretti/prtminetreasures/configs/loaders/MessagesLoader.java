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
            output.setPlayerOnlyMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.player-only")));
            output.setHelpMessages(ReplaceUtils.toColorMessage(FileConfigurationUtils.getStringList(config.getConfig(), config.getDefaultConfig(), "messages.help")));

            output.setItemNoHandItemMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.no-hand-item")));
            output.setItemLoreFormatMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.lore-format")));
            output.setItemLoreFormatEmptyMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.lore-format-empty")));
            output.setItemMetaFormatMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.meta-format")));
            output.setItemMetaFormatEmptyMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.meta-format-empty")));
            output.setItemEnchantFormatMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.enchant-format")));
            output.setItemEnchantFormatEmptyMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.enchant-format-empty")));
            output.setItemFlagFormatMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.flag-format")));
            output.setItemFlagFormatEmptyMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.flag-format-empty")));
            output.setItemNameFormatMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.name-format")));
            output.setItemNameFormatEmptyMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.item-info.name-format-empty")));
            output.setItemInfoMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getStringList(config.getConfig(), config.getDefaultConfig(), "messages.item-info.info-format")));

            output.setBlockNoLookMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getString(config.getConfig(), config.getDefaultConfig(), "messages.block-info.no-look-block")));
            output.setBlockInfoMessage(ReplaceUtils.toColorMessage(FileConfigurationUtils.getStringList(config.getConfig(), config.getDefaultConfig(), "messages.block-info.info-format")));
            return true;
          }
      }
    return false;
  }
}
