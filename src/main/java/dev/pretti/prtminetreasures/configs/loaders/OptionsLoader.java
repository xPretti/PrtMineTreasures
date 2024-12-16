package dev.pretti.prtminetreasures.configs.loaders;

import dev.pretti.prtminetreasures.configs.interfaces.IConfig;
import dev.pretti.prtminetreasures.configs.interfaces.IOptionsConfig;
import dev.pretti.prtminetreasures.configs.loaders.interfaces.IConfigLoader;
import dev.pretti.prtminetreasures.configs.setups.DefaultConfigSetup;
import dev.pretti.prtminetreasures.utils.FileConfigurationUtils;

public class OptionsLoader implements IConfigLoader
{
  private final DefaultConfigSetup config;

  /**
   * Construtor da classe
   */
  public OptionsLoader(DefaultConfigSetup config)
  {
    this.config = config;
  }

  /**
   * Método de carregamento
   */
  @Override
  public boolean load(IConfig outputConfig)
  {
    if(outputConfig != null)
      {
        if(outputConfig instanceof IOptionsConfig)
          {
            IOptionsConfig output = (IOptionsConfig) outputConfig;

            String options = "options.";

            output.setRemoveVanillaDrops(FileConfigurationUtils.getBoolean(config.getConfig(), config.getDefaultConfig(), options + "remove-vanilla-drops"));
            output.setTreasuresLimit(FileConfigurationUtils.getInt(config.getConfig(), config.getDefaultConfig(), options + "treasures-limit"));
            output.setDropToInventory(FileConfigurationUtils.getBoolean(config.getConfig(), config.getDefaultConfig(), options + "drop-to-inventory"));
            output.setDiscardExcess(FileConfigurationUtils.getBoolean(config.getConfig(), config.getDefaultConfig(), options + "discard-excess"));
            return true;
          }
      }
    return false;
  }

}
