package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.treasures.conditions.interfaces.ILogicComparator;
import dev.pretti.treasuresapi.conditions.types.IComparatorCondition;
import dev.pretti.treasuresapi.contexts.TreasureContext;
import dev.pretti.treasuresapi.enums.EnumConditionType;
import org.jetbrains.annotations.NotNull;

public class StringComparatorCondition implements IComparatorCondition
{
  private final ILogicComparator<String> comparator;

  private final PlaceholderApiIntegration placeholderApiIntegration;

  private final EnumConditionType condType;
  private final String input;
  private final String output;

  /**
   * Construtor da classe
   */
  public StringComparatorCondition(PlaceholderApiIntegration placeholderApiIntegration, EnumConditionType condType, String input, String output, ILogicComparator<String> comparator)
  {
    this.comparator                = comparator;
    this.placeholderApiIntegration = placeholderApiIntegration;
    this.condType                  = condType;
    this.input                     = input;
    this.output                    = output;
  }

  /**
   * Método de verificação
   */
  @Override
  public boolean evaluate(@NotNull TreasureContext treasureContext)
  {
    String inputValue  = placeholderApiIntegration.setPlaceholders(treasureContext.getPlayer(), input);
    String outputValue = placeholderApiIntegration.setPlaceholders(treasureContext.getPlayer(), output);
    return comparator.compare(inputValue, outputValue);
  }


  /**
   * Retornos
   */
  @Override
  public @NotNull EnumConditionType getCondType()
  {
    return condType;
  }

  @Override
  public @NotNull String getInput()
  {
    return input;
  }

  @Override
  public @NotNull String getOutput()
  {
    return output;
  }
}
