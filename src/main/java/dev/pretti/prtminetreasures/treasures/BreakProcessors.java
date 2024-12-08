package dev.pretti.prtminetreasures.treasures;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.configs.interfaces.IOptionsConfig;
import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.prtminetreasures.treasures.builder.MineConditionsBuilder;
import dev.pretti.prtminetreasures.treasures.builder.MineTreasureBuilder;
import dev.pretti.prtminetreasures.utils.LogUtils;
import dev.pretti.treasuresapi.TreasuresApi;
import dev.pretti.treasuresapi.conditions.interfaces.IConditionsBuilder;
import dev.pretti.treasuresapi.errors.interfaces.ITreasureError;
import dev.pretti.treasuresapi.errors.interfaces.ITreasureErrorLogger;
import dev.pretti.treasuresapi.errors.interfaces.ITreasureErrors;
import dev.pretti.treasuresapi.processors.TreasuresProcessors;
import dev.pretti.treasuresapi.processors.context.TreasureContext;
import dev.pretti.treasuresapi.processors.interfaces.ITreasureBuilder;
import dev.pretti.treasuresapi.rewards.Treasure;
import dev.pretti.treasuresapi.throwz.InvalidTreasuresLoaderException;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

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
    List<Treasure> treasures;
    boolean success = true;
    try
      {
        treasures = TreasuresApi.loader(folder, getConditionsBuilder());
      } catch(InvalidTreasuresLoaderException e)
      {
        sendErrors(e.getMessage(), e.getTreasureErros());
        treasures = e.getLoadedTreasures();
        success = false;
      }
    if(treasures == null)
      {
        return false;
      }
    treasuresProcessors = new TreasuresProcessors();
    return treasuresProcessors.load(treasures, getBuilder()) && success;
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

  /**
   * Métodos de envio de erros
   */
  private void sendErrors(String message, ITreasureErrorLogger errors)
  {
    if(errors != null && message != null)
      {
        Collection<ITreasureErrors> errorsList = errors.getErrors();
        if(errorsList != null)
          {
            LogUtils.logError("§c"+message);
            for(ITreasureErrors treErrors : errorsList)
              {
                String section = "§e" + treErrors.getSection();
                LogUtils.logError(section);
                List<ITreasureError> singleErrors = treErrors.getErrors();
                if(singleErrors != null)
                  {
                    for(ITreasureError treError : singleErrors)
                      {
                        LogUtils.logError(String.format("§8- %s: §c%s§8.", treError.getMessage(), treError.getValue()));
                      }
                  }
              }
          }
      }
  }
}
