package dev.pretti.prtminetreasures.treasures.outputs;

import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.prtminetreasures.utils.*;
import dev.pretti.treasuresapi.datatypes.ItemType;
import dev.pretti.treasuresapi.processors.context.TreasureContext;
import dev.pretti.treasuresapi.processors.interfaces.outputs.IItemOutput;
import dev.pretti.treasuresapi.rewards.Options.RewardOptions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class ItemOutput implements IItemOutput
{
  private final PlaceholderManager placeholderManager;

  private final boolean isDirectlyInvetory;
  private final boolean isDiscartExcess;


  /**
   * Construtor da classe
   */
  public ItemOutput(PlaceholderManager placeholderManager, boolean isDirectlyInvetory, boolean isDiscartExcess)
  {
    this.placeholderManager = placeholderManager;
    this.isDirectlyInvetory = isDirectlyInvetory;
    this.isDiscartExcess    = isDiscartExcess;
  }

  /**
   * Método de processamento
   */
  @Override
  public boolean process(@NotNull TreasureContext context, @NotNull ItemType itemType, @NotNull RewardOptions options)
  {
    return processItem(context, itemType, options);
  }

  /**
   * Métodos para serem implementados
   */
  protected String getReplaceItemName(TreasureContext context, String current)
  {
    return placeholderManager.replaceAll(current, context.getPlayer(), context.getEventLocation(), null, 0, 0);
  }

  protected List<String> getReplaceItemLores(TreasureContext context, List<String> current)
  {
    return placeholderManager.replaceAll(current, context.getPlayer(), context.getEventLocation(), null, 0, 0);
  }

  /**
   * Processamentos
   */
  private boolean processItem(TreasureContext context, @NotNull ItemType itemType, @NotNull RewardOptions options)
  {
    itemType.setItemName(getReplaceItemName(context, itemType.getItemName()));
    itemType.setLores(getReplaceItemLores(context, itemType.getLores()));

    ItemStack itemStack = ItemUtils.getItemStack(itemType);
    if(options.isUseFortune())
      {
        Player player  = context.getPlayer();
        int    fortune = MathUtils.getRandom(0, ToolUtils.getFortuneLevel(player.getItemInHand())) + 1;
        int    amount  = itemType.getAmount() * fortune;
        itemType.setAmount(amount);
        itemStack.setAmount(amount);
      }
    return sendItem(context, itemStack);
  }


  /**
   * Métodos de execução
   */
  protected boolean sendItem(TreasureContext context, ItemStack item)
  {
    if(isDirectlyInvetory)
      {
        Player                player      = context.getPlayer();
        Collection<ItemStack> excessItems = InventoryUtils.addItem(player.getInventory(), item, true);
        if(!isDiscartExcess)
          {
            Location location = context.getEventLocation().clone();
            if(excessItems != null)
              {
                for(ItemStack excessItem : excessItems)
                  {
                    DropUtils.drop(location.clone().add(0.5D, 0.5D, 0.5D), excessItem, true);
                  }
              }
          }
      }
    else
      {
        Location location = context.getEventLocation().clone();
        DropUtils.drop(location.add(0.5D, 0.5D, 0.5D), item, true);
      }
    return true;
  }
}
