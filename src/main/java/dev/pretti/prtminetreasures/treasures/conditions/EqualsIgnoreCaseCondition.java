package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.treasures.conditions.base.StringComparator;

public class EqualsIgnoreCaseCondition extends StringComparator
{
  /**
   * Construtor da classe
   */
  public EqualsIgnoreCaseCondition(PlaceholderApiIntegration placeholderApiIntegration, String input, String output, boolean invert)
  {
    super(placeholderApiIntegration, input, output, invert);
  }

  /**
   * Método de comparação
   */
  @Override
  protected boolean compare(String input, String output)
  {
    return input.equalsIgnoreCase(output);
  }

}
