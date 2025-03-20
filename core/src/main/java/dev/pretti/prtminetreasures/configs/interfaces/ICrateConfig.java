package dev.pretti.prtminetreasures.configs.interfaces;

import dev.pretti.prtminetreasures.datatypes.SoundType;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

public interface ICrateConfig extends IConfig
{
  Material getBlockType();

  void setBlockType(Material blockType);

  int getDestroySeconds();

  void setDestroySeconds(int destroySeconds);

  boolean isOwnerOnly();

  void setOwnerOnly(boolean ownerOnly);

  int getInventoryRows();

  void setInventoryRows(int rows);

  @Nullable
  SoundType getOpenSound();

  void setOpenSound(@Nullable SoundType openSound);

  @Nullable
  SoundType getCloseSound();

  void setCloseSound(@Nullable SoundType closeSound);
}
