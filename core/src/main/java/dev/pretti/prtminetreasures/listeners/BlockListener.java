package dev.pretti.prtminetreasures.listeners;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.configs.interfaces.IOptionsConfig;
import dev.pretti.prtminetreasures.crates.crate.Crate;
import dev.pretti.prtminetreasures.treasures.BreakProcessors;
import dev.pretti.prtminetreasures.utils.MetaUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class BlockListener implements Listener
{
  private final BreakProcessors breakProcessors;

  private final IOptionsConfig optionsConfig;

  /**
   * Contrutor da classe
   */
  public BlockListener(PrtMineTreasures plugin)
  {
    this.breakProcessors = plugin.getBreakProcessors();
    this.optionsConfig   = plugin.getConfigManager().getOptionsConfig();
  }

  /**
   * Evento de quebra de bloco
   */
  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event)
  {
    breakProcessors.process(event);
  }

  /**
   * Evento de colocação de bloco
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

  /**
   * Evento de proteção contra quebra de blocos
   */
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onBlockExplode(EntityExplodeEvent event)
  {
    List<Block> blocks = event.blockList();
    int         size   = blocks.size();
    for(int i = size - 1; i >= 0; i--)
      {
        Block    block    = blocks.get(i);
        Location location = block.getLocation();
        if(Crate.isCrate(location))
          {
            blocks.remove(block);
          }
      }
  }
}
