package dev.pretti.prtminetreasures.commands.interfaces;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface ICommand
{
  boolean isEnabled();

  String getCommand();

  String getPermission();

  boolean hasPermission(CommandSender sender);

  boolean isCommand(String command);

  boolean canUse(CommandSender sender);

  boolean run(CommandSender sender, String command, String[] args);

  List<String> runAutoComplete(CommandSender sender, String command);
}
