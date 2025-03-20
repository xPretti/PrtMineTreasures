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

  boolean isSpawnActionBarMessage();

  void setSpawnActionBarMessage(boolean spawnActionBarMessage);

  boolean isSpawnTitleMessage();

  void setSpawnTitleMessage(boolean spawnTitleMessage);

  boolean isSpawnChatMessage();

  void setSpawnChatMessage(boolean spawnChatMessage);

  boolean isCollectActionBarMessage();

  void setCollectActionBarMessage(boolean collectActionBarMessage);

  boolean isCollectTitleMessage();

  void setCollectTitleMessage(boolean collectTitleMessage);

  boolean isCollectChatMessage();

  void setCollectChatMessage(boolean collectChatMessage);

  boolean isTimerActionBarMessage();

  void setTimerActionBarMessage(boolean timerActionBarMessage);

  void setTitle(int titleFadeIn, int titleFadeOut, int titleStay);

  int getTitleFadeIn();

  int getTitleFadeOut();

  int getTitleStay();
}
