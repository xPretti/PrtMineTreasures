package dev.pretti.prtminetreasures.listeners;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.treasures.BreakProcessors;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener
{
  private final BreakProcessors breakProcessors;

  /**
   * Contrutor da classe
   */
  public BlockBreakListener(PrtMineTreasures plugin)
  {
    breakProcessors = plugin.getBreakProcessors();
  }

  /**
   * Monipulador do evento
   */
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onBlockBreak(BlockBreakEvent event)
  {
    Block block = event.getBlock();
    if(breakProcessors.process(event.getPlayer(), block.getLocation()))
      {
        block.setType(Material.AIR);
        event.setCancelled(true);
      }
  }
}
