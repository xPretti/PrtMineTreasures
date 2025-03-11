package dev.pretti.prtminetreasures.treasures;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.configs.interfaces.IOptionsConfig;
import dev.pretti.prtminetreasures.crates.Crate;
import dev.pretti.prtminetreasures.managers.CrateManager;
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
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;

public class BreakProcessors
{
  private final PrtMineTreasures    plugin;
  private       BlockProcessMapping blockProcessMapping;
  private final IOptionsConfig      optionsConfig;
  private       CrateManager        crateManager;

  public BreakProcessors(PrtMineTreasures plugin)
  {
    this.plugin        = plugin;
    this.optionsConfig = plugin.getConfigManager().getOptionsConfig();
    this.crateManager  = plugin.getCrateManager();
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
  public boolean process(BlockBreakEvent event)
  {
    Block                   block                   = event.getBlock();
    EnumDeliveryType        deliveryType            = optionsConfig.isDropToInventory() ? EnumDeliveryType.INVENTORY : EnumDeliveryType.DROP;
    TreasureContext         treasureContext         = new TreasureContext(event.getPlayer(), block.getLocation(), deliveryType);
    BlockConditionMapContex blockConditionMapContex = new BlockConditionMapContex(treasureContext, optionsConfig.getTreasuresLimit());
    if(blockProcessMapping != null && blockProcessMapping.process(blockConditionMapContex))
      {
        rewardProcess(event, treasureContext);
        return true;
      }
    return false;
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

  /**
   * Método de processamento dos tesouros
   */
  private void rewardProcess(BlockBreakEvent event, TreasureContext context)
  {
    Block                block            = event.getBlock();
    EnumVanillaDropsType vanillaDropsType = context.getProcessResult().getRemoveVanillaDrops();
    boolean              cancelEvent      = vanillaDropsType != null && vanillaDropsType.equals(EnumVanillaDropsType.REMOVE);
    boolean              removeBlock      = cancelEvent;
    List<ItemStack>      stock            = context.getProcessResult().getStorege();
    if(stock != null && !stock.isEmpty())
      {
        removeBlock = false;
        cancelEvent = true;

        Crate crate = crateManager.getOrCreateCrate(block.getLocation(), stock);
        crate.setOwner(event.getPlayer())
                .setOwnerOnly(false)
                .setCrateRows(9)
                .setBlock(Material.CHEST)
                .setTitle("Jujubas doces")
                .setDestroySeconds(300)
                .create();
        crateManager.openCrate(event.getPlayer(), block.getLocation());

      }
    if(removeBlock)
      {
        block.setType(Material.AIR);
      }
    if(cancelEvent)
      {
        event.setCancelled(true);
      }
  }
}
