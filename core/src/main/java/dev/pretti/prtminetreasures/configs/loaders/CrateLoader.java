package dev.pretti.prtminetreasures.configs.loaders;

import dev.pretti.prtminetreasures.configs.interfaces.IConfig;
import dev.pretti.prtminetreasures.configs.interfaces.ICrateConfig;
import dev.pretti.prtminetreasures.configs.loaders.interfaces.IConfigLoader;
import dev.pretti.prtminetreasures.configs.setups.DefaultConfigSetup;
import dev.pretti.prtminetreasures.datatypes.SoundType;
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
            output.setOpenSound(loadSound("open"));
            output.setCloseSound(loadSound("close"));

            output.setSpawnActionBarMessage(config.getBoolean(options + "messages.onSpawn.actionbar"));
            output.setSpawnTitleMessage(config.getBoolean(options + "messages.onSpawn.title"));
            output.setSpawnChatMessage(config.getBoolean(options + "messages.onSpawn.chat"));

            output.setCollectActionBarMessage(config.getBoolean(options + "messages.onCollect.actionbar"));
            output.setCollectTitleMessage(config.getBoolean(options + "messages.onCollect.title"));
            output.setCollectChatMessage(config.getBoolean(options + "messages.onCollect.chat"));

            output.setTimerActionBarMessage(config.getBoolean(options + "messages.onTimer.actionbar"));

            output.setTitle(config.getInt(options + "title.fade-in"), config.getInt(options + "title.fade-out"), config.getInt(options + "title.stay"));
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
