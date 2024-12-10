package dev.pretti.prtminetreasures.treasures.conditions.base;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.treasuresapi.conditions.interfaces.ICondition;
import dev.pretti.treasuresapi.processors.context.TreasureContext;
import org.jetbrains.annotations.NotNull;

public abstract class StringComparator implements ICondition
{
  private final PlaceholderApiIntegration placeholderApiIntegration;

  private final String  input;
  private final String  output;
  private final boolean invert;

  /**
   * Construtor da classe
   */
  public StringComparator(PlaceholderApiIntegration placeholderApiIntegration, String input, String output, boolean invert)
  {
    this.placeholderApiIntegration = placeholderApiIntegration;
    this.input                     = input;
    this.output                    = output;
    this.invert                    = invert;
  }

  /**
   * Métodos abstractos
   */
  protected abstract boolean compare(String input, String output);

  /**
   * Método de verificação
   */
  @Override
  public boolean evaluate(@NotNull TreasureContext treasureContext)
  {
    String  inputValue  = placeholderApiIntegration.setPlaceholders(treasureContext.getPlayer(), input);
    String  outputValue = placeholderApiIntegration.setPlaceholders(treasureContext.getPlayer(), output);
    boolean result      = compare(inputValue, outputValue);
    return invert != result;
  }

}
