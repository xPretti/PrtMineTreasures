package dev.pretti.prtminetreasures.crates.crate;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.configs.types.CrateConfig;
import dev.pretti.prtminetreasures.configs.types.MessagesConfig;
import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.enums.EnumCrateOpenType;
import dev.pretti.prtminetreasures.utils.CoordUtils;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Crates
{
  private final PrtMineTreasures plugin;
  private final MessagesConfig   messagesConfig;
  private final CrateConfig      cratesConfig;

  /**
   * Contrutor da classe
   */
  public Crates(PrtMineTreasures plugin)
  {
    this.plugin    = plugin;
    messagesConfig = plugin.getConfigManager().getMessagesConfig();
    cratesConfig   = plugin.getConfigManager().getCratesConfig();
  }

  /**
   * Métodos de inicialização
   */
  public void init()
  {
  }

  public void deinit()
  {
    for(ICrate<?> crate : Crate.getCrates())
      {
        if(crate != null)
          {
            crate.destroy(false);
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
              String message = plugin.getPlaceholderManager().replaceCrateAll(messagesConfig.getCrateOwnerOnlyMessage(), crate);
              if(message != null)
                {
                  player.sendMessage(message);
                }
              return false;
            }
            default:
            {
              String message = plugin.getPlaceholderManager().replaceCrateAll(messagesConfig.getCrateEmptyMessage(), crate);
              if(message != null)
                {
                  player.sendMessage(message);
                }
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
        ICrate<Crate> crate     = new Crate(plugin.getPlaceholderManager(), loc, items, cratesConfig.getInventoryRows());
        crate.setOwner(player)
                .setOwnerOnly(true)
                .setDestroySeconds(cratesConfig.getDestroySeconds())
                .setOwnerOnly(cratesConfig.isOwnerOnly())
                .setOpenSound(cratesConfig.getOpenSound())
                .setCloseSound(cratesConfig.getCloseSound())
                .setTitle(messagesConfig.getCrateInventoryTitleMessage())
                .setBlock(cratesConfig.getBlockType(), chestFace)
                .create();
        return true;
      }
    return false;
  }

  public void destroy(@NotNull Location loc)
  {
    ICrate<?> crate = Crate.getCrate(loc);
    if(crate != null)
      {
        crate.destroy(false);
      }
  }
}