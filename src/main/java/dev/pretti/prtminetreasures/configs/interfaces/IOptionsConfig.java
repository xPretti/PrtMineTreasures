package dev.pretti.prtminetreasures.configs.interfaces;

public interface IOptionsConfig extends IConfig
{
  int getMaxDurability();

  void setMaxDurability(int maxDurability);

  double getDurabilityMinChance();

  void setDurabilityMinChance(double durabilityMinChance);
}
