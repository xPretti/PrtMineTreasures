package dev.pretti.prtminetreasures.utils;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;

public class MetaUtils
{
  private static final String PLACED_BY_PLAYER = "prt_placed_by_player";

  /**
   * Predefined meta
   */
  public static void setPlacedByPlayer(@NotNull Block block, @NotNull Player player)
  {
    if(isPlacedByPlayer(block))
      {
        return;
      }
    block.setMetadata(PLACED_BY_PLAYER, new FixedMetadataValue(PrtMineTreasures.getInstance(), player.getDisplayName()));
  }

  public static boolean isPlacedByPlayer(@NotNull Block block)
  {
    return block.hasMetadata(PLACED_BY_PLAYER);
  }

  public static String getPlacedByPlayer(@NotNull Block block)
  {
    if(!isPlacedByPlayer(block))
      {
        return null;
      }
    return block.getMetadata(PLACED_BY_PLAYER).get(0).asString();
  }

}
