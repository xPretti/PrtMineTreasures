package dev.pretti.prtminetreasures.commands.subcommands;

import de.tr7zw.changeme.nbtapi.NBT;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTType;
import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.commands.base.BaseCommand;
import dev.pretti.prtminetreasures.configs.types.MessagesConfig;
import dev.pretti.prtminetreasures.utils.ReplaceUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MTInfo extends BaseCommand
{
  private final MessagesConfig messagesConfig;

  /**
   * Construtor da classe
   */
  public MTInfo(String command, String permission, PrtMineTreasures plugin)
  {
    super(command, permission, true);
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
        if(canUse(sender))
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
                String newName  = itemName == null ? messagesConfig.getNameFormatEmptyMessage() : messagesConfig.getNameFormatMessage().replaceAll("@name", ReplaceUtils.toOriginalMessage(itemName));
                line = line.replaceAll("@name_format", newName);
              }
            if(line.contains("@lore_format"))
              {
                line = line.replaceAll("@lore_format", getLoreFormatMessage(meta));
              }
            if(line.contains("@meta-format"))
              {
                line = line.replaceAll("@meta-format", getMetaFormatMessage(item));
              }
            if(line.contains("@enchant-format"))
              {
                line = line.replaceAll("@enchant-format", getEnchantFormatMessage(meta));
              }
            if(line.contains("@flag-format"))
              {
                line = line.replaceAll("@flag-format", getFlagsFormatMessage(meta));
              }
            player.sendMessage(line);
          }
      }
  }

  /**
   * Retorna o formato de mensagens
   */
  @NotNull
  private String getLoreFormatMessage(ItemMeta meta)
  {
    List<String> lores = meta.getLore();
    if(lores == null || lores.isEmpty())
      {
        return messagesConfig.getLoreFormatEmptyMessage();
      }
    String loreFormat        = messagesConfig.getLoreFormatMessage();
    String result = "";
    String loreValue;
    for(int i = 0; i < lores.size(); i++)
      {
        loreValue = lores.get(i);
        if(loreValue != null)
          {
            if(!result.isEmpty())
              {
                result = result.concat("\n");
              }
            result = result.concat(loreFormat.replaceAll("@line", String.valueOf(i)).replaceAll("@lore", ReplaceUtils.toOriginalMessage(loreValue)));
          }
      }
    return result;
  }

  @NotNull
  private String getMetaFormatMessage(ItemStack itemStack)
  {
    NBTCompound compound = (NBTCompound) NBT.readNbt(itemStack);
    Set<String> keys     = compound.getKeys();
    if(keys == null || keys.isEmpty())
      {
        return messagesConfig.getMetaFormatEmptyMessage();
      }
    String metaFormat = messagesConfig.getMetaFormatMessage();
    String result     = "";
    for(String key : keys)
      {
        String  typeName = null;
        String  value    = null;
        NBTType type     = compound.getType(key);
        switch(type)
          {
            case NBTTagByte:
              typeName = "Byte";
              value = String.valueOf(compound.getBoolean(key));
              break;
            case NBTTagShort:
            case NBTTagInt:
              typeName = "Int";
              value = String.valueOf(compound.getInteger(key));
              break;
            case NBTTagLong:
              typeName = "Long";
              value = String.valueOf(compound.getLong(key));
              break;
            case NBTTagFloat:
              typeName = "Float";
              value = String.valueOf(compound.getFloat(key));
              break;
            case NBTTagDouble:
              typeName = "Double";
              value = String.valueOf(compound.getDouble(key));
              break;
            case NBTTagString:
              typeName = "String";
              value = compound.getString(key);
              break;
          }
        if(value != null)
          {
            if(!result.isEmpty())
              {
                result = result.concat("\n");
              }
            result = result.concat(metaFormat.replaceAll("@key", key).replaceAll("@value", value).replaceAll("@type", typeName));
          }
      }
    return result.isEmpty() ? messagesConfig.getMetaFormatEmptyMessage() : result;
  }

  @NotNull
  private String getEnchantFormatMessage(ItemMeta meta)
  {
    Map<Enchantment, Integer> enchants = meta.getEnchants();
    if(enchants == null || enchants.isEmpty())
      {
        return messagesConfig.getEnchantFormatEmptyMessage();
      }
    String enchantFormat = messagesConfig.getEnchantFormatMessage();
    String result        = "";
    for(Map.Entry<Enchantment, Integer> entry : enchants.entrySet())
      {
        if(entry != null)
          {
            Enchantment enchantment = entry.getKey();
            int         level       = entry.getValue();
            if(!result.isEmpty())
              {
                result = result.concat("\n");
              }
            result = result.concat(enchantFormat.replaceAll("@enchant", enchantment.getName()).replaceAll("@level", String.valueOf(level)));
          }
      }
    return result.isEmpty() ? messagesConfig.getEnchantFormatEmptyMessage() : result;
  }

  @NotNull
  private String getFlagsFormatMessage(ItemMeta meta)
  {
    Set<ItemFlag> flags = meta.getItemFlags();
    if(flags == null || flags.isEmpty())
      {
        return messagesConfig.getFlagFormatEmptyMessage();
      }
    String enchantFormat = messagesConfig.getFlagFormatMessage();
    String result        = "";
    for(ItemFlag flag : flags)
      {
        if(flag != null)
          {
            if(!result.isEmpty())
              {
                result = result.concat("\n");
              }
            result = result.concat(enchantFormat.replaceAll("@flag", flag.name()));
          }
      }
    return result.isEmpty() ? messagesConfig.getFlagFormatEmptyMessage() : result;
  }

}
