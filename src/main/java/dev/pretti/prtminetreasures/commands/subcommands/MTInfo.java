package dev.pretti.prtminetreasures.commands.subcommands;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.commands.base.BaseCommand;
import dev.pretti.prtminetreasures.configs.types.MessagesConfig;
import dev.pretti.prtminetreasures.utils.ReplaceUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MTInfo extends BaseCommand
{
  private final PrtMineTreasures plugin;
  private final MessagesConfig   messagesConfig;

  /**
   * Construtor da classe
   */
  public MTInfo(String command, String permission, PrtMineTreasures plugin)
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
            Player    player    = (Player) sender;
            ItemStack itemStack = player.getItemInHand();
            if(itemStack != null && !itemStack.getType().equals(Material.AIR))
              {
                sendItemInfo(player, itemStack);
                return true;
              }
            sender.sendMessage(messagesConfig.getNoHandItemMessage());
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

  /**
   * Retorna a informação do item para o jogador
   */
  private void sendItemInfo(Player player, ItemStack item)
  {
    ItemMeta meta = item.getItemMeta();
    if(meta == null)
      {
        return;
      }
    List<String> infoMessages = messagesConfig.getInfoMessage();
    if(infoMessages == null || infoMessages.isEmpty())
      {
        return;
      }
    for(String line : infoMessages)
      {
        if(line != null)
          {
            if(line.contains("@type"))
              {
                line = line.replaceAll("@type", item.getType().name());
              }
            if(line.contains("@data"))
              {
                line = line.replaceAll("@data", String.valueOf(item.getDurability()));
              }
            if(line.contains("@name"))
              {
                String itemName = item.getItemMeta().getDisplayName();
                String newName  = itemName == null ? messagesConfig.getNameEmptyMessage() : messagesConfig.getNameMessage().replaceAll("@name", itemName);
                line = line.replaceAll("@name_format", newName);
              }
            if(line.contains("@lore_format"))
              {
                line = line.replaceAll("@lore_format", getLoreFormatMessage(meta));
              }
            player.sendMessage(ReplaceUtils.toColorMessage(line));
          }
      }
  }

  /**
   * Retorna o formato de mensagens
   */
  @NotNull
  private String getLoreFormatMessage(ItemMeta meta)
  {
    if(meta == null)
      {
        return "";
      }
    String loreFormat = messagesConfig.getLoreFormatMessage();
    List<String> lores = meta.getLore();
    if(lores == null || lores.isEmpty())
      {
        return messagesConfig.getLoreFormatEmptyMessage();
      }
    String loreFormatMessage = "";
    String loreValue;
    for(int i = 0; i < lores.size(); i++)
      {
        loreValue = lores.get(i);
        if(loreValue != null)
          {
            if(!loreFormatMessage.isEmpty())
              {
                loreFormatMessage = loreFormatMessage.concat("\n");
              }
            loreFormatMessage = loreFormatMessage.concat(loreFormat.replaceAll("@line", String.valueOf(i)).replaceAll("@lore", loreValue));
          }
      }
    return loreFormatMessage;
  }
}
