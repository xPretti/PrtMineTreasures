package dev.pretti.prtminetreasures.commands;


import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.commands.base.BaseCommands;
import dev.pretti.prtminetreasures.commands.subcommands.MTBlock;
import dev.pretti.prtminetreasures.commands.subcommands.MTItem;
import dev.pretti.prtminetreasures.commands.subcommands.MTReload;
import dev.pretti.prtminetreasures.configs.types.MessagesConfig;
import dev.pretti.prtminetreasures.constants.PermissionsConstants;
import dev.pretti.prtminetreasures.utils.ReplaceUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class MineTreasuresCommand extends BaseCommands implements CommandExecutor, TabCompleter
{
  private final MessagesConfig messagesConfig;

  /**
   * Construtor da classe
   */
  public MineTreasuresCommand(PrtMineTreasures plugin)
  {
    super("MineTreasures", PermissionsConstants.PERM_COMMAND_MT);
    this.messagesConfig      = plugin.getConfigManager().getMessagesConfig();

    // Registros
    register(new MTReload("reload", PermissionsConstants.PERM_COMMAND_MT_RELOAD, plugin));
    register(new MTItem("item", PermissionsConstants.PERM_COMMAND_MT_INFO, plugin));
    register(new MTBlock("block", PermissionsConstants.PERM_COMMAND_MT_INFO, plugin));
  }

  /**
   * Implementação
   */
  @Override
  public boolean onCommand(CommandSender sender, Command command, String s, String[] strings)
  {
    if(hasPermission(sender))
      {
        if(!run(sender, s, strings))
          {
            messagesConfig.getHelpMessages().forEach(message -> sender.sendMessage(ReplaceUtils.toColorMessage(message)));
          }
      }
    else
      {
        String message = ReplaceUtils.toColorMessage(messagesConfig.getNoPermissionMessage());
        sender.sendMessage(message);
      }
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
  {
    if(args.length == 1)
      {
        return getCommandsNames(args[0]);
      }
    else if(args.length > 1)
      {
        return runAutoComplete(sender, args[args.length - 2]);
      }
    return null;
  }
}
