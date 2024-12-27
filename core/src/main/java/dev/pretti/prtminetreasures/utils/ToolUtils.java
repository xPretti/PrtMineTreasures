package dev.pretti.prtminetreasures.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ToolUtils
{
  private static final Random random = new Random();

  public static boolean damagedTool(ItemStack tool)
  {
    if(tool != null && isTool(tool.getType()))
      {
        int level = getUnbreakingLevel(tool);
        if(level == 0 || (100 / (level + 1)) > random.nextInt(100))
          {
            tool.setDurability((short) (tool.getDurability() + 1));
          }
        return true;
      }
    return false;
  }

  /**
   * Verificações
   */
  public static boolean isTool(Material material)
  {
    return isSword(material) || isPickaxe(material) || isAxe(material) || isShovel(material) || isHoe(material) || isShears(material);
  }

  public static boolean isSword(Material material)
  {
    return material.name().contains("_SWORD");
  }

  public static boolean isPickaxe(Material material)
  {
    return material.name().contains("_PICKAXE");
  }

  public static boolean isAxe(Material material)
  {
    return material.name().contains("_AXE");
  }

  public static boolean isShovel(Material material)
  {
    String name = material.name();
    return name.contains("_SHOVEL") || name.contains("_SPADE");
  }

  public static boolean isHoe(Material material)
  {
    return material.name().contains("_HOE");
  }

  public static boolean isShears(Material material)
  {
    return material.equals(Material.SHEARS);
  }


  /**
   * Retornos
   */
  public static int getUnbreakingLevel(ItemStack tool)
  {
    if(tool != null)
      {
        return tool.getEnchantmentLevel(Enchantment.DURABILITY);
      }
    return 0;
  }

  public static int getFortuneLevel(ItemStack tool)
  {
    if(tool != null)
      {
        return tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
      }
    return 0;
  }
}
