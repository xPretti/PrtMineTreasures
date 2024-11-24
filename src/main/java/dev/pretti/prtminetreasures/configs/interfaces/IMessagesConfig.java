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
}
