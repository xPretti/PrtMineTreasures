package dev.pretti.prtminetreasures.crates.interfaces;

import dev.pretti.prtminetreasures.enums.EnumCrateCloseType;
import dev.pretti.prtminetreasures.enums.EnumCrateOpenType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public interface ICrate<T>
{
  Location getLocation();

  T setOwner(Player player);

  Player getOwner();

  T setOwnerOnly(boolean ownerOnly);

  boolean isOwnerOnly();

  T setBlock(Material block, BlockFace face);

  Material getBlock();

  BlockFace getFace();

  boolean create();

  void destroy();

  EnumCrateOpenType open(Player player);

  EnumCrateCloseType close(Player player);

  boolean closeAll();

  boolean isOpen();

  boolean isEmpty();

  boolean isOwner(Player player);

  boolean isExpired();
}
