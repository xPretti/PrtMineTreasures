package dev.pretti.prtminetreasures.utils;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

public class DropUtils
{
  private static final Vector DEFAULT_VELOCITY = new Vector(0d, 0.20d, 0d);

  /**
   * Dropa um bloco em uma localização
   * @param location localização do bloco
   * @param itemStack item
   */
  public static void drop(Location location, ItemStack itemStack, boolean fixed)
  {
    if(fixed)
      {
        dropFixed(location, itemStack);
      }
    else
      {
        drop(location, itemStack);
      }
  }

  @Nullable
  public static Item drop(Location location, ItemStack itemStack)
  {
    if(location != null && itemStack != null)
      {
        Item item = location.getWorld().dropItem(location, itemStack);
        item.setVelocity(DEFAULT_VELOCITY);
        return item;
      }
    return null;
  }

  public static void dropFixed(Location location, ItemStack itemStack)
  {
    if(location != null && itemStack != null)
      {
        int amount = itemStack.getAmount();
        if(amount > 64)
          {
            int max       = 64;
            int stacks    = amount / max;
            int separated = amount % max;
            for(int i = 0; i < stacks; i++)
              {
                ItemStack newItemStack = itemStack.clone();
                newItemStack.setAmount(max);
                Item item = location.getWorld().dropItem(location, newItemStack);
                item.setVelocity(DEFAULT_VELOCITY);
              }
            if(separated > 0)
              {
                ItemStack newItemStack = itemStack.clone();
                newItemStack.setAmount(separated);
                Item item = location.getWorld().dropItem(location, newItemStack);
                item.setVelocity(DEFAULT_VELOCITY);
              }
          }
        else
          {
            drop(location, itemStack);
          }
      }
  }
}
