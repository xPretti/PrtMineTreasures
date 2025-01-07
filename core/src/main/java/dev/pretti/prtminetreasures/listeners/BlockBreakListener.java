package dev.pretti.prtminetreasures.listeners;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.configs.interfaces.IOptionsConfig;
import dev.pretti.prtminetreasures.treasures.BreakProcessors;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener
{
  private final BreakProcessors breakProcessors;

  private final IOptionsConfig optionsConfig;

  /**
   * Contrutor da classe
   */
  public BlockBreakListener(PrtMineTreasures plugin)
  {
    this.breakProcessors = plugin.getBreakProcessors();
    this.optionsConfig   = plugin.getConfigManager().getOptionsConfig();
  }

  /**
   * Monipulador do evento
   */
  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event)
  {
    Player player = event.getPlayer();
    if(player.getGameMode().equals(GameMode.SURVIVAL))
      {
        Block block = event.getBlock();
        if(breakProcessors.process(player, block.getLocation()))
          {
            if(optionsConfig.isRemoveVanillaDrops())
              {
                block.setType(Material.AIR);
                event.setCancelled(true);
              }
          }
      }
  }
}
