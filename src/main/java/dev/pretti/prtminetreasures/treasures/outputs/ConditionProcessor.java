package dev.pretti.prtminetreasures.treasures.outputs;

import dev.pretti.treasuresapi.conditions.types.BiomeCondition;
import dev.pretti.treasuresapi.conditions.types.WorldCondition;
import dev.pretti.treasuresapi.conditions.types.base.Condition;
import dev.pretti.treasuresapi.processors.context.TreasureContext;
import dev.pretti.treasuresapi.processors.interfaces.outputs.IConditionOutput;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ConditionProcessor implements IConditionOutput
{
  /**
   * Método de processamento
   */
  @Override
  public boolean process(@NotNull TreasureContext context, Condition condition)
  {
    if(condition != null)
      {
        switch(condition.getType())
          {
            case WORLD:
              return runWorld(context, (WorldCondition) condition);
            case BIOME:
              return runBiome(context, (BiomeCondition) condition);
            default:
              break;
          }
      }
    return true;
  }

  /**
   * Métodos de execução
   */
  protected boolean runWorld(TreasureContext context, WorldCondition condition)
  {
    String worldName = context.getPlayer().getWorld().getName();
    return condition.IsPassed(worldName);
  }

  protected boolean runBiome(TreasureContext context, BiomeCondition condition)
  {
    Player player = context.getPlayer();
    int    x      = player.getLocation().getBlockX();
    int    z      = player.getLocation().getBlockZ();
    return condition.IsPassed(player.getWorld().getBiome(x, z).name());
  }
}
