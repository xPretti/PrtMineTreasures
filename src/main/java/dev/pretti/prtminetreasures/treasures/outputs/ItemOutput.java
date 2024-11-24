package dev.pretti.prtminetreasures.treasures.outputs;

import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.prtminetreasures.utils.DropUtils;
import dev.pretti.prtminetreasures.utils.ItemUtils;
import dev.pretti.prtminetreasures.utils.MathUtils;
import dev.pretti.prtminetreasures.utils.ToolUtils;
import dev.pretti.treasuresapi.datatypes.ItemType;
import dev.pretti.treasuresapi.processors.context.TreasureContext;
import dev.pretti.treasuresapi.processors.interfaces.outputs.IItemOutput;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class ItemOutput implements IItemOutput
{
  private final PlaceholderManager placeholderManager;

  private boolean isDirectlyInvetory = true;
  private boolean isDiscartExcess    = true;


  /**
   * Construtor da classe
   */
  public ItemOutput(PlaceholderManager placeholderManager)
  {
    this.placeholderManager = placeholderManager;
  }

  /**
   * Método de processamento
   */
  @Override
  public boolean process(@NotNull TreasureContext context, ItemType itemType, boolean useLooting)
  {
    return processItem(context, itemType, useLooting);
  }

  /**
   * Métodos para serem implementados
   */
  protected String getReplaceItemName(TreasureContext context, String current)
  {
    return placeholderManager.replaceAll(current, context.getPlayer());
  }

  protected void getReplaceItemLores(TreasureContext context, List<String> current)
  {
    placeholderManager.replaceAll(current, context.getPlayer());
  }

  /**
   * Processamentos
   */
  private boolean processItem(TreasureContext context, ItemType itemType, boolean useLooting)
  {
    itemType.setItemName(getReplaceItemName(context, itemType.getItemName()));
    getReplaceItemLores(context, itemType.getLores());
    ItemStack itemStack = ItemUtils.getItemStack(itemType);
    if(useLooting)
      {
        Player player = context.getPlayer();
        int    amount = ToolUtils.getFortuneLevel(player.getItemInHand()) + 1;
        amount = MathUtils.getRandom(1, amount);
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
        Collection<ItemStack> excessItems = player.getInventory().addItem(item).values();
        if(isDiscartExcess)
          {
            Location location = context.getEventLocation().clone();
            for(ItemStack excessItem : excessItems)
              {
                DropUtils.drop(location.add(0.5D, 0.5D, 0.5D), excessItem, true);
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
