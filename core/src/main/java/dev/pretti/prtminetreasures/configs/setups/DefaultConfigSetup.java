package dev.pretti.prtminetreasures.configs.setups;

import org.bukkit.configuration.file.FileConfiguration;

public class DefaultConfigSetup extends ConfigSetup
{
  private final FileConfiguration defaultConfig;

  /**
   * Construtor da classe
   */
  public DefaultConfigSetup(FileConfiguration config, FileConfiguration defaultConfig, String configPath)
  {
    super(config, configPath);
    this.defaultConfig = defaultConfig;
  }

  /**
   * Retornos da classe
   */
  public FileConfiguration getDefaultConfig()
  {
    return this.defaultConfig;
  }
}
