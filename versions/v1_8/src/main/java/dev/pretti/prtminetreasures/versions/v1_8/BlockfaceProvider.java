package dev.pretti.prtminetreasures.versions.v1_8;

import dev.pretti.prtminetreasures.versions.providers.IBlockfaceProvider;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

public class BlockfaceProvider implements IBlockfaceProvider
{
  @Override
  public void setFace(@NotNull Block block, @NotNull BlockFace face)
  {
    BlockState   state = block.getState();
    MaterialData data  = state.getData();
    if(data instanceof Directional)
      {
        Directional directional = (Directional) data;
        directional.setFacingDirection(face);
        state.setData((MaterialData) directional);
        state.update(true);
      }
  }
}
