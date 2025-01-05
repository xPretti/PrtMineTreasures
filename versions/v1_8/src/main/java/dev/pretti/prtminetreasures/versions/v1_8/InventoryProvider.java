package dev.pretti.prtminetreasures.versions.v1_8;

import dev.pretti.prtminetreasures.versions.providers.IInventoryProvider;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InventoryProvider implements IInventoryProvider
{
  @Override
  public ItemStack getLeftHandItem(@NotNull Player player)
  {
    return null;
  }

  @Override
  public ItemStack getRightHandItem(@NotNull Player player)
  {
    return player.getInventory().getItemInHand();
  }
}
