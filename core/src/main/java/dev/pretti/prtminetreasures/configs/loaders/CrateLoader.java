package dev.pretti.prtminetreasures.configs.loaders;

import dev.pretti.prtminetreasures.configs.interfaces.IConfig;
import dev.pretti.prtminetreasures.configs.interfaces.ICrateConfig;
import dev.pretti.prtminetreasures.configs.loaders.interfaces.IConfigLoader;
import dev.pretti.prtminetreasures.configs.setups.DefaultConfigSetup;
import dev.pretti.prtminetreasures.datatypes.SoundType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;

public class CrateLoader implements IConfigLoader
{
  private final DefaultConfigSetup config;

  /**
   * Construtor da classe
   */
  public CrateLoader(DefaultConfigSetup config)
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
        if(outputConfig instanceof ICrateConfig)
          {
            ICrateConfig output = (ICrateConfig) outputConfig;

            String options = "crates.";

            output.setBlockType(Material.valueOf(config.getString(options + "block-type")));
            output.setDestroySeconds(config.getInt(options + "destroy-time"));
            output.setOwnerOnly(config.getBoolean(options + "owner-only"));
            output.setInventoryRows(config.getInt(options + "inventory-rows"));
            output.setHologramEnabled(config.getBoolean(options + "hologram.use"));
            output.setHologramHeight(config.getDouble(options + "hologram.height"));
            output.setHologramDistance(config.getInt(options + "hologram.distance"));
            output.setOpenSound(loadSound("open"));
            output.setCloseSound(loadSound("close"));
            return true;
          }
      }
    return false;
  }


  private SoundType loadSound(String name)
  {
    String options   = "crates.sounds.";
    String soundName = config.getString(options + name + ".sound");
    if(soundName == null || soundName.isEmpty() || soundName.equalsIgnoreCase("NONE"))
      {
        return null;
      }
    try
      {
        Sound  sound  = Sound.valueOf(soundName);
        double volume = config.getDouble(options + name + ".volume");
        double pitch  = config.getDouble(options + name + ".pitch");
        return new SoundType(sound, (float) volume, (float) pitch);
      } catch(Throwable e)
      {
        return null;
      }
  }
}
