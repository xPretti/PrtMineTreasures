package dev.pretti.prtminetreasures.utils;

import org.bukkit.block.BlockFace;

public class CoordUtils
{
  private static BlockFace[] DIRECTIONS = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

  public static BlockFace getCompassDirection(double yaw)
  {
    yaw = (yaw % 360 + 360) % 360;
    int index = (int) Math.round(yaw / 90) % 4;
    return DIRECTIONS[index];
  }

}
