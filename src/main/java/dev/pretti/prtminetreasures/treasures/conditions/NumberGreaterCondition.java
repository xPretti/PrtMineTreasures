package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.treasures.conditions.base.NumberComparator;
import dev.pretti.treasuresapi.enums.EnumConditionType;
import org.jetbrains.annotations.NotNull;

public class NumberGreaterCondition extends NumberComparator
{
  /**
   * Construtor da classe
   */
  public NumberGreaterCondition(PlaceholderApiIntegration placeholderApiIntegration, String input, String output)
  {
    super(placeholderApiIntegration, input, output);
  }

  @Override
  protected boolean compare(double input, double output)
  {
    return input > output;
  }

  /**
   * Retornos
   */
  @Override
  public @NotNull EnumConditionType getCondType()
  {
    return EnumConditionType.NUMBER_GREATER;
  }
}
