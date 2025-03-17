package dev.pretti.prtminetreasures.crates;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.datatypes.SoundType;
import dev.pretti.prtminetreasures.enums.EnumCrateOpenType;
import dev.pretti.prtminetreasures.runnables.CrateRunnable;
import dev.pretti.prtminetreasures.utils.CoordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Crates
{
  private final PrtMineTreasures plugin;
  private       int              taskId = -1;

  /**
   * Contrutor da classe
   */
  public Crates(PrtMineTreasures plugin)
  {
    this.plugin = plugin;
  }

  /**
   * Métodos de inicialização
   */
  public void init()
  {
    if(taskId != -1)
      {
        return;
      }
    taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new CrateRunnable(), 0L, 20L).getTaskId();
  }

  public void deinit()
  {
    if(taskId != -1)
      {
        Bukkit.getScheduler().cancelTask(taskId);
        taskId = -1;
      }
    for(ICrate<?> crate : Crate.getCrates())
      {
        if(crate != null)
          {
            crate.destroy();
          }
      }
  }

  /**
   * Métodos de retornos
   */
  public boolean isCrate(@NotNull Location location)
  {
    return Crate.isCrate(location);
  }

  public boolean inMenu(@NotNull Player player)
  {
    return Crate.inMenu(player);
  }

  public boolean open(@NotNull Player player, @NotNull Location location)
  {
    ICrate<?> crate = Crate.getCrate(location);
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

  public boolean close(@NotNull Player player, @NotNull Location location)
  {
    ICrate<?> crate = Crate.getCrate(location);
    if(crate != null)
      {
        crate.close(player);
        return true;
      }
    return false;
  }

  public void close(@NotNull Player player)
  {
    Crate.closeOpen(player);
  }


  public boolean create(@NotNull Player player, @NotNull Location loc, @NotNull List<ItemStack> items)
  {
    if(!Crate.isCrate(loc))
      {
        BlockFace     chestFace = CoordUtils.getCompassDirection(player.getLocation().getYaw());
        ICrate<Crate> crate     = new Crate(plugin, loc, items, 9);
        crate.setOwner(player)
                .setOwnerOnly(true)
                .setBlock(Material.CHEST, chestFace)
                .setTitle("Jujubas doces")
                .setDestroySeconds(300)
                .setOpenSound(new SoundType(Sound.valueOf("CHEST_OPEN"), 1, 1))
                .setCloseSound(new SoundType(Sound.valueOf("CHEST_CLOSE"), 1, 1))
                .create();


        player.getPlayer();
        player.sendMessage("§eVoce encontrou um tesouro, abra para ver os itens!");
        return true;
      }
    return false;
  }

  public void destroy(@NotNull Location loc)
  {
    ICrate<?> crate = Crate.getCrate(loc);
    if(crate != null)
      {
        crate.destroy();
      }
  }
}