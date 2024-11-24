package dev.pretti.prtminetreasures.configs.setups;

import dev.pretti.prtminetreasures.configs.interfaces.IConfigSetup;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigSetup implements IConfigSetup
{
  private final FileConfiguration config;
  private final String            configPath;

  /**
   * Construtor da classe
   */
  public ConfigSetup(FileConfiguration config, String configPath)
  {
    this.config     = config;
    this.configPath = configPath;
  }

  /**
   * Retornos da classe
   */
  @Override
  public FileConfiguration getConfig()
  {
    return this.config;
  }

  @Override
  public String getConfigPath()
  {
    return this.configPath;
  }
}
