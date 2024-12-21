package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.prtminetreasures.utils.MetaUtils;
import dev.pretti.treasuresapi.conditions.types.IPlacedCondition;
import dev.pretti.treasuresapi.contexts.TreasureContext;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

public class PlacedCondition implements IPlacedCondition
{
  private final boolean ignored;

  /**
   * Construtor da classe
   */
  public PlacedCondition(boolean ignored)
  {
    this.ignored = ignored;
  }

  /**
   * Método de verificação
   */
  @Override
  public boolean evaluate(@NotNull TreasureContext treasureContext)
  {
    if(ignored)
      {
        Block block = treasureContext.getEventLocation().getBlock();
        return !MetaUtils.isPlacedByPlayer(block);
      }
    return true;
  }

  /**
   * Retornos
   */
  @Override
  public boolean isIgnore()
  {
    return ignored;
  }
}
