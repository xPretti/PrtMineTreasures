package dev.pretti.prtminetreasures.configs.interfaces;

public interface IDependenciesConfig extends IConfig
{
  boolean useServerNBT();

  void setUseServerNBT(Boolean serverNBT);
}
