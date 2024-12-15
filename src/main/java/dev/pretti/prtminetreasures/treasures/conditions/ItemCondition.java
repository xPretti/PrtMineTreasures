package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.treasuresapi.conditions.types.IItemCondition;
import dev.pretti.treasuresapi.contexts.TreasureContext;
import dev.pretti.treasuresapi.datatypes.MaterialType;
import dev.pretti.treasuresapi.options.ItemConditionOptions;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemCondition implements IItemCondition
{
  private final PlaceholderManager placeholderManager;

  private final MaterialType         materialType;
  private final int                  amount;
  private final String               name;
  private final List<String>         lores;
  private final ItemConditionOptions options;

  private final boolean invert;

  private final String loresText;

  /**
   * Construtor da classe
   */
  public ItemCondition(PlaceholderManager placeholderManager, @NotNull MaterialType materialType, int amount, @Nullable String name, @Nullable List<String> lores,
                       @NotNull ItemConditionOptions itemConditionOptions, boolean invert)
  {
    this.placeholderManager = placeholderManager;

    this.materialType = materialType;
    this.amount       = amount;
    this.name         = name;
    this.lores        = lores != null ? lores.isEmpty() ? null : lores : null;
    this.options      = itemConditionOptions;
    this.invert       = invert;

    this.loresText = lores != null ? lores.isEmpty() ? null : String.join("", lores) : null;
  }

  /**
   * Método de verificação
   */
  @Override
  public boolean evaluate(@NotNull TreasureContext treasureContext)
  {
    return invert != checkMaterial(treasureContext);
  }

  /**
   * Verificações
   */
  private boolean checkMaterial(TreasureContext contex)
  {
    int total = 0;
    if(options.isInInventory())
      {
        total += checkMaterialInInventory(contex);
      }
    else
      {
        total = options.isInHotbar() ? checkMaterialInHotbar(contex) : checkMaterialInHand(contex);
      }
    if(total >= amount)
      {
        return true;
      }
    if(options.isInArmor())
      {
        total += checkMaterialInArmor(contex);
      }
    return total >= amount;
  }

  private int checkMaterialInHand(TreasureContext contex)
  {
    ItemStack itemInHand = contex.getPlayer().getItemInHand();
    return isItem(contex, itemInHand) ? itemInHand.getAmount() : 0;
  }

  private int checkMaterialInInventory(TreasureContext contex)
  {
    int             total     = 0;
    PlayerInventory inventory = contex.getPlayer().getInventory();
    for(ItemStack item : inventory.getContents())
      {
        if(item != null)
          {
            total += isItem(contex, item) ? item.getAmount() : 0;
          }
        if(total >= amount)
          {
            return total;
          }
      }
    return total;
  }

  private int checkMaterialInArmor(TreasureContext contex)
  {
    int total = 0;
    for(ItemStack item : contex.getPlayer().getInventory().getArmorContents())
      {
        if(item != null)
          {
            total += isItem(contex, item) ? item.getAmount() : 0;
          }
        if(total >= amount)
          {
            return total;
          }
      }
    return total;
  }

  private int checkMaterialInHotbar(TreasureContext contex)
  {
    int             total     = 0;
    PlayerInventory inventory = contex.getPlayer().getInventory();
    for(int i = 0; i < 9; i++)
      {
        ItemStack item = inventory.getItem(i);
        if(item != null)
          {
            total += isItem(contex, item) ? item.getAmount() : 0;
          }
        if(total >= amount)
          {
            return total;
          }
      }
    return total;
  }

  private boolean isItem(TreasureContext contex, ItemStack compareItem)
  {
    boolean isData = !materialType.isUseData() || compareItem.getDurability() == materialType.getData();
    if(compareItem.getType().equals(materialType.getMaterial()) && isData)
      {
        ItemMeta meta = compareItem.getItemMeta();

        if(textMatches(replaceAll(contex, name), meta.getDisplayName()))
          {
            if(loresText == null)
              {
                return true;
              }
            List<String> itemLores = meta.getLore();
            return textMatches(replaceAll(contex, loresText), itemLores != null ? itemLores.isEmpty() ? null : String.join("", itemLores) : null);
          }
      }
    return false;
  }

  /**
   * Matches
   */
  private boolean textMatches(String nameParam, String itemName)
  {
    if(nameParam == null)
      {
        return true;
      }
    if(itemName == null)
      {
        return false;
      }
    if(options.isNameIgnoreCase() && itemName.equalsIgnoreCase(nameParam))
      {
        return true;
      }
    else if(options.isNameContains() && itemName.contains(nameParam))
      {
        return true;
      }
    return itemName.equals(nameParam);
  }

  /**
   * Replaces
   */
  private String replaceAll(TreasureContext contex, String name)
  {
    return placeholderManager.replaceAll(name, contex.getPlayer(), contex.getEventLocation(), null, 0, 0);
  }

  /**
   * Retornos
   */
  @Override
  public @NotNull MaterialType getMaterial()
  {
    return materialType;
  }

  @Override
  public int getAmount()
  {
    return amount;
  }

  @Override
  public @Nullable String getName()
  {
    return name;
  }

  @Override
  public @Nullable List<String> getLores()
  {
    return lores;
  }

  @Override
  public @NotNull ItemConditionOptions getOptions()
  {
    return options;
  }

}
