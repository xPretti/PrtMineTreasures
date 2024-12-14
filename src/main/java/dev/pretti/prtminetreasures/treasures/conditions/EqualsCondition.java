package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.treasures.conditions.base.StringComparator;
import dev.pretti.treasuresapi.enums.EnumConditionType;
import org.jetbrains.annotations.NotNull;

public class EqualsCondition extends StringComparator
{
  /**
   * Construtor da classe
   */
  public EqualsCondition(PlaceholderApiIntegration placeholderApiIntegration, String input, String output, boolean invert)
  {
    super(placeholderApiIntegration, input, output, invert);
  }

  /**
   * Método de comparação
   */
  @Override
  protected boolean compare(String input, String output)
  {
    return input.equals(output);
  }

  /**
   * Retornos
   */
  @Override
  public @NotNull EnumConditionType getCondType()
  {
    return EnumConditionType.EQUALS;
  }
}
