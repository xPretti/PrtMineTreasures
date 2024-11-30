package dev.pretti.prtminetreasures.configs.interfaces;

public interface IOptionsConfig extends IConfig
{
  boolean isRemoveVanillaDrops();

  void setRemoveVanillaDrops(Boolean removeVanillaDrops);

  int getTreasuresLimit();

  void setTreasuresLimit(int treasuresLimit);

  boolean isDropToInventory();

  void setDropToInventory(boolean dropToInventory);

  boolean isDiscardExcess();

  void setDiscardExcess(boolean discardExcess);
}
