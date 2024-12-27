package dev.pretti.prtminetreasures.metadatas.conditions.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IMetadataCondition
{
  boolean evaluate(@NotNull Player player, @NotNull ItemStack itemStack);
}
