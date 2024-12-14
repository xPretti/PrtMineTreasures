package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.treasuresapi.conditions.types.IWorldCondition;
import dev.pretti.treasuresapi.contexts.TreasureContext;
import dev.pretti.treasuresapi.enums.EnumAccessType;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;

public class WorldCondition implements IWorldCondition
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
        return accessType.equals(EnumAccessType.BLACKLIST);
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

  /**
   * Retorna dos valores
   */
  @Override
  public @NotNull EnumAccessType getAccessType()
  {
    return accessType;
  }

  @Override
  public @NotNull HashSet<String> getWorlds()
  {
    return worldNames;
  }
}
