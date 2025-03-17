package dev.pretti.prtminetreasures.handlers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IHologramHandler
{
  boolean create(@NotNull Location location);

  boolean recreate();

  boolean delete();

  boolean isDeleted();

  void setTextLine(int line, @NotNull String text);

  void setItemLine(int line, @NotNull ItemStack item);

  void addTextLine(@NotNull String text);

  void addItemLine(@NotNull ItemStack item);

  void removeLine(int line);

  void removeLines();

  void removeLines(int count);

  void setVisibility(Player player, boolean visible);

  void clearVisibility();

  int size();
}
