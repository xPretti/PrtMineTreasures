package dev.pretti.prtminetreasures.configs.interfaces;

import java.util.List;

public interface IMessagesConfig extends IConfig
{
  String getNoPermissionMessage();

  void setNoPermissionMessage(String noPermissionMessage);

  String getReloadMessage();

  void setReloadMessage(String reloadMessage);

  String getReloadErrorMessage();

  void setReloadErrorMessage(String reloadErrorMessage);

  String getPlayerOnlyMessage();

  void setPlayerOnlyMessage(String playerOnlyMessage);

  List<String> getHelpMessages();

  void setHelpMessages(List<String> helpMessage);

  /**
   * Item info
   */
  String getItemNoHandItemMessage();

  void setItemNoHandItemMessage(String noHandItemMessage);

  String getItemLoreFormatMessage();

  void setItemLoreFormatMessage(String loreFormatMessage);

  String getItemLoreFormatEmptyMessage();

  void setItemLoreFormatEmptyMessage(String loreFormatEmptyMessage);

  String getItemMetaFormatMessage();

  void setItemMetaFormatMessage(String metaFormatMessage);

  String getItemMetaFormatEmptyMessage();

  void setItemMetaFormatEmptyMessage(String metaFormatEmptyMessage);

  String getItemEnchantFormatMessage();

  void setItemEnchantFormatMessage(String enchantFormatMessage);

  String getItemEnchantFormatEmptyMessage();

  void setItemEnchantFormatEmptyMessage(String enchantFormatEmptyMessage);

  String getItemFlagFormatMessage();

  void setItemFlagFormatMessage(String flagFormatMessage);

  String getItemFlagFormatEmptyMessage();

  void setItemFlagFormatEmptyMessage(String flagFormatEmptyMessage);

  String getItemNameFormatMessage();

  void setItemNameFormatMessage(String nameMessage);

  String getItemNameFormatEmptyMessage();

  void setItemNameFormatEmptyMessage(String nameEmptyMessage);

  List<String> getItemInfoMessage();

  void setItemInfoMessage(List<String> infoMessage);

  /**
   * Block info
   */
  String getBlockNoLookMessage();

  void setBlockNoLookMessage(String noLookMessage);

  List<String> getBlockInfoMessage();

  void setBlockInfoMessage(List<String> infoMessage);


  /**
   * Crates
   */
  String getCrateInventoryTitleMessage();

  void setCrateInventoryTitleMessage(String name);

  String getCrateOwnerOnlyMessage();

  void setCrateOwnerOnlyMessage(String name);

  String getCrateEmptyMessage();

  void setCrateEmptyMessage(String name);

  String getCrateSpawnActionBarMessage();

  void setCrateSpawnActionBarMessage(String name);

  List<String> getCrateSpawnTitleMessage();

  void setCrateSpawnTitleMessage(List<String> name);

  List<String> getCrateSpawnChatMessage();

  void setCrateSpawnChatMessage(List<String> name);

  String getCrateCollectActionBarMessage();

  void setCrateCollectActionBarMessage(String name);

  List<String> getCrateCollectTitleMessage();

  void setCrateCollectTitleMessage(List<String> name);

  List<String> getCrateCollectChatMessage();

  void setCrateCollectChatMessage(List<String> name);

  String getCrateTimerActionBarMessage();

  void setCrateTimerActionBarMessage(String name);
}
