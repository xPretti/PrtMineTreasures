package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.treasuresapi.conditions.interfaces.ICondition;
import dev.pretti.treasuresapi.enums.EnumAccessType;
import dev.pretti.treasuresapi.processors.context.TreasureContext;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;

public class WorldCondition implements ICondition
{
  private final EnumAccessType  accessType;
  private final HashSet<String> worldNames = new HashSet<>();

  /**
   * Construtor da classe
   */
  public WorldCondition(@NotNull EnumAccessType enumAccessType, @NotNull List<String> list)
  {
    this.accessType = enumAccessType;
    this.worldNames.addAll(list);
  }

  /**
  * Método de verificação
  */
  @Override
  public boolean evaluate(@NotNull TreasureContext treasureContext)
  {
    if(worldNames.isEmpty())
      {
        return true;
      }
    boolean result = worldNames.contains(treasureContext.getPlayer().getWorld().getName());
    if(accessType.equals(EnumAccessType.WHITELIST))
      {
        return result;
      }
    else if(accessType.equals(EnumAccessType.BLACKLIST))
      {
        return !result;
      }
    return true;
  }
}
