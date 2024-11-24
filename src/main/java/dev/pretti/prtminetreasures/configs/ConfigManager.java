package dev.pretti.prtminetreasures.configs;

import dev.pretti.prtminetreasures.configs.interfaces.IConfig;
import dev.pretti.prtminetreasures.configs.interfaces.IConfigManager;
import dev.pretti.prtminetreasures.configs.setups.ConfigSetup;
import dev.pretti.prtminetreasures.configs.setups.DefaultConfigSetup;
import dev.pretti.prtminetreasures.configs.types.MessagesConfig;
import dev.pretti.prtminetreasures.configs.types.OptionsConfig;
import dev.pretti.prtminetreasures.utils.LogUtils;
import dev.pretti.prtminetreasures.utils.ResourceUtils;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager implements IConfigManager
{
  private final String configName   = "config.yml";
  private final String messagesName = "messages.yml";

  private final OptionsConfig  optionsConfig  = new OptionsConfig();
  private final MessagesConfig messagesConfig = new MessagesConfig();

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
   * Retornos
   */
  public OptionsConfig getOptionsConfig()
  {
    return this.optionsConfig;
  }

  public MessagesConfig getMessagesConfig()
  {
    return this.messagesConfig;
  }

  /**
   * Métodos de carregamentos privados
   */
  private void _createConfigs()
  {
    ResourceUtils.CreateConfig(configName);
    ResourceUtils.CreateConfig(messagesName);
  }

  private boolean _loaderConfigs()
  {
    FileConfiguration configuration        = ResourceUtils.getConfig(configName);
    FileConfiguration configurationDefault = ResourceUtils.getResource(configName);

    LogUtils.logNormal("");

    int errors = 0;
    errors += loadConfig("options..", this.optionsConfig, new DefaultConfigSetup(configuration, configurationDefault, configName));
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
