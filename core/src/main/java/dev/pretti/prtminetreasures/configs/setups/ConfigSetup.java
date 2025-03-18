package dev.pretti.prtminetreasures.configs.setups;

import dev.pretti.prtminetreasures.configs.interfaces.IConfigSetup;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ConfigSetup implements IConfigSetup
{
  private final FileConfiguration config;
  private final String            configPath;

  /**
   * Construtor da classe
   */
  public ConfigSetup(@NotNull FileConfiguration config, String configPath)
  {
    this.config     = config;
    this.configPath = configPath;
  }

  /**
   * Retornos da classe
   */
  @Override
  public @NotNull FileConfiguration getConfig()
  {
    return this.config;
  }

  @Override
  public String getConfigPath()
  {
    return this.configPath;
  }

  /**
   * Métodos de retornos padrão
   */
  @Override
  public @Nullable String getString(@NotNull String param)
  {
    return config.getString(param);
  }

  @Override
  public int getInt(@NotNull String param)
  {
    return config.getInt(param);
  }

  @Override
  public boolean getBoolean(@NotNull String param)
  {
    return config.getBoolean(param);
  }

  @Override
  public double getDouble(@NotNull String param)
  {
    return config.getDouble(param);
  }

  @Override
  public @Nullable List<String> getStringList(@NotNull String param)
  {
    return config.getStringList(param);
  }
}
