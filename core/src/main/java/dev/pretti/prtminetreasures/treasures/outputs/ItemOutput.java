package dev.pretti.prtminetreasures.treasures.outputs;

import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.prtminetreasures.utils.*;
import dev.pretti.prtminetreasures.versions.VersionsManager;
import dev.pretti.treasuresapi.contexts.TreasureContext;
import dev.pretti.treasuresapi.datatypes.ItemType;
import dev.pretti.treasuresapi.datatypes.MetadataType;
import dev.pretti.treasuresapi.enums.EnumDeliveryType;
import dev.pretti.treasuresapi.options.RewardOptions;
import dev.pretti.treasuresapi.processors.interfaces.outputs.IItemOutput;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class ItemOutput implements IItemOutput
{
  private final VersionsManager versionsManager;

  private final PlaceholderManager placeholderManager;

  private final boolean isDiscartExcess;


  /**
   * Construtor da classe
   */
  public ItemOutput(PlaceholderManager placeholderManager, boolean isDiscartExcess)
  {
    this.versionsManager    = VersionsManager.getInstance();
    this.placeholderManager = placeholderManager;
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
  protected String getReplaceName(@NotNull TreasureContext context, String current)
  {
    return placeholderManager.replaceAll(current, context.getPlayer(), context.getEventLocation(), null, 0, 0);
  }

  protected List<String> getReplaceNames(@NotNull TreasureContext context, List<String> current)
  {
    return placeholderManager.replaceAll(current, context.getPlayer(), context.getEventLocation(), null, 0, 0);
  }

  /**
   * Processamentos
   */
  private boolean processItem(@NotNull TreasureContext context, @NotNull ItemType itemType, @NotNull RewardOptions options)
  {
    itemType.setItemName(getReplaceName(context, itemType.getItemName()));
    itemType.setLores(getReplaceNames(context, itemType.getLores()));

    ItemStack itemStack = ItemUtils.getItemStack(itemType);
    if(options.isUseFortune())
      {
        Player player  = context.getPlayer();
        int    fortune = MathUtils.getRandom(0, ToolUtils.getFortuneLevel(versionsManager.getInventoryVersion().getRightHandItem(player))) + 1;
        int    amount  = itemType.getAmount() * fortune;
        itemType.setAmount(amount);
        itemStack.setAmount(amount);
      }
    applyMeta(context, itemType, itemStack);
    return sendItem(context, itemStack, options.getDeliveryType() == null ? context.getDeliveryType() : options.getDeliveryType());
  }

  protected void applyMeta(@NotNull TreasureContext context, @NotNull ItemType itemType, @NotNull ItemStack itemStack)
  {
    if(itemType.getMetadata() != null && !itemType.getMetadata().isEmpty())
      {
        for(MetadataType meta : itemType.getMetadata())
          {
            meta.setKey(getReplaceName(context, meta.getKey()));
            meta.setValue(getReplaceName(context, meta.getValue()));
            MetaUtils.setMeta(itemStack, meta.getKey(), meta.getValue());
          }
      }
  }

  /**
   * Métodos de execução
   */
  protected boolean sendItem(@NotNull TreasureContext context, @NotNull ItemStack item, EnumDeliveryType deliveryType)
  {
    if(deliveryType.equals(EnumDeliveryType.INVENTORY))
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
    else if(deliveryType.equals(EnumDeliveryType.DROP))
      {
        Location location = context.getEventLocation().clone();
        DropUtils.drop(location.add(0.5D, 0.5D, 0.5D), item, true);
      }
    return true;
  }
}
