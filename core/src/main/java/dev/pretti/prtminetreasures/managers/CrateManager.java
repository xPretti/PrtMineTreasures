package dev.pretti.prtminetreasures.managers;

import dev.pretti.prtminetreasures.crates.Crate;
import dev.pretti.prtminetreasures.enums.EnumCrateOpenType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CrateManager
{
  private final HashMap<Location, Crate> crates        = new HashMap<>();
  private final List<Location>           expiredCrates = new ArrayList<>();

  /**
   * Retorna se o player estiver estiver com menu de crate aberto
   */
  public boolean inCrateMenu(@NotNull Player player)
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
  public Crate getOrCreateCrate(@NotNull Location location, List<ItemStack> items)
  {
    return crates.computeIfAbsent(location, l -> new Crate(l, items));
  }

  @Nullable
  public Crate getOpenCrate(@NotNull Player player)
  {
    return Crate.PLAYER_CRATES.get(player.getUniqueId());
  }

  public void destroyCrate(@NotNull Location location)
  {
    Crate crate = crates.get(location);
    if(crate != null)
      {
        crate.destroy();
      }
    crates.remove(location);
  }

  public void closeCrate(@NotNull Player player)
  {
    Crate crate = getOpenCrate(player);
    if(crate != null)
      {
        crate.close(player);
      }
  }

  public boolean openCrate(@NotNull Player player, @NotNull Location location)
  {
    Crate crate = crates.get(location);
    if(crate != null)
      {
        EnumCrateOpenType type = crate.open(player);
        switch(type)
          {
            case SUCCESS:
            {
              player.sendMessage("§eTesouro aberto com sucesso!");
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
}
