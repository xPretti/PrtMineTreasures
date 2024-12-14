package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.treasures.conditions.base.NumberComparator;
import dev.pretti.treasuresapi.enums.EnumConditionType;
import org.jetbrains.annotations.NotNull;

public class NumberLessOrEqualsCondition extends NumberComparator
{
  /**
   * Construtor da classe
   */
  public NumberLessOrEqualsCondition(PlaceholderApiIntegration placeholderApiIntegration, String input, String output)
  {
    super(placeholderApiIntegration, input, output);
  }

  @Override
  protected boolean compare(double input, double output)
  {
    return input <= output;
  }

  /**
   * Retornos
   */
  @Override
  public @NotNull EnumConditionType getCondType()
  {
    return EnumConditionType.NUMBER_LESS_OR_EQUALS;
  }
}
