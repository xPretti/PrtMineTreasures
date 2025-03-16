package dev.pretti.prtminetreasures.versions.providers;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

public interface IBlockfaceProvider
{
  void setFace(@NotNull Block block, @NotNull BlockFace face);
}
