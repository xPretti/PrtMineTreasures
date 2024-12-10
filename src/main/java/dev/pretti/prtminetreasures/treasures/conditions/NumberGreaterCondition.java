package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.treasures.conditions.base.NumberComparator;

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
}
