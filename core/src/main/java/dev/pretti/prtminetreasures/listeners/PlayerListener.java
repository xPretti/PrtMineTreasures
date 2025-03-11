package dev.pretti.prtminetreasures.listeners;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.managers.CrateManager;
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
  private final CrateManager crateManager;

  /**
   * Contrutor da classe
   */
  public PlayerListener(PrtMineTreasures plugin)
  {
    this.crateManager = plugin.getCrateManager();
  }

  /**
   * Evento de abertura do inventário
   */
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event)
  {
    Player player = event.getPlayer();
    if(crateManager.inCrateMenu(player))
      {
        crateManager.closeCrate(player);
      }
  }

  /**
   * Evento de interação com o tesouro
   */
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event)
  {
    Action action = event.getAction();
    if(action != Action.RIGHT_CLICK_BLOCK)
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
    if(crateManager.isCrate(location))
      {
        crateManager.openCrate(player, location);
        event.setCancelled(true);
      }
  }
}
