package dev.pretti.prtminetreasures.placeholders.types;


import dev.pretti.prtminetreasures.placeholders.types.base.Placeholder;
import dev.pretti.prtminetreasures.utils.MaterialUtils;
import dev.pretti.treasuresapi.datatypes.ItemType;

public class ItemRewardPlaceholder extends Placeholder<ItemType>
{
  /**
   * Construtor da classe
   */
  public ItemRewardPlaceholder()
  {
    super("@item");
    getPlaceholders().put("@item_amount", struct -> String.format("%d", struct.getAmount()));
    getPlaceholders().put("@item_name", struct -> String.format("%s", struct.getItemName() == null ? MaterialUtils.getMaterialName(struct.getType()) : struct.getItemName()));
    getPlaceholders().put("@item_type", struct -> String.format("%s", struct.getType().name()));
    getPlaceholders().put("@item_data", struct -> String.format("%d", struct.getData()));
  }
}