package dev.pretti.prtminetreasures.crates;

import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.versions.VersionsManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class BaseCrate<T extends BaseCrate<T>> implements ICrate<T>
{
  private final static   ConcurrentMap<Location, ICrate<?>> CRATES        = new ConcurrentHashMap<>();
  protected final static HashMap<UUID, ICrate<?>>           PLAYER_CRATES = new HashMap<>(); // CUIDADO, PRECISA REMOVER A LOCALIZAÇÃO DO PLAYER QUANDO O PLAYER SAIR OU FECHAR O INV

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
    return CRATES.put(getLocation(), this);
  }

  protected void toRemove()
  {
    CRATES.remove(getLocation());
  }

  /**
   * Métodos de retornos estáticos
   */
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
