package dev.pretti.prtminetreasures.configs.interfaces;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IConfigSetup
{
  FileConfiguration getConfig();

  String getConfigPath();

  /**
   * Métodos de retornos padrão
   */
  @Nullable
  String getString(@NotNull String param);

  int getInt(@NotNull String param);

  boolean getBoolean(@NotNull String param);

  double getDouble(@NotNull String param);

  @Nullable
  List<String> getStringList(@NotNull String param);
}
