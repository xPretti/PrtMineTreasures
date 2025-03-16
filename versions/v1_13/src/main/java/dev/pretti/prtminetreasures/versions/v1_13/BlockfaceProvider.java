package dev.pretti.prtminetreasures.versions.v1_13;

import dev.pretti.prtminetreasures.versions.providers.IBlockfaceProvider;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.jetbrains.annotations.NotNull;

public class BlockfaceProvider implements IBlockfaceProvider
{
  @Override
  public void setFace(@NotNull Block block, @NotNull BlockFace face)
  {
    BlockData blockData = block.getBlockData();
    if(blockData instanceof Directional)
      {
        ((Directional) blockData).setFacing(face);
        block.setBlockData(blockData);
      }
  }
}
