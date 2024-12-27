package dev.pretti.prtminetreasures.commands.base;

import dev.pretti.prtminetreasures.commands.interfaces.ICommand;
import dev.pretti.prtminetreasures.commands.interfaces.ICommands;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseCommands extends BaseCommand implements ICommands
{
  private final List<String>   autoCompleteCommands = new ArrayList<>();
  private final List<ICommand> subCommands          = new ArrayList<>();

  /**
   * Construtor da classe
   */
  public BaseCommands(String command, String permission)
  {
    super(command, permission);
  }

  /**
   * Implementações
   */
  @Override
  public boolean run(CommandSender sender, String command, String[] args)
  {
    if(args.length > 0)
      {
        String   newCommand = args[0];
        String[] newArgs    = Arrays.copyOfRange(args, 1, args.length);
        for(ICommand subCommand : subCommands)
          {
            if(subCommand.run(sender, newCommand, newArgs))
              {
                return (true);
              }
          }
      }
    return (false);
  }

  @Override
  public List<String> runAutoComplete(CommandSender sender, String command)
  {
    List<String> complete;
    for(ICommand subCommand : subCommands)
      {
        if(subCommand.isEnabled())
          {
            if((complete = subCommand.runAutoComplete(sender, command)) != null)
              {
                return (complete);
              }
          }
      }
    return (null);
  }

  @Override
  public List<String> getCommandsNames()
  {
    return autoCompleteCommands;
  }

  @Override
  public List<String> getCommandsNames(String startWith)
  {
    if(startWith != null && !subCommands.isEmpty())
      {
        List<String> result = new ArrayList<>();
        String       command;
        for(ICommand subCommand : subCommands)
          {
            if(subCommand.isEnabled())
              {
                command = subCommand.getCommand();
                if(command.startsWith(startWith))
                  {
                    result.add(command);
                  }
              }
          }
        return result.isEmpty() ? null : result;
      }
    return null;
  }

  @Override
  public List<ICommand> getCommands()
  {
    return subCommands;
  }

  /**
   * Métodos de modificações
   */
  @Override
  public boolean exist(String command)
  {
    return subCommands.stream().anyMatch(subCommand -> subCommand.getCommand().equalsIgnoreCase(command));
  }

  @Override
  public boolean register(ICommand command)
  {
    if(!exist(command.getCommand()))
      {
        autoCompleteCommands.add(command.getCommand());
        subCommands.add(command);
        return true;
      }
    return false;
  }

  @Override
  public boolean unregister(ICommand command)
  {
    autoCompleteCommands.remove(command.getCommand());
    return subCommands.remove(command);
  }

  @Override
  public boolean unregister(String command)
  {
    return subCommands.removeIf(subCommand -> subCommand.getCommand().equalsIgnoreCase(command));
  }
}
