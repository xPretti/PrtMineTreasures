package dev.pretti.prtminetreasures.crates.interfaces;

import dev.pretti.prtminetreasures.crates.Crate;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ICrates
{
  boolean inMenu(@NotNull Player player);

  boolean isCrate(@NotNull Location location);

  @Nullable
  Crate getCrate(@NotNull Location location);

  @NotNull
  Crate create(@NotNull Location location, @NotNull List<ItemStack> items);

  void destroy(@NotNull Location location);

  @Nullable
  Crate getOpen(@NotNull Player player);

  boolean open(@NotNull Player player, @NotNull Location location);

  boolean close(@NotNull Player player);

}
