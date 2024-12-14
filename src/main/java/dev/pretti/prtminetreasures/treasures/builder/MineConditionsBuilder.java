package dev.pretti.prtminetreasures.treasures.builder;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.treasures.conditions.*;
import dev.pretti.treasuresapi.conditions.interfaces.IConditionsBuilder;
import dev.pretti.treasuresapi.conditions.types.IBiomeCondition;
import dev.pretti.treasuresapi.conditions.types.IBlockCondition;
import dev.pretti.treasuresapi.conditions.types.IComparatorCondition;
import dev.pretti.treasuresapi.conditions.types.IWorldCondition;
import dev.pretti.treasuresapi.enums.EnumAccessType;
import dev.pretti.treasuresapi.enums.EnumConditionType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MineConditionsBuilder implements IConditionsBuilder
{
  private final PlaceholderApiIntegration placeholderApiIntegration;

  /**
   * Contrutor da classe
   */
  public MineConditionsBuilder(PlaceholderApiIntegration placeholderApiIntegration)
  {
    this.placeholderApiIntegration = placeholderApiIntegration;
  }

  /**
   * Construtores
   */
  @Override
  public IWorldCondition buildWorld(@NotNull EnumAccessType enumAccessType, @NotNull List<String> worlds)
  {
    return new WorldCondition(enumAccessType, worlds);
  }

  @Override
  public IBiomeCondition buildBiome(@NotNull EnumAccessType enumAccessType, @NotNull List<String> biomes)
  {
    return new BiomeCondition(enumAccessType, biomes);
  }

  @Override
  public IBlockCondition buildBlock(@NotNull EnumAccessType enumAccessType, @NotNull List<String> blocks)
  {
    return new BlockCondition(enumAccessType, blocks);
  }

  @Override
  public IComparatorCondition buildComparator(@NotNull EnumConditionType enumConditionType, @NotNull String input, @NotNull String output)
  {
    switch(enumConditionType)
      {
        case EQUALS:
          return new EqualsCondition(placeholderApiIntegration, input, output, false);
        case EQUALS_IGNORE_CASE:
          return new EqualsIgnoreCaseCondition(placeholderApiIntegration, input, output, false);
        case CONTAINS:
          return new ContainsCondition(placeholderApiIntegration, input, output, false);
        case NOT_EQUALS:
          return new EqualsCondition(placeholderApiIntegration, input, output, true);
        case NOT_EQUALS_IGNORE_CASE:
          return new EqualsIgnoreCaseCondition(placeholderApiIntegration, input, output, true);
        case NOT_CONTAINS:
          return new ContainsCondition(placeholderApiIntegration, input, output, true);
        case NUMBER_EQUALS:
          return new NumberEqualsCondition(placeholderApiIntegration, input, output);
        case NUMBER_NOT_EQUALS:
          return new NumberNotEqualsCondition(placeholderApiIntegration, input, output);
        case NUMBER_GREATER:
          return new NumberGreaterCondition(placeholderApiIntegration, input, output);
        case NUMBER_GREATER_OR_EQUALS:
          return new NumberGreaterOrEqualsCondition(placeholderApiIntegration, input, output);
        case NUMBER_LESS:
          return new NumberLessCondition(placeholderApiIntegration, input, output);
        case NUMBER_LESS_OR_EQUALS:
          return new NumberLessOrEqualsCondition(placeholderApiIntegration, input, output);
      }
    return null;
  }

}
