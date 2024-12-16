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

  List<String> getHelpMessages();

  void setHelpMessages(List<String> helpMessage);

  String getNoHandItemMessage();

  void setNoHandItemMessage(String noHandItemMessage);

  String getLoreFormatMessage();

  void setLoreFormatMessage(String loreFormatMessage);

  String getLoreFormatEmptyMessage();

  void setLoreFormatEmptyMessage(String loreFormatEmptyMessage);

  String getNameMessage();

  void setNameFormatMessage(String nameMessage);

  String getNameEmptyMessage();

  void setNameFormatEmptyMessage(String nameEmptyMessage);

  List<String> getInfoMessage();

  void setInfoMessage(List<String> infoMessage);

}
