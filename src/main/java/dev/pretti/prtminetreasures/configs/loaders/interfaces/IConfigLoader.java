package dev.pretti.prtminetreasures.configs.loaders.interfaces;


import dev.pretti.prtminetreasures.configs.interfaces.IConfig;

public interface IConfigLoader
{
  /**
   * Método de carregamento
   */
  boolean load(IConfig outputConfig);
}
