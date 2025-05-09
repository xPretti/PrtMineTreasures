package dev.pretti.prtminetreasures;

import de.tr7zw.changeme.nbtapi.NBT;
import dev.pretti.prtminetreasures.commands.MineTreasuresCommand;
import dev.pretti.prtminetreasures.configs.ConfigManager;
import dev.pretti.prtminetreasures.crates.CrateManager;
import dev.pretti.prtminetreasures.integrations.IntegrationManager;
import dev.pretti.prtminetreasures.listeners.BlockListener;
import dev.pretti.prtminetreasures.listeners.CratesListener;
import dev.pretti.prtminetreasures.listeners.InventoryListener;
import dev.pretti.prtminetreasures.listeners.PlayerListener;
import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.prtminetreasures.treasures.BreakProcessors;
import dev.pretti.prtminetreasures.utils.LogUtils;
import dev.pretti.prtminetreasures.utils.SystemUtils;
import dev.pretti.prtminetreasures.versions.VersionsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PrtMineTreasures extends JavaPlugin
{
  private static PrtMineTreasures instance;

  private VersionsManager versionsManager;

  private ConfigManager      configManager;
  private IntegrationManager integrationManager;
  private PlaceholderManager placeholderManager;
  private CrateManager       crateManager;
  private BreakProcessors    breakProcessors;

  private boolean isInitialized;

  /**
   * Implementações
   */
  @Override
  public void onLoad()
  {
    loadInstances();
  }

  @Override
  public void onEnable()
  {
    registerEvents();
    registerCommands();

    load();

    NBT.preloadApi();
  }

  @Override
  public void onDisable()
  {
    LogUtils.log("");
    LogUtils.logNormal("Finishing...");

    LogUtils.logNormal("Removed all treasures from the world...");
    crateManager.deinit();
    LogUtils.log("");
  }

  /**
   * Inicializadores
   */
  public boolean reload()
  {
    return load();
  }

  protected boolean load()
  {
    String initMessage = isInitialized ? "Re-Initializing..." : "Initializing...";
    LogUtils.log("");
    LogUtils.logNormal(initMessage);
    LogUtils.logNormal("Plugin version: §e" + getDescription().getVersion());
    LogUtils.logNormal("Server version: §e" + SystemUtils.getServerVersion());

    int errors = configManager.load() ? 0 : 1;
    errors += integrationManager.loader() ? 0 : 1;
    errors += breakProcessors.load(getDataFolder() + File.separator + configManager.getTreasuresFolder()) ? 0 : 1;

    boolean success = errors == 0;

    if(success)
      {
        LogUtils.logNormal("");
        LogUtils.logNormal("Everything initialized correctly.");
      }
    else
      {
        LogUtils.logNormal("");
        LogUtils.logError("&4Something went wrong during the initialization process.");
      }
    LogUtils.log("");

    isInitialized = true;

    Bukkit.getScheduler().runTaskLater(this, this::delayedLoad, 20L);

    return success;
  }

  /**
   * Método de carregamento atrasado
   */
  public void delayedLoad()
  {
    crateManager.init();
  }

  /**
   * Métodos de carregamentos da instância
   */
  protected void loadInstances()
  {
    instance           = this;
    versionsManager    = new VersionsManager();
    configManager      = new ConfigManager(getDataFolder().toString());
    integrationManager = new IntegrationManager();
    placeholderManager = new PlaceholderManager(integrationManager.getPlaceholderApi());
    crateManager       = new CrateManager(this);
    breakProcessors    = new BreakProcessors(this);
  }

  /**
   * Métodos de registros de eventos
   */
  protected void registerEvents()
  {
    Bukkit.getPluginManager().registerEvents(new BlockListener(this), this);
    Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    Bukkit.getPluginManager().registerEvents(new InventoryListener(this), this);
    Bukkit.getPluginManager().registerEvents(new CratesListener(this), this);
  }

  /**
   * Métodos de registros de comandos
   */
  protected void registerCommands()
  {
    getCommand("PrtMineTreasures").setExecutor(new MineTreasuresCommand(this));
  }

  /**
   * Métodos de retornos
   */
  public static PrtMineTreasures getInstance()
  {
    return instance;
  }

  public ConfigManager getConfigManager()
  {
    return configManager;
  }

  public IntegrationManager getIntegrationManager()
  {
    return integrationManager;
  }

  public PlaceholderManager getPlaceholderManager()
  {
    return (placeholderManager);
  }

  public BreakProcessors getBreakProcessors()
  {
    return (breakProcessors);
  }

  public CrateManager getCrateManager()
  {
    return (crateManager);
  }
}
