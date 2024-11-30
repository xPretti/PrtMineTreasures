package dev.pretti.prtminetreasures.treasures;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.configs.interfaces.IOptionsConfig;
import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.prtminetreasures.treasures.builder.MineConditionsBuilder;
import dev.pretti.prtminetreasures.treasures.builder.MineTreasureBuilder;
import dev.pretti.prtminetreasures.utils.LogUtils;
import dev.pretti.treasuresapi.TreasuresApi;
import dev.pretti.treasuresapi.conditions.interfaces.IConditionsBuilder;
import dev.pretti.treasuresapi.processors.TreasuresProcessors;
import dev.pretti.treasuresapi.processors.context.TreasureContext;
import dev.pretti.treasuresapi.processors.interfaces.ITreasureBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BreakProcessors
{
  private final PlaceholderManager placeholderManager;

  private TreasuresProcessors treasuresProcessors;

  private final IOptionsConfig optionsConfig;

  public BreakProcessors(PrtMineTreasures plugin)
  {
    this.placeholderManager = plugin.getPlaceholderManager();
    this.optionsConfig = plugin.getConfigManager().getOptionsConfig();
  }

  /**
   * Método de carregamento
   */
  public boolean load(String folder)
  {
    LogUtils.logNormal("Loading treasures...");
    try
      {
        treasuresProcessors = TreasuresApi.loader(folder, getBuilder(), getConditionsBuilder());
        return (true);
      } catch(Throwable ignored)
      {
      }
    return false;
  }

  /**
   * Método de processamento do evento
   */
  public boolean process(Player player, Location location)
  {
    return treasuresProcessors.processAll(new TreasureContext(player, location), optionsConfig.getTreasuresLimit());
  }

  /**
   * Retorna o builder
   */
  private ITreasureBuilder getBuilder()
  {
    return new MineTreasureBuilder(placeholderManager, optionsConfig);
  }

  private IConditionsBuilder getConditionsBuilder()
  {
    return new MineConditionsBuilder();
  }
}
