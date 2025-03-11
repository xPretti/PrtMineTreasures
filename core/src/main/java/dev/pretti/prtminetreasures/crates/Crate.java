package dev.pretti.prtminetreasures.crates;

import dev.pretti.prtminetreasures.enums.EnumCrateOpenType;
import dev.pretti.prtminetreasures.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Crate
{
  // falta por:
  // 1. sounds
  // 2. holograma
  // 3. particle
  public static HashMap<UUID, Crate> PLAYER_CRATES = new HashMap<>();

  // Properties
  private final Location location;
  private       Player   owner;
  private       Material block          = Material.CHEST;
  private       boolean  ownerOnly      = false;
  private       int      destroySeconds = 300;
  private       int      crateRows      = 1;
  private       String   title          = "Treasures";

  // Vars
  private long createTime;

  // Data
  private List<ItemStack> items;
  private Inventory       inventory;

  /**
   * Contrutor da classe
   */
  public Crate(@NotNull Location location, @NotNull List<ItemStack> items)
  {
    this.location = location;
    this.items    = items;
  }

  /**
   * Definição das propriedades
   */
  public Crate setBlock(@NotNull Material block)
  {
    this.block = block;
    return this;
  }

  public Crate setOwnerOnly(boolean ownerOnly)
  {
    this.ownerOnly = ownerOnly;
    return this;
  }

  public Crate setDestroySeconds(int destroySeconds)
  {
    this.destroySeconds = destroySeconds;
    return this;
  }

  public Crate setTitle(@NotNull String title)
  {
    this.title = title;
    return this;
  }

  public Crate setOwner(@Nullable Player owner)
  {
    this.owner = owner;
    return this;
  }

  public Crate setCrateRows(int crateRows)
  {
    this.crateRows = Math.min(Math.max(1, crateRows), 6);
    return this;
  }

  /**
   * Retornos das propriedades
   */
  public Player getOwner()
  {
    return owner;
  }

  public Location getLocation()
  {
    return location;
  }

  public boolean isOwnerOnly()
  {
    return ownerOnly;
  }

  public int getDestroySeconds()
  {
    return destroySeconds;
  }

  /**
   * Métodos de manipulação
   */
  public boolean create()
  {
    createTime = TimeUtils.getCurrentTime();
    location.getBlock().setType(block);
    // holograma
    return true;
  }

  public void destroy()
  {
    //colocar talvez um método dropItems();
    crateCloseInventories();
    crateDestroy();
  }

  public EnumCrateOpenType open(Player player)
  {
    if(isOwner(player))
      {
        if(crateOpen())
          {
            Crate currentCrate = PLAYER_CRATES.get(player.getUniqueId());
            if(currentCrate != null && currentCrate != this)
              {
                currentCrate.close(player);
              }
            player.openInventory(inventory);
            PLAYER_CRATES.put(player.getUniqueId(), this);
            return EnumCrateOpenType.SUCCESS;
          }
        return EnumCrateOpenType.EMPTY;
      }
    return EnumCrateOpenType.OWNER;
  }

  public boolean close(Player player)
  {
    if(isViewer(player))
      {
        int viewers = getViewers();
        PLAYER_CRATES.remove(player.getUniqueId());
        player.closeInventory();
        if(viewers <= 1)
          {
            crateClose();
          }
        return true;
      }
    return false;
  }

  public boolean closeAll()
  {
    if(crateCloseInventories())
      {
        crateClose();
        return true;
      }
    return false;
  }

  /**
   * Métodos de retornos
   */
  public boolean isOpen()
  {
    return inventory != null;
  }

  public boolean isEmpty()
  {
    return items == null || items.isEmpty();
  }

  public int getViewers()
  {
    return isOpen() ? inventory.getViewers().size() : 0;
  }

  public boolean isViewer(Player player)
  {
    return isOpen() && inventory.getViewers().contains(player);
  }

  public boolean isOwner(Player player)
  {
    return !isOwnerOnly() || owner == null || owner == player;
  }

  public boolean isExpired()
  {
    return (TimeUtils.getCurrentTime() - createTime) > destroySeconds;
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
    inventory = Bukkit.createInventory(null, 9 * crateRows, title);
    items.forEach(inventory::addItem);
    items = null;
    return true;
  }

  private void crateClose()
  {
    int size = inventory.getSize();
    items = new ArrayList<>();
    ItemStack item;
    for(int i = 0; i < size; i++)
      {
        item = inventory.getItem(i);
        if(item != null)
          {
            items.add(item);
          }
      }
    inventory = null;
    if(items.isEmpty())
      {
        crateDestroy();
      }
  }

  private void crateDestroy()
  {
    items     = null;
    inventory = null;
    location.getBlock().setType(Material.AIR);
  }

  private boolean crateCloseInventories()
  {
    if(isOpen())
      {
        List<HumanEntity> players = inventory.getViewers();
        if(!players.isEmpty())
          {
            for(HumanEntity player : players)
              {
                PLAYER_CRATES.remove(player.getUniqueId());
                player.closeInventory();
              }
            return true;
          }
      }
    return false;
  }
}
