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
import dev.pretti.treasuresapi.result.TreasureProcessorsResult;
import dev.pretti.treasuresapi.result.errors.interfaces.ITreasureErrorResult;
import dev.pretti.treasuresapi.result.errors.types.TreasureErrorResult;
import dev.pretti.treasuresapi.result.errors.types.TreasureErrorsResult;
import dev.pretti.treasuresapi.result.interfaces.ITreasureResult;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

public class BreakProcessors
{
  private final PlaceholderManager placeholderManager;

  private TreasuresProcessors treasuresProcessors;

  private final IOptionsConfig optionsConfig;

  public BreakProcessors(PrtMineTreasures plugin)
  {
    this.placeholderManager = plugin.getPlaceholderManager();
    this.optionsConfig      = plugin.getConfigManager().getOptionsConfig();
  }

  /**
   * Método de carregamento
   */
  public boolean load(String folder)
  {
    LogUtils.logNormal("Loading treasures...");
    TreasureProcessorsResult treasureResult = TreasuresApi.loader(folder, getBuilder(), getConditionsBuilder());
    treasuresProcessors = treasureResult.getTreasuresProcessors();

    ITreasureResult                  result = treasureResult.getTreasureResult();
    Collection<ITreasureErrorResult> errors = result.getErrors();
    if(errors != null && !errors.isEmpty())
      {
        for(ITreasureErrorResult error : errors)
          {
            if(error != null)
              {
                String section = "§e" + error.getIdentifier();
                if(error instanceof TreasureErrorsResult)
                  {
                    TreasureErrorsResult treasureErrorsResult = (TreasureErrorsResult) error;
                    LogUtils.logError(section + "§8: ");
                    for(String name : treasureErrorsResult.getErrors())
                      {
                        LogUtils.logError("§8- " + treasureErrorsResult.getError() + ": §c" + name);
                      }
                  }
                else if(error instanceof TreasureErrorResult)
                  {
                    TreasureErrorResult treasureErrorResult = (TreasureErrorResult) error;
                    LogUtils.logError(section + "§8: §c" + treasureErrorResult.getError());
                  }
              }
          }
        return false;
      }
    return true;
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
