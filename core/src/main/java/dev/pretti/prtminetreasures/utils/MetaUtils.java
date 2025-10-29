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
   * Define metadata em um item
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
//        case LONG:
//          NBT.modify(itemStack, nbt ->
//            {
//              nbt.setLong(key, TypeUtils.toLong(value));
//            });
//          break;
        case DOUBLE:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setDouble(key, TypeUtils.toDouble(value));
            });
          break;
//        case BYTE:
//          NBT.modify(itemStack, nbt ->
//            {
//              nbt.setByte(key, TypeUtils.toByte(value));
//            });
//          break;
//        case FLOAT:
//          NBT.modify(itemStack, nbt ->
//            {
//              nbt.setFloat(key, TypeUtils.toFloat(value));
//            });
//          break;
//        case SHORT:
//          NBT.modify(itemStack, nbt ->
//            {
//              nbt.setShort(key, TypeUtils.toShort(value));
//            });
//          break;
//        case BYTE_ARRAY:
//          NBT.modify(itemStack, nbt ->
//            {
//              nbt.setByteArray(key, TypeUtils.toByteArray(value));
//            });
//          break;
        case INT_ARRAY:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setIntArray(key, TypeUtils.toIntArray(value));
            });
          break;
//        case LONG_ARRAY:
//          NBT.modify(itemStack, nbt ->
//            {
//              nbt.setLongArray(key, TypeUtils.toLongArray(value));
//            });
//          break;
        default:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setString(key, value);
            });
          break;
      }
  }
}
