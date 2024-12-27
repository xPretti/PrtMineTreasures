package dev.pretti.prtminetreasures.commands.subcommands;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.commands.base.BaseCommand;
import dev.pretti.prtminetreasures.configs.types.MessagesConfig;
import dev.pretti.prtminetreasures.utils.ReplaceUtils;
import org.bukkit.command.CommandSender;

public class MTReload extends BaseCommand
{
  private final PrtMineTreasures plugin;
  private final MessagesConfig   messagesConfig;

  /**
   * Construtor da classe
   */
  public MTReload(String command, String permission, PrtMineTreasures plugin)
  {
    super(command, permission);
    this.plugin         = plugin;
    this.messagesConfig = plugin.getConfigManager().getMessagesConfig();
  }

  /**
   * Implementação do comando
   */
  @Override
  public boolean run(CommandSender sender, String command, String[] args)
  {
    if(isCommand(command))
      {
        if(hasPermission(sender))
          {
            boolean success = plugin.reload();
            String  message = ReplaceUtils.toColorMessage(success ? messagesConfig.getReloadMessage() : messagesConfig.getReloadErrorMessage());
            sender.sendMessage(message);
          }
        else
          {
            String message = ReplaceUtils.toColorMessage(messagesConfig.getNoPermissionMessage());
            sender.sendMessage(message);
          }
        return true;
      }
    return false;
  }
}
