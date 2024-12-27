package dev.pretti.prtminetreasures.treasures.builder;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.prtminetreasures.treasures.conditions.*;
import dev.pretti.treasuresapi.conditions.interfaces.IConditionsBuilder;
import dev.pretti.treasuresapi.conditions.types.*;
import dev.pretti.treasuresapi.datatypes.MaterialType;
import dev.pretti.treasuresapi.datatypes.MetadataConditionType;
import dev.pretti.treasuresapi.enums.EnumAccessType;
import dev.pretti.treasuresapi.enums.EnumConditionType;
import dev.pretti.treasuresapi.options.ItemConditionOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class MineConditionsBuilder implements IConditionsBuilder
{
  private final PlaceholderManager        placeholderManager;
  private final PlaceholderApiIntegration placeholderApiIntegration;

  /**
   * Contrutor da classe
   */
  public MineConditionsBuilder(PlaceholderManager placeholderManager, PlaceholderApiIntegration placeholderApiIntegration)
  {
    this.placeholderManager        = placeholderManager;
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
          return new StringComparatorCondition(placeholderApiIntegration, enumConditionType, input, output, String::equals);
        case EQUALS_IGNORE_CASE:
          return new StringComparatorCondition(placeholderApiIntegration, enumConditionType, input, output, String::equalsIgnoreCase);
        case CONTAINS:
          return new StringComparatorCondition(placeholderApiIntegration, enumConditionType, input, output, String::contains);
        case NOT_EQUALS:
          return new StringComparatorCondition(placeholderApiIntegration, enumConditionType, input, output, (v1, v2) -> !v1.equals(v2));
        case NOT_EQUALS_IGNORE_CASE:
          return new StringComparatorCondition(placeholderApiIntegration, enumConditionType, input, output, (v1, v2) -> !v1.equalsIgnoreCase(v2));
        case NOT_CONTAINS:
          return new StringComparatorCondition(placeholderApiIntegration, enumConditionType, input, output, (v1, v2) -> !v1.contains(v2));
        case NUMBER_EQUALS:
          return new NumberComparatorCondition(placeholderApiIntegration, enumConditionType, input, output, Objects::equals);
        case NUMBER_NOT_EQUALS:
          return new NumberComparatorCondition(placeholderApiIntegration, enumConditionType, input, output, (v1, v2) -> !Objects.equals(v1, v2));
        case NUMBER_GREATER:
          return new NumberComparatorCondition(placeholderApiIntegration, enumConditionType, input, output, (v1, v2) -> v1 > v2);
        case NUMBER_GREATER_OR_EQUALS:
          return new NumberComparatorCondition(placeholderApiIntegration, enumConditionType, input, output, (v1, v2) -> v1 >= v2);
        case NUMBER_LESS:
          return new NumberComparatorCondition(placeholderApiIntegration, enumConditionType, input, output, (v1, v2) -> v1 < v2);
        case NUMBER_LESS_OR_EQUALS:
          return new NumberComparatorCondition(placeholderApiIntegration, enumConditionType, input, output, (v1, v2) -> v1 <= v2);
      }
    return null;
  }

  @Override
  public IItemCondition buildItem(@NotNull EnumConditionType enumConditionType, @Nullable MaterialType materialType, int amount, @Nullable String name, @Nullable List<String> lores,
                                  @NotNull ItemConditionOptions itemConditionOptions, @Nullable List<MetadataConditionType> metadatasType)
  {
    return new ItemCondition(placeholderManager, materialType, amount, name, lores, itemConditionOptions, enumConditionType.equals(EnumConditionType.NOT_ITEM), metadatasType);
  }

  @Override
  public IPlacedCondition buildPlaced(boolean ignore)
  {
    return new PlacedCondition(ignore);
  }

}
