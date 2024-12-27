package dev.pretti.prtminetreasures.utils;

import de.tr7zw.changeme.nbtapi.NBT;
import dev.pretti.prtminetreasures.PrtMineTreasures;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

  /**
   * Metadatas para itens
   */
  public static void setMeta(@NotNull ItemStack itemStack, String key, String value)
  {
    switch(TypeUtils.getEnumType(value))
      {
        case BOOLEAN:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setBoolean(key, TypeUtils.toBoolean(value));
            });
          break;
        case INT:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setInteger(key, TypeUtils.toInteger(value));
            });
          break;
        case LONG:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setLong(key, TypeUtils.toLong(value));
            });
          break;
        case DOUBLE:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setDouble(key, TypeUtils.toDouble(value));
            });
          break;
        default:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setString(key, value);
            });
          break;
      }
  }
}
