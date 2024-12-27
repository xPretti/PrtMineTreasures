package dev.pretti.prtminetreasures.versions.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IInventoryVersion
{
  ItemStack getLeftHandItem(@NotNull Player player);

  ItemStack getRightHandItem(@NotNull Player player);
}
