package dev.pretti.prtminetreasures.crates;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.datatypes.SoundType;
import dev.pretti.prtminetreasures.enums.EnumCrateCloseType;
import dev.pretti.prtminetreasures.enums.EnumCrateHologramStateType;
import dev.pretti.prtminetreasures.enums.EnumCrateOpenType;
import dev.pretti.prtminetreasures.holograms.CrateHologram;
import dev.pretti.prtminetreasures.utils.InventoryUtils;
import dev.pretti.prtminetreasures.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Crate extends BaseCrate<Crate>
{
  // Properties
  private       int           destroySeconds = 300;
  private       String        title          = "Treasures";
  private       SoundType     openSound      = null;
  private       SoundType     closeSound     = null;
  private final int           crateRows;
  private final CrateHologram crateHologram = new CrateHologram(this);

  // Vars
  private long    createTime;
  private boolean isFirstOpen = true;

  // Data
  private ItemStack[] items;
  private Inventory   inventory;

  /**
   * Contrutor da classe
   */
  public Crate(@NotNull Location location, @NotNull List<ItemStack> items, int rows)
  {
    super(location);
    this.items     = items.toArray(new ItemStack[0]);
    this.crateRows = Math.min(Math.max(1, rows), 6);
  }

  /**
   * Definição das propriedades
   */
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

  public Crate setOpenSound(SoundType openSound)
  {
    this.openSound = openSound;
    return this;
  }

  public Crate setCloseSound(SoundType closeSound)
  {
    this.closeSound = closeSound;
    return this;
  }

  public Crate setCrateHologram(boolean show, double height, @Nullable String[] lines, @Nullable String[] linesDestroy)
  {
    crateHologram.setShow(show).setHeight(height).setLines(lines, linesDestroy);
    return this;
  }

  /**
   * Retornos das propriedades
   */
  public int getDestroySeconds()
  {
    return destroySeconds;
  }

  /**
   * Métodos de atualização
   */
  @Override
  public void update()
  {
    crateHologram.update();
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
        last.destroy();
      }
    createTime = TimeUtils.getCurrentTime();
    toBlock();
    crateHologram.create(getLocation());
    return true;
  }

  @Override
  public void destroy()
  {
    crateCloseInventories();
    items     = null;
    inventory = null;
    toAir();
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
        if(closeSound != null)
          {
            player.playSound(getLocation(), closeSound.getSound(), closeSound.getVolume(), closeSound.getPitch());
          }
        if(viewers <= 1 && !crateClose())
          {
            crateHologram.setStateType(EnumCrateHologramStateType.DESTROY);
            Bukkit.getScheduler().runTaskLater(PrtMineTreasures.getInstance(), this::destroy, 30L);
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
        destroy();
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
    return !isOwnerOnly() || owner == null || owner == player;
  }

  @Override
  public boolean isExpired()
  {
    return (TimeUtils.getCurrentTime() - createTime) >= destroySeconds;
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
    return destroySeconds - (TimeUtils.getCurrentTime() - createTime);
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
