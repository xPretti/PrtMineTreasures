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

  String getNoHandItemMessage();

  void setNoHandItemMessage(String noHandItemMessage);

  String getLoreFormatMessage();

  void setLoreFormatMessage(String loreFormatMessage);

  String getLoreFormatEmptyMessage();

  void setLoreFormatEmptyMessage(String loreFormatEmptyMessage);

  String getMetaFormatMessage();

  void setMetaFormatMessage(String metaFormatMessage);

  String getMetaFormatEmptyMessage();

  void setMetaFormatEmptyMessage(String metaFormatEmptyMessage);

  String getEnchantFormatMessage();

  void setEnchantFormatMessage(String enchantFormatMessage);

  String getEnchantFormatEmptyMessage();

  void setEnchantFormatEmptyMessage(String enchantFormatEmptyMessage);

  String getFlagFormatMessage();

  void setFlagFormatMessage(String flagFormatMessage);

  String getFlagFormatEmptyMessage();

  void setFlagFormatEmptyMessage(String flagFormatEmptyMessage);

  String getNameFormatMessage();

  void setNameFormatMessage(String nameMessage);

  String getNameFormatEmptyMessage();

  void setNameFormatEmptyMessage(String nameEmptyMessage);

  List<String> getInfoMessage();

  void setInfoMessage(List<String> infoMessage);

}
