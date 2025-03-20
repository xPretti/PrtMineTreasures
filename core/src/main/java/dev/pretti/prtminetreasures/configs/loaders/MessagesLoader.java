package dev.pretti.prtminetreasures.configs.loaders;

import dev.pretti.prtminetreasures.configs.interfaces.IConfig;
import dev.pretti.prtminetreasures.configs.interfaces.IMessagesConfig;
import dev.pretti.prtminetreasures.configs.loaders.interfaces.IConfigLoader;
import dev.pretti.prtminetreasures.configs.setups.DefaultConfigSetup;
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
            output.setNoPermissionMessage(ReplaceUtils.toColorMessage(config.getString("messages.no-permission")));
            output.setReloadMessage(ReplaceUtils.toColorMessage(config.getString("messages.reload")));
            output.setReloadErrorMessage(ReplaceUtils.toColorMessage(config.getString("messages.reload-error")));
            output.setPlayerOnlyMessage(ReplaceUtils.toColorMessage(config.getString("messages.player-only")));
            output.setHelpMessages(ReplaceUtils.toColorMessage(config.getStringList("messages.help")));

            output.setItemNoHandItemMessage(ReplaceUtils.toColorMessage(config.getString("messages.item-info.no-hand-item")));
            output.setItemLoreFormatMessage(ReplaceUtils.toColorMessage(config.getString("messages.item-info.lore-format")));
            output.setItemLoreFormatEmptyMessage(ReplaceUtils.toColorMessage(config.getString("messages.item-info.lore-format-empty")));
            output.setItemMetaFormatMessage(ReplaceUtils.toColorMessage(config.getString("messages.item-info.meta-format")));
            output.setItemMetaFormatEmptyMessage(ReplaceUtils.toColorMessage(config.getString("messages.item-info.meta-format-empty")));
            output.setItemEnchantFormatMessage(ReplaceUtils.toColorMessage(config.getString("messages.item-info.enchant-format")));
            output.setItemEnchantFormatEmptyMessage(ReplaceUtils.toColorMessage(config.getString("messages.item-info.enchant-format-empty")));
            output.setItemFlagFormatMessage(ReplaceUtils.toColorMessage(config.getString("messages.item-info.flag-format")));
            output.setItemFlagFormatEmptyMessage(ReplaceUtils.toColorMessage(config.getString("messages.item-info.flag-format-empty")));
            output.setItemNameFormatMessage(ReplaceUtils.toColorMessage(config.getString("messages.item-info.name-format")));
            output.setItemNameFormatEmptyMessage(ReplaceUtils.toColorMessage(config.getString("messages.item-info.name-format-empty")));
            output.setItemInfoMessage(ReplaceUtils.toColorMessage(config.getStringList("messages.item-info.info-format")));

            output.setBlockNoLookMessage(ReplaceUtils.toColorMessage(config.getString("messages.block-info.no-look-block")));
            output.setBlockInfoMessage(ReplaceUtils.toColorMessage(config.getStringList("messages.block-info.info-format")));

            output.setCrateInventoryTitleMessage(ReplaceUtils.toColorMessage(config.getString("messages.crates.inventory-title")));
            output.setCrateOwnerOnlyMessage(ReplaceUtils.toColorMessage(config.getString("messages.crates.owner-only")));
            output.setCrateEmptyMessage(ReplaceUtils.toColorMessage(config.getString("messages.crates.crate-empty")));

            output.setCrateSpawnActionBarMessage(ReplaceUtils.toColorMessage(config.getString("messages.crates.actions.onSpawn.actionbar")));
            output.setCrateSpawnTitleMessage(ReplaceUtils.toColorMessage(config.getStringList("messages.crates.actions.onSpawn.title")));
            output.setCrateSpawnChatMessage(ReplaceUtils.toColorMessage(config.getStringList("messages.crates.actions.onSpawn.chat")));
            output.setCrateCollectActionBarMessage(ReplaceUtils.toColorMessage(config.getString("messages.crates.actions.onCollect.actionbar")));
            output.setCrateCollectTitleMessage(ReplaceUtils.toColorMessage(config.getStringList("messages.crates.actions.onCollect.title")));
            output.setCrateCollectChatMessage(ReplaceUtils.toColorMessage(config.getStringList("messages.crates.actions.onCollect.chat")));
            output.setCrateTimerActionBarMessage(ReplaceUtils.toColorMessage(config.getString("messages.crates.actions.onTimer.actionbar")));
            return true;
          }
      }
    return false;
  }
}
