package dev.pretti.prtminetreasures.versions.providers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IInventoryProvider
{
  ItemStack getLeftHandItem(@NotNull Player player);

  ItemStack getRightHandItem(@NotNull Player player);
}
