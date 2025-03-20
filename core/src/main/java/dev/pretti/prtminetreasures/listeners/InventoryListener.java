package dev.pretti.prtminetreasures.listeners;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.crate.Crates;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryListener implements Listener
{
  private final Crates crates;

  /**
   * Contrutor da classe
   */
  public InventoryListener(PrtMineTreasures plugin)
  {
    this.crates = plugin.getCrateManager().getCrates();
  }

  /**
   * Evento de saída do jogador
   */
  @EventHandler
  public void onInventoryOpen(InventoryOpenEvent event)
  {
    Player player = (Player) event.getPlayer();
    if(player.isSleeping())
      {
        event.setCancelled(true);
      }
    if(crates.inMenu(player))
      {
        crates.close(player);
      }
  }

  /**
   * Evento de fechamento do inventário
   */
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event)
  {
    Player player = (Player) event.getPlayer();
    if(crates.inMenu(player))
      {
        crates.close(player);
      }
  }

}
