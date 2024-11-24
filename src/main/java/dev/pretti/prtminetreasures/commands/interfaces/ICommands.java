package dev.pretti.prtminetreasures.commands.interfaces;

import java.util.List;

public interface ICommands
{
  List<String> getCommandsNames();

  List<String> getCommandsNames(String startWith);

  List<ICommand> getCommands();

  boolean exist(String command);

  boolean register(ICommand command);

  boolean unregister(ICommand command);

  boolean unregister(String command);
}
