package dev.pretti.prtminetreasures.handlers;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IHologramHandler
{
  boolean create(@NotNull Location location);

  boolean delete();

  boolean isDeleted();

  void setTextLine(int line, @Nullable String text);

  void setItemLine(int line, @Nullable ItemStack item);

  void addTextLine(@NotNull String text);

  void addItemLine(@NotNull ItemStack item);

  void removeLine(int line);

  void removeLines();

  int size();
}
