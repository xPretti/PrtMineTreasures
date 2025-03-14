package dev.pretti.prtminetreasures.crates;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.interfaces.ICrates;
import dev.pretti.prtminetreasures.enums.EnumCrateCloseType;
import dev.pretti.prtminetreasures.enums.EnumCrateOpenType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class CrateManager implements ICrates
{
  private final HashMap<Location, Crate> crates = new HashMap<>();

  /**
   * Retorna se o player estiver estiver com menu de crate aberto
   */
  public boolean inMenu(@NotNull Player player)
  {
    return Crate.PLAYER_CRATES.containsKey(player.getUniqueId());
  }

  public boolean isCrate(@NotNull Location location)
  {
    return crates.containsKey(location);
  }

  @Nullable
  public Crate getCrate(@NotNull Location location)
  {
    return crates.get(location);
  }

  @NotNull
  public Crate create(@NotNull Location location, @NotNull List<ItemStack> items)
  {
    if(isCrate(location))
      {
        destroy(location);
      }
    Crate crate = new Crate(location, items);
    crates.put(location, crate);
    return crate;
  }

  @Nullable
  public Crate getOpen(@NotNull Player player)
  {
    return Crate.PLAYER_CRATES.get(player.getUniqueId());
  }

  public void destroy(@NotNull Location location)
  {
    Crate crate = crates.get(location);
    if(crate != null)
      {
        crate.destroy();
      }
    crates.remove(location);
  }

  public boolean open(@NotNull Player player, @NotNull Location location)
  {
    Crate crate = crates.get(location);
    if(crate != null)
      {
        EnumCrateOpenType type = crate.open(player);
        switch(type)
          {
            case SUCCESS:
            {
              return true;
            }
            case OWNER:
            {
              player.sendMessage("§cEste tesouro pertence a: " + crate.getOwner().getDisplayName());
              return false;
            }
            default:
            {
              player.sendMessage("§cEste tesouro esta vazio.");
              return false;
            }
          }
      }
    return false;
  }

  public boolean close(@NotNull Player player)
  {
    Crate crate = getOpen(player);
    if(crate == null)
      {
        return false;
      }
    EnumCrateCloseType type = crate.close(player);
    switch(type)
      {
        case SUCCESS:
        {
          return true;
        }
        case EMPTY:
        {
          Bukkit.getScheduler().runTaskLater(PrtMineTreasures.getInstance(), new Runnable() {
            @Override
            public void run() {
              crate.destroy();
              crates.remove(crate.getLocation());
            }
          }, 40L);
          return true;
        }
        default:
        {
          return false;
        }
      }
  }
}
