package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.prtminetreasures.metadatas.ItemMetadataProcess;
import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.prtminetreasures.versions.VersionsManager;
import dev.pretti.treasuresapi.conditions.types.IItemCondition;
import dev.pretti.treasuresapi.contexts.TreasureContext;
import dev.pretti.treasuresapi.datatypes.MaterialType;
import dev.pretti.treasuresapi.datatypes.MetadataConditionType;
import dev.pretti.treasuresapi.options.ItemConditionOptions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemCondition implements IItemCondition
{
  private final VersionsManager    versionsManager;
  private final PlaceholderManager placeholderManager;

  private final MaterialType         materialType;
  private final int                  amount;
  private final String               name;
  private final List<String>         lores;
  private final ItemConditionOptions options;

  private final boolean invert;

  private final String loresText;

  private ItemMetadataProcess itemMetadataProcess;

  /**
   * Construtor da classe
   */
  public ItemCondition(PlaceholderManager placeholderManager, @Nullable MaterialType materialType, int amount, @Nullable String name, @Nullable List<String> lores,
                       @NotNull ItemConditionOptions itemConditionOptions, boolean invert, @Nullable List<MetadataConditionType> metadatas)
  {
    this.versionsManager    = VersionsManager.getInstance();
    this.placeholderManager = placeholderManager;

    this.materialType = materialType;
    this.amount       = amount;
    this.name         = name;
    this.lores        = lores != null ? lores.isEmpty() ? null : lores : null;
    this.options      = itemConditionOptions;
    this.invert       = invert;

    this.loresText = lores != null ? lores.isEmpty() ? null : String.join("", lores) : null;

    if(metadatas != null)
      {
        this.itemMetadataProcess = new ItemMetadataProcess();
        itemMetadataProcess.load(placeholderManager.getPlaceholderApi(), metadatas);
      }
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
    String nameParam = replaceAll(contex, name);
    String loreParam = replaceAll(contex, loresText);
    Player player    = contex.getPlayer();

    int total = 0;
    if(options.isInInventory())
      {
        total += checkMaterialInInventory(player, nameParam, loreParam);
      }
    else
      {
        total = options.isInHotbar() ? checkMaterialInHotbar(player, nameParam, loreParam) : checkMaterialInHand(player, nameParam, loreParam);
      }
    if(total >= amount)
      {
        return true;
      }
    if(options.isInArmor())
      {
        total += checkMaterialInArmor(player, nameParam, loreParam);
      }
    return total >= amount;
  }

  private int checkMaterialInHand(Player player, String nameCheck, String loresCheck)
  {
    int count = 0;
    if(options.isInHand())
      {
        ItemStack hand = versionsManager.getInventoryVersion().getRightHandItem(player);
        if(hand != null)
          {
            count = isItem(player, nameCheck, loresCheck, hand) ? hand.getAmount() : 0;
          }
      }
    if(count < amount && options.isInOffHand())
      {
        ItemStack hand = versionsManager.getInventoryVersion().getLeftHandItem(player);
        if(hand != null)
          {
            count += isItem(player, nameCheck, loresCheck, hand) ? hand.getAmount() : 0;
          }
      }
    return count;
  }

  private int checkMaterialInInventory(Player player, String nameCheck, String loresCheck)
  {
    int             total     = 0;
    PlayerInventory inventory = player.getInventory();
    for(ItemStack item : inventory.getContents())
      {
        if(item != null)
          {
            total += isItem(player, nameCheck, loresCheck, item) ? item.getAmount() : 0;
          }
        if(total >= amount)
          {
            return total;
          }
      }
    return total;
  }

  private int checkMaterialInArmor(Player player, String nameCheck, String loresCheck)
  {
    int total = 0;
    for(ItemStack item : player.getInventory().getArmorContents())
      {
        if(item != null)
          {
            total += isItem(player, nameCheck, loresCheck, item) ? item.getAmount() : 0;
          }
        if(total >= amount)
          {
            return total;
          }
      }
    return total;
  }

  private int checkMaterialInHotbar(Player player, String nameCheck, String loresCheck)
  {
    int             total     = 0;
    PlayerInventory inventory = player.getInventory();
    for(int i = 0; i < 9; i++)
      {
        ItemStack item = inventory.getItem(i);
        if(item != null)
          {
            total += isItem(player, nameCheck, loresCheck, item) ? item.getAmount() : 0;
          }
        if(total >= amount)
          {
            return total;
          }
      }
    return total;
  }

  private boolean isItem(Player player, String nameCheck, String loresCheck, ItemStack compareItem)
  {
    if(compareItem.getType().equals(Material.AIR))
      {
        return false;
      }
    boolean isData = materialType != null && (!materialType.isUseData() || compareItem.getDurability() == materialType.getData());
    if(materialType == null || compareItem.getType().equals(materialType.getMaterial()) && isData)
      {
        ItemMeta meta = compareItem.getItemMeta();
        if(textMatches(nameCheck, meta.getDisplayName()))
          {
            if(loresText != null)
              {
                List<String> itemLores = meta.getLore();
                if(!textMatches(loresCheck, itemLores != null ? itemLores.isEmpty() ? null : String.join("", itemLores) : null))
                  {
                    return false;
                  }
              }
            if(itemMetadataProcess != null)
              {
                return itemMetadataProcess.evaluate(player, compareItem);
              }
            return true;

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
  public @Nullable MaterialType getMaterial()
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
