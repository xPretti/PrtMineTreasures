package dev.pretti.prtminetreasures.treasures;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.configs.interfaces.IOptionsConfig;
import dev.pretti.prtminetreasures.enums.EnumBreakProcessResultType;
import dev.pretti.prtminetreasures.treasures.builder.MineConditionsBuilder;
import dev.pretti.prtminetreasures.treasures.builder.MineTreasureBuilder;
import dev.pretti.prtminetreasures.utils.LogUtils;
import dev.pretti.treasuresapi.TreasuresApi;
import dev.pretti.treasuresapi.conditions.interfaces.IConditionsBuilder;
import dev.pretti.treasuresapi.contexts.BlockConditionMapContex;
import dev.pretti.treasuresapi.contexts.TreasureContext;
import dev.pretti.treasuresapi.enums.EnumDeliveryType;
import dev.pretti.treasuresapi.enums.EnumVanillaDropsType;
import dev.pretti.treasuresapi.errors.interfaces.ITreasureError;
import dev.pretti.treasuresapi.errors.interfaces.ITreasureErrorLogger;
import dev.pretti.treasuresapi.errors.interfaces.ITreasureErrors;
import dev.pretti.treasuresapi.mapping.BlockProcessMapping;
import dev.pretti.treasuresapi.processors.TreasuresProcessors;
import dev.pretti.treasuresapi.processors.interfaces.ITreasureBuilder;
import dev.pretti.treasuresapi.rewards.Treasure;
import dev.pretti.treasuresapi.throwz.InvalidTreasuresLoaderException;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public class BreakProcessors
{
  private final PrtMineTreasures plugin;

  private BlockProcessMapping blockProcessMapping;

  private final IOptionsConfig optionsConfig;

  public BreakProcessors(PrtMineTreasures plugin)
  {
    this.plugin        = plugin;
    this.optionsConfig = plugin.getConfigManager().getOptionsConfig();
  }

  /**
   * Método de carregamento
   */
  public boolean load(String folder)
  {
    LogUtils.logNormal("Loading treasures...");
    List<Treasure> treasures;
    boolean        success = true;
    try
      {
        treasures = TreasuresApi.loader(folder, getConditionsBuilder());
      } catch(InvalidTreasuresLoaderException e)
      {
        sendErrors(e.getMessage(), e.getTreasureErros());
        treasures = e.getLoadedTreasures();
        success   = false;
      }
    if(treasures == null)
      {
        return false;
      }

    TreasuresProcessors treasuresProcessors = new TreasuresProcessors();
    success             = treasuresProcessors.load(treasures, getBuilder()) && success;
    blockProcessMapping = new BlockProcessMapping(treasuresProcessors);
    return success;
  }

  /**
   * Método de processamento do evento
   */
  public EnumBreakProcessResultType process(Player player, Location location)
  {
    EnumDeliveryType        deliveryType            = optionsConfig.isDropToInventory() ? EnumDeliveryType.INVENTORY : EnumDeliveryType.DROP;
    TreasureContext         treasureContext         = new TreasureContext(player, location, deliveryType);
    BlockConditionMapContex blockConditionMapContex = new BlockConditionMapContex(treasureContext, optionsConfig.getTreasuresLimit());
    if(blockProcessMapping != null && blockProcessMapping.process(blockConditionMapContex))
      {
        EnumVanillaDropsType vanillaDropsType = treasureContext.getProcessResult().getRemoveVanillaDrops();
        return vanillaDropsType == EnumVanillaDropsType.REMOVE ? EnumBreakProcessResultType.SUCCESS_REMOVE_DROPS :
                vanillaDropsType == EnumVanillaDropsType.NOT_REMOVE ? EnumBreakProcessResultType.SUCCESS_NO_REMOVE_DROPS : EnumBreakProcessResultType.SUCCESS_IGNORED;
      }
    return EnumBreakProcessResultType.FAIL;
  }

  /**
   * Retorna o builder
   */
  private ITreasureBuilder getBuilder()
  {
    return new MineTreasureBuilder(plugin.getIntegrationManager(), plugin.getPlaceholderManager(), optionsConfig);
  }

  private IConditionsBuilder getConditionsBuilder()
  {
    return new MineConditionsBuilder(plugin.getPlaceholderManager(), plugin.getIntegrationManager().getPlaceholderApi());
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
            LogUtils.logError("§c" + message);
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
