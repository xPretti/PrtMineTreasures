package dev.pretti.prtminetreasures.configs;

import dev.pretti.prtminetreasures.configs.interfaces.IConfig;
import dev.pretti.prtminetreasures.configs.interfaces.IConfigManager;
import dev.pretti.prtminetreasures.configs.setups.ConfigSetup;
import dev.pretti.prtminetreasures.configs.setups.DefaultConfigSetup;
import dev.pretti.prtminetreasures.configs.types.CrateConfig;
import dev.pretti.prtminetreasures.configs.types.MessagesConfig;
import dev.pretti.prtminetreasures.configs.types.OptionsConfig;
import dev.pretti.prtminetreasures.utils.FileUtils;
import dev.pretti.prtminetreasures.utils.LogUtils;
import dev.pretti.prtminetreasures.utils.ResourceUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ConfigManager implements IConfigManager
{
  private final String pluginDataFolder;

  private final String configName   = "config.yml";
  private final String messagesName = "messages.yml";

  private final String treasuresFolder = "Treasures";

  private final OptionsConfig  optionsConfig  = new OptionsConfig();
  private final CrateConfig    cratesConfig   = new CrateConfig();
  private final MessagesConfig messagesConfig = new MessagesConfig();

  /**
   * Contrutor da classe
   */
  public ConfigManager(String dataFolder)
  {
    this.pluginDataFolder = dataFolder;
  }

  /**
   * Métodos de carregamentos
   */
  @Override
  public boolean load()
  {
    _createConfigs();
    return _loaderConfigs();
  }

  @Override
  public boolean reload()
  {
    return this.load();
  }

  /**
   * Retornos das configurações
   */
  @NotNull
  public OptionsConfig getOptionsConfig()
  {
    return optionsConfig;
  }

  @NotNull
  public CrateConfig getCratesConfig()
  {
    return cratesConfig;
  }

  @NotNull
  public MessagesConfig getMessagesConfig()
  {
    return messagesConfig;
  }

  /**
   * Retornos de pastas e arquivos
   */
  @NotNull
  public String getTreasuresFolder()
  {
    return treasuresFolder;
  }

  /**
   * Métodos de carregamentos privados
   */
  private void _createConfigs()
  {
    ResourceUtils.CreateConfig(configName);
    ResourceUtils.CreateConfig(messagesName);
    if(!FileUtils.FolderExist(pluginDataFolder + File.separator + treasuresFolder))
      {
        ResourceUtils.CreateConfig(treasuresFolder + File.separator + "commands-example.yml");
        ResourceUtils.CreateConfig(treasuresFolder + File.separator + "conditions-example.yml");
        ResourceUtils.CreateConfig(treasuresFolder + File.separator + "fortune-example.yml");
        ResourceUtils.CreateConfig(treasuresFolder + File.separator + "groups-example.yml");
        ResourceUtils.CreateConfig(treasuresFolder + File.separator + "items-example.yml");
        ResourceUtils.CreateConfig(treasuresFolder + File.separator + "money_exp_level-example.yml");
        ResourceUtils.CreateConfig(treasuresFolder + File.separator + "placeholder-example.yml");
        ResourceUtils.CreateConfig(treasuresFolder + File.separator + "metadata-example.yml");
      }
  }

  private boolean _loaderConfigs()
  {
    FileConfiguration  configuration        = ResourceUtils.getConfig(configName);
    FileConfiguration  configurationDefault = ResourceUtils.getResource(configName);
    DefaultConfigSetup configFile           = new DefaultConfigSetup(configuration, configurationDefault, configName);

    LogUtils.logNormal("");

    int errors = 0;
    errors += loadConfig("options..", this.optionsConfig, configFile);
    errors += loadConfig("crate options..", this.cratesConfig, configFile);
    errors += loadConfig("messages..", this.messagesConfig, new DefaultConfigSetup(ResourceUtils.getConfig(messagesName), ResourceUtils.getResource(messagesName), messagesName));
    return (errors <= 0);
  }

  private int loadConfig(String category, IConfig config, ConfigSetup setup)
  {
    if(category != null)
      {
        LogUtils.logNormal("&8Loading " + category);
      }
    return config.load(setup) ? 0 : 1;
  }

}
