package dev.pretti.prtminetreasures.treasures.builder;

import dev.pretti.prtminetreasures.treasures.conditions.BiomeCondition;
import dev.pretti.prtminetreasures.treasures.conditions.BlockCondition;
import dev.pretti.prtminetreasures.treasures.conditions.WorldCondition;
import dev.pretti.treasuresapi.conditions.interfaces.ICondition;
import dev.pretti.treasuresapi.conditions.interfaces.IConditionsBuilder;
import dev.pretti.treasuresapi.enums.EnumAccessType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MineConditionsBuilder implements IConditionsBuilder
{
  @Override
  public ICondition buildWorld(@NotNull EnumAccessType enumAccessType, @NotNull List<String> list)
  {
    return new WorldCondition(enumAccessType, list);
  }

  @Override
  public ICondition buildBiome(@NotNull EnumAccessType enumAccessType, @NotNull List<String> list)
  {
    return new BiomeCondition(enumAccessType, list);
  }

  @Override
  public ICondition buildBlock(@NotNull EnumAccessType enumAccessType, @NotNull List<String> list)
  {
    return new BlockCondition(enumAccessType, list);
  }
}
