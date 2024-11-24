package dev.pretti.prtminetreasures.utils;

import dev.pretti.treasuresapi.datatypes.EnchantType;
import dev.pretti.treasuresapi.datatypes.ItemType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Set;

public class ItemUtils
{
  /**
   * Métodos de retornos de itens
   */
  public static ItemStack getItemStack(Material type, Byte data, int amount)
  {
    return new ItemStack(type, amount, data);
  }

  public static ItemStack getItemStack(Material type, Byte data, int amount, String itemName)
  {
    ItemStack itemStack = getItemStack(type, data, amount);
    setItemName(itemStack, itemName);
    return itemStack;
  }

  public static ItemStack getItemStack(Material type, Byte data, int amount, String itemName, List<String> lores)
  {
    ItemStack itemStack = getItemStack(type, data, amount, itemName);
    setLores(itemStack, lores);
    return itemStack;
  }

  public static ItemStack getItemStack(Material type, Byte data, int amount, String itemName, List<String> lores,
                                       List<EnchantType> enchants)
  {
    ItemStack itemStack = getItemStack(type, data, amount, itemName, lores);
    setEnchantmens(itemStack, enchants);
    return itemStack;
  }

  public static ItemStack getItemStack(Material type, Byte data, int amount, String itemName, List<String> lores,
                                       List<EnchantType> enchants, Set<ItemFlag> flags)
  {
    ItemStack itemStack = getItemStack(type, data, amount, itemName, lores, enchants);
    setFlags(itemStack, flags);
    return itemStack;
  }

  public static ItemStack getItemStack(ItemType itemType)
  {
    return getItemStack(itemType.getType(), itemType.getData(), itemType.getAmount(), itemType.getItemName(), itemType.getLores(), itemType.getEnchants(), itemType.getFlags());
  }

  /**
   * Métodos de modificação de item
   */
  public static boolean setItemName(ItemStack itemStack, String name)
  {
    if(itemStack != null)
      {
        if(name != null)
          {
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(ReplaceUtils.toColorMessage(name));
            return itemStack.setItemMeta(meta);
          }
      }
    return false;
  }

  public static boolean setLores(ItemStack itemStack, List<String> lores)
  {
    if(itemStack != null)
      {
        if(lores != null)
          {
            if(!lores.isEmpty())
              {
                ItemMeta meta = itemStack.getItemMeta();
                meta.setLore(lores);
                return itemStack.setItemMeta(meta);
              }
          }
      }
    return false;
  }

  public static boolean setEnchantmens(ItemStack itemStack, List<EnchantType> enchants)
  {
    if(itemStack != null)
      {
        if(enchants != null)
          {
            if(!enchants.isEmpty())
              {
                ItemMeta meta = itemStack.getItemMeta();
                int      enchantLevel;
                for(EnchantType enchant : enchants)
                  {
                    enchantLevel = enchant.getEnchantLevel();
                    if(enchantLevel > 0)
                      {
                        meta.addEnchant(enchant.getEnchantType(), enchantLevel, true);
                      }
                  }
                return itemStack.setItemMeta(meta);
              }
          }
      }
    return false;
  }

  public static boolean setFlags(ItemStack itemStack, Set<ItemFlag> flags)
  {
    if(itemStack != null)
      {
        if(flags != null)
          {
            if(!flags.isEmpty())
              {
                ItemMeta meta = itemStack.getItemMeta();
                for(ItemFlag flag : flags)
                  {
                    meta.addItemFlags(flag);
                  }
                return itemStack.setItemMeta(meta);
              }
          }
      }
    return false;
  }
}
