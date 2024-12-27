package dev.pretti.prtminetreasures.versions.v1_9;

import dev.pretti.prtminetreasures.versions.interfaces.IInventoryVersion;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InventoryVersion implements IInventoryVersion
{
  @Override
  public ItemStack getLeftHandItem(@NotNull Player player)
  {
    return player.getInventory().getItemInOffHand();
  }

  @Override
  public ItemStack getRightHandItem(@NotNull Player player)
  {
    return player.getInventory().getItemInMainHand();
  }
}
