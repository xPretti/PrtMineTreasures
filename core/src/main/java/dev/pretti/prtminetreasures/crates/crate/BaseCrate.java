package dev.pretti.prtminetreasures.crates.crate;

import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.events.CrateLookEvent;
import dev.pretti.prtminetreasures.structs.CratePlayerStats;
import dev.pretti.prtminetreasures.utils.TimeUtils;
import dev.pretti.prtminetreasures.versions.VersionsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class BaseCrate<T extends BaseCrate<T>> implements ICrate<T>
{
  public final static    ConcurrentMap<Player, CratePlayerStats>   LOOK_CRATES   = new ConcurrentHashMap<>();
  protected final static ConcurrentMap<ICrate<?>, HashSet<Player>> LOOK_IN_CRATE = new ConcurrentHashMap<>();
  private final static   ConcurrentMap<Location, ICrate<?>>        CRATES        = new ConcurrentHashMap<>();
  protected final static HashMap<UUID, ICrate<?>>                  PLAYER_CRATES = new HashMap<>();

  // Properties
  private final Location  location;
  private       Player    owner;
  private       Material  block;
  private       BlockFace face;
  private       boolean   ownerOnly;


  /**
   * Contrutor da classe
   */
  protected BaseCrate(Location location)
  {
    this.location = location;
  }

  /**
   * Retorna a classe atual sem causar erros
   */
  protected abstract T self();

  /**
   * Métodos de retornos
   */
  public Location getLocation()
  {
    return location;
  }

  public Player getOwner()
  {
    return owner;
  }

  public T setOwner(Player owner)
  {
    this.owner = owner;
    return self();
  }

  @Override
  public boolean isOwnerOnly()
  {
    return ownerOnly;
  }

  @Override
  public T setOwnerOnly(boolean ownerOnly)
  {
    this.ownerOnly = ownerOnly;
    return self();
  }

  @Override
  public Material getBlock()
  {
    return block;
  }

  public T setBlock(Material block, BlockFace face)
  {
    this.block = block;
    this.face  = face;
    return self();
  }

  public BlockFace getFace()
  {
    return face;
  }

  /**
   * Ações
   */
  protected void toBlock()
  {
    location.getBlock().setType(getBlock());
    VersionsManager.getInstance().getBlockfaceVersion().setFace(location.getBlock(), getFace());
  }

  protected void toAir()
  {
    location.getBlock().setType(Material.AIR);
  }

  protected ICrate<?> toPut()
  {
    ICrate<?> put = CRATES.put(getLocation(), this);
    LOOK_IN_CRATE.put(this, new HashSet<>());
    return put;
  }

  protected void toRemove()
  {
    HashSet<Player> players = LOOK_IN_CRATE.get(this);
    if(players != null)
      {
        for(Player player : players)
          {
            LOOK_CRATES.remove(player);
          }
      }
    LOOK_IN_CRATE.remove(this);
    CRATES.remove(getLocation());
  }

  /**
   * Métodos de retornos estáticos
   */
  public static int Total()
  {
    return CRATES.size();
  }

  public static boolean isLookCrate(@NotNull Player player)
  {
    Block     block = player.getTargetBlock(null, 5);
    ICrate<?> crate = getCrate(block.getLocation());
    if(crate != null)
      {
        lookCrate(player, crate);
        return true;
      }
    unlookCrate(player);
    return false;
  }

  public static void lookCrate(@NotNull Player player, @NotNull ICrate<?> crate)
  {
    CratePlayerStats other = LOOK_CRATES.get(player);
    if(other == null)
      {
        LOOK_CRATES.put(player, new CratePlayerStats(TimeUtils.getSystemTime(), crate));
        LOOK_IN_CRATE.get(crate).add(player);
        Bukkit.getPluginManager().callEvent(new CrateLookEvent(crate, player));
      }
    else if(other.interactCrate != crate)
      {
        LOOK_CRATES.put(player, new CratePlayerStats(TimeUtils.getSystemTime(), crate));
        LOOK_IN_CRATE.get(other.interactCrate).remove(player);
        LOOK_IN_CRATE.get(crate).add(player);
        Bukkit.getPluginManager().callEvent(new CrateLookEvent(crate, player));
      }
  }

  public static void unlookCrate(@NotNull Player player)
  {
    CratePlayerStats crate = LOOK_CRATES.get(player);
    if(crate != null)
      {
        LOOK_IN_CRATE.get(crate.interactCrate).remove(player);
        LOOK_CRATES.remove(player);
      }
  }

  public static boolean inMenu(@NotNull Player player)
  {
    return PLAYER_CRATES.containsKey(player.getUniqueId());
  }

  public static boolean isCrate(@NotNull Location location)
  {
    return CRATES.containsKey(location);
  }

  @Nullable
  public static ICrate<?> getCrate(@NotNull Location location)
  {
    return CRATES.get(location);
  }

  public static Collection<ICrate<?>> getCrates()
  {
    return CRATES.values();
  }

  @Nullable
  public static ICrate<?> getOpen(@NotNull Player player)
  {
    return PLAYER_CRATES.get(player.getUniqueId());
  }

  public static void closeOpen(@NotNull Player player)
  {
    ICrate<?> currentCrate = getOpen(player);
    if(currentCrate != null)
      {
        currentCrate.close(player);
      }
  }
}
