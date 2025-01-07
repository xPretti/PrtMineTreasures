package dev.pretti.prtminetreasures.listeners;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.utils.MetaUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener
{
  /**
   * Contrutor da classe
   */
  public BlockPlaceListener(PrtMineTreasures plugin)
  {
  }

  /**
   * Evento
   */
  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  void onBlockPlace(BlockPlaceEvent event)
  {
    Player player = event.getPlayer();
    if(player.getGameMode().equals(GameMode.SURVIVAL))
      {
        Block block = event.getBlockPlaced();
        if(!block.getType().equals(Material.AIR))
          {
            MetaUtils.setPlacedByPlayer(block, player);
          }
      }
  }
}
