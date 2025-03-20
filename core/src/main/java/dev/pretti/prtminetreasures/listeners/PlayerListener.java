package dev.pretti.prtminetreasures.listeners;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.crate.Crate;
import dev.pretti.prtminetreasures.crates.crate.Crates;
import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener
{
  private final Crates crates;

  /**
   * Contrutor da classe
   */
  public PlayerListener(PrtMineTreasures plugin)
  {
    this.crates = plugin.getCrateManager().getCrates();
  }

  /**
   * Evento de abertura do inventário
   */
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event)
  {
    Player player = event.getPlayer();
    if(crates.inMenu(player))
      {
        crates.close(player);
      }
    Crate.LOOK_CRATES.remove(player);
  }

  /**
   * Evento de interação com o tesouro
   */
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event)
  {
    Block block = event.getClickedBlock();
    if(block == null)
      {
        return;
      }
    Location  location = block.getLocation();
    ICrate<?> crate    = Crate.getCrate(location);
    if(crate != null)
      {
        Player player = event.getPlayer();
        if(crate.getOwner() == player)
          {
            Crate.lookCrate(player, crate);
          }
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
          {
            crates.open(player, location);
            event.setCancelled(true);
          }
      }
  }
}
