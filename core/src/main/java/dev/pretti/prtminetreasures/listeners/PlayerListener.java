package dev.pretti.prtminetreasures.listeners;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.Crates;
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
    this.crates = plugin.getCrateManager();
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
  }

  /**
   * Evento de interação com o tesouro
   */
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event)
  {
    if(event.getAction() != Action.RIGHT_CLICK_BLOCK)
      {
        return;
      }
    Block block = event.getClickedBlock();
    if(block == null)
      {
        return;
      }
    Player   player   = event.getPlayer();
    Location location = block.getLocation();
    if(crates.isCrate(location))
      {
        crates.open(player, location);
        event.setCancelled(true);
      }
  }
}
