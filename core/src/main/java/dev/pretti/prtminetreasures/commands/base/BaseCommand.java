package dev.pretti.prtminetreasures.commands.base;

import dev.pretti.prtminetreasures.commands.interfaces.ICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class BaseCommand implements ICommand
{
  private final String command;
  private final String permission;
  private       boolean playerOnly;

  /**
   * Construtor da classe
   */
  public BaseCommand(String command, String permission)
  {
    this(command, permission, false);
  }

  public BaseCommand(String command, String permission, boolean playerOnly)
  {
    this.command    = command;
    this.permission = permission;
    this.playerOnly = playerOnly;
  }

  /**
   * Retornos
   */
  @Override
  public boolean isEnabled()
  {
    return true;
  }

  @Override
  public String getCommand()
  {
    return command;
  }

  @Override
  public String getPermission()
  {
    return permission;
  }

  @Override
  public boolean hasPermission(CommandSender sender)
  {
    return !(sender instanceof Player) || sender.hasPermission(getPermission());
  }

  @Override
  public boolean isCommand(String subCommand)
  {
    return (subCommand != null && subCommand.equalsIgnoreCase(getCommand()));
  }

  @Override
  public boolean canUse(CommandSender sender)
  {
    return (sender instanceof Player) || !isPlayerOnly();
  }

  @Override
  public List<String> runAutoComplete(CommandSender sender, String command)
  {
    return null;
  }

  public boolean isPlayerOnly()
  {
    return playerOnly;
  }

  public void setPlayerOnly(boolean playerOnly)
  {
    this.playerOnly = playerOnly;
  }
}
