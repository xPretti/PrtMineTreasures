package dev.pretti.prtminetreasures.commands.subcommands;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.commands.base.BaseCommand;
import dev.pretti.prtminetreasures.configs.types.MessagesConfig;
import dev.pretti.treasuresapi.utils.BlockDataUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class MTBlock extends BaseCommand
{
  private final MessagesConfig messagesConfig;

  /**
   * Construtor da classe
   */
  public MTBlock(String command, String permission, PrtMineTreasures plugin)
  {
    super(command, permission, true);
    this.messagesConfig  = plugin.getConfigManager().getMessagesConfig();
  }

  /**
   * Implementação do comando
   */
  @Override
  public boolean run(CommandSender sender, String command, String[] args)
  {
    if(isCommand(command))
      {
        if(canUse(sender))
          {
            if(hasPermission(sender))
              {
                Player        player     = (Player) sender;
                Set<Material> transparen = null;
                Block         block      = player.getTargetBlock(transparen, 5);
                if(!block.getType().equals(Material.AIR))
                  {
                    sendBlockInfo(player, block);
                    return true;
                  }
                sender.sendMessage(messagesConfig.getBlockNoLookMessage());
              }
            else
              {
                sender.sendMessage(messagesConfig.getNoPermissionMessage());
              }
          }
        else
          {
            String message = messagesConfig.getPlayerOnlyMessage();
            sender.sendMessage(message);
          }
        return true;
      }
    return false;
  }

  /**
   * Retorna a informação do item para o jogador
   */
  private void sendBlockInfo(Player player, Block block)
  {
    List<String> infoMessages = messagesConfig.getBlockInfoMessage();
    if(infoMessages == null || infoMessages.isEmpty())
      {
        return;
      }
    for(String line : infoMessages)
      {
        if(line != null)
          {
            if(line.contains("@material"))
              {
                line = line.replaceAll("@material", block.getType().name());
              }
            if(line.contains("@data"))
              {
                line = line.replaceAll("@data", String.valueOf(BlockDataUtils.getData(block)));
              }
            player.sendMessage(line);
          }
      }
  }
}
