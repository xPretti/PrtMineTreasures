package dev.pretti.prtminetreasures.crates;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.constants.PermissionsConstants;
import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.datatypes.SoundType;
import dev.pretti.prtminetreasures.enums.EnumCrateCloseType;
import dev.pretti.prtminetreasures.enums.EnumCrateHologramStateType;
import dev.pretti.prtminetreasures.enums.EnumCrateOpenType;
import dev.pretti.prtminetreasures.holograms.CrateHologram;
import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.prtminetreasures.settings.CrateSettings;
import dev.pretti.prtminetreasures.utils.DropUtils;
import dev.pretti.prtminetreasures.utils.InventoryUtils;
import dev.pretti.prtminetreasures.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Crate extends BaseCrate<Crate>
{
  private final PlaceholderManager placeholderManager;

  // Properties
  private final CrateSettings settings;
  private final int           crateRows;
  private final CrateHologram crateHologram;

  // Vars
  private long    createTime;
  private boolean isFirstOpen = true;

  // Data
  private ItemStack[] items;
  private Inventory   inventory;

  /**
   * Contrutor da classe
   */
  public Crate(PrtMineTreasures plugin, @NotNull Location location, @NotNull List<ItemStack> items, @NotNull CrateSettings settings)
  {
    super(location);
    this.placeholderManager = plugin.getPlaceholderManager();
    this.items              = items.toArray(new ItemStack[0]);
    this.crateRows          = Math.min(Math.max(1, settings.getCrateRows()), 6);
    this.settings           = settings;
    crateHologram           = new CrateHologram(plugin, this, settings.getHologramSettings());
  }


  /**
   * Retornos das propriedades
   */
  public int getDestroySeconds()
  {
    return settings.getDestroySeconds();
  }

  public SoundType getOpenSound()
  {
    return settings.getOpenSound();
  }

  public SoundType getCloseSound()
  {
    return settings.getCloseSound();
  }

  /**
   * Métodos de atualização
   */
  @Override
  public void update()
  {
    crateHologram.update();
    try
      {
        Block block = getLocation().getBlock();
        if(block.getType().equals(Material.AIR) || !block.getType().equals(getBlock()))
          {
            Bukkit.getScheduler().runTaskLater(PrtMineTreasures.getInstance(), () -> destroy(true), 0L);
          }
      }
    catch(Exception ignored)
      {
      }
  }


  /**
   * Métodos de manipulação
   */
  @Override
  public boolean create()
  {
    ICrate<?> last = toPut();
    if(last != null)
      {
        last.destroy(false);
      }
    createTime = TimeUtils.getSystemTime();
    toBlock();
    crateHologram.create(getLocation());


    return true;
  }

  @Override
  public void destroy(boolean dropItems)
  {
    crateCloseInventories();
    if(dropItems && items != null)
      {
        for(ItemStack item : items)
          {
            if(item != null)
              {
                DropUtils.drop(getLocation().clone().add(0.5, 0.5, 0.5), item);
              }
          }
      }
    items     = null;
    inventory = null;
    if(getLocation().getBlock().getType().equals(getBlock()))
      {
        toAir();
      }
    crateHologram.delete();
    toRemove();
  }

  @Override
  public EnumCrateOpenType open(Player player)
  {
    if(isOwner(player))
      {
        if(crateOpen())
          {
            BaseCrate.closeOpen(player);
            player.openInventory(inventory);
            PLAYER_CRATES.put(player.getUniqueId(), this);
            SoundType openSound = getOpenSound();
            if(openSound != null)
              {
                player.playSound(getLocation(), openSound.getSound(), openSound.getVolume(), openSound.getPitch());
              }
            return EnumCrateOpenType.SUCCESS;
          }
        return EnumCrateOpenType.EMPTY;
      }
    return EnumCrateOpenType.OWNER;
  }

  @Override
  public EnumCrateCloseType close(Player player)
  {
    if(isViewer(player))
      {
        int viewers = getViewers();
        PLAYER_CRATES.remove(player.getUniqueId());
        player.closeInventory();
        SoundType closeSound = getCloseSound();
        if(closeSound != null)
          {
            player.playSound(getLocation(), closeSound.getSound(), closeSound.getVolume(), closeSound.getPitch());
          }
        if(viewers <= 1 && !crateClose())
          {
            crateHologram.setStateType(EnumCrateHologramStateType.DESTROY);
            Bukkit.getScheduler().runTaskLater(PrtMineTreasures.getInstance(), () -> destroy(false), 30L);
            return EnumCrateCloseType.EMPTY;
          }
        return EnumCrateCloseType.SUCCESS;
      }
    return EnumCrateCloseType.NOT_VIEWER;
  }

  @Override
  public boolean closeAll()
  {
    if(crateCloseInventories())
      {
        crateClose();
        destroy(false);
        return true;
      }
    return false;
  }

  /**
   * Métodos de retornos
   */
  @Override
  public boolean isOpen()
  {
    return inventory != null;
  }

  @Override
  public boolean isEmpty()
  {
    return items == null || items.length == 0;
  }

  public int getViewers()
  {
    return isOpen() ? inventory.getViewers().size() : 0;
  }

  public boolean isViewer(Player player)
  {
    return isOpen() && inventory.getViewers().contains(player);
  }

  @Override
  public boolean isOwner(Player player)
  {
    Player owner = getOwner();
    return !isOwnerOnly() || owner == null || owner == player || player.hasPermission(PermissionsConstants.PERM_BYPASS_OWNER);
  }

  @Override
  public boolean isExpired()
  {
    return (TimeUtils.getSystemTime() - createTime) >= getDestroySeconds();
  }

  @Override
  public long getTime()
  {
    return createTime;
  }

  @Override
  public long getTimeLeft()
  {
    if(isExpired())
      {
        return 0;
      }
    return getDestroySeconds() - (TimeUtils.getSystemTime() - createTime);
  }

  /**
   * Métodos privados
   */
  private boolean crateOpen()
  {
    if(isOpen())
      {
        return true;
      }
    if(items == null)
      {
        return false;
      }
    inventory = Bukkit.createInventory(null, 9 * crateRows, placeholderManager.replaceCrateAll(settings.getTitle(), this));
    if(isFirstOpen)
      {
        isFirstOpen = false;
        for(ItemStack item : items)
          {
            if(item != null)
              {
                InventoryUtils.addItem(inventory, item, true);
              }
          }
      }
    else
      {
        inventory.setContents(items);
      }
    items = null;
    return true;
  }

  private boolean crateClose()
  {
    boolean isEmpty = InventoryUtils.isEmpty(inventory);
    if(!isEmpty)
      {
        items = inventory.getContents();
      }
    inventory = null;
    return !isEmpty;
  }

  private boolean crateCloseInventories()
  {
    if(isOpen())
      {
        List<HumanEntity> players    = new ArrayList<>(inventory.getViewers());
        boolean           hasPlayers = false;
        for(HumanEntity player : players)
          {
            PLAYER_CRATES.remove(player.getUniqueId());
            player.closeInventory();
            hasPlayers = true;
          }

        return hasPlayers;
      }
    return false;
  }

  /**
   * Retorna a classe atual sem causar erros
   */
  @Override
  protected Crate self()
  {
    return this;
  }
}
