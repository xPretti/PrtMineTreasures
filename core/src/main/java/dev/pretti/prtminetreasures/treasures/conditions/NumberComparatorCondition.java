package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.treasures.conditions.interfaces.ILogicComparator;
import dev.pretti.prtminetreasures.utils.LogUtils;
import dev.pretti.treasuresapi.conditions.interfaces.IInvalidCondition;
import dev.pretti.treasuresapi.conditions.invalids.ComparatorInvalidCondition;
import dev.pretti.treasuresapi.conditions.types.IComparatorCondition;
import dev.pretti.treasuresapi.contexts.TreasureContext;
import dev.pretti.treasuresapi.enums.EnumConditionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NumberComparatorCondition implements IComparatorCondition
{
  private final ILogicComparator<Double> comparator;

  private final PlaceholderApiIntegration placeholderApiIntegration;

  private final EnumConditionType condType;
  private final String            input;
  private final String            output;

  private ComparatorInvalidCondition invalidCondition;

  /**
   * Construtor da classe
   */
  public NumberComparatorCondition(PlaceholderApiIntegration placeholderApiIntegration, EnumConditionType condType, String input, String output, ILogicComparator<Double> comparator)
  {
    this.placeholderApiIntegration = placeholderApiIntegration;
    this.input                     = input;
    this.condType                  = condType;
    this.output                    = output;
    this.comparator                = comparator;

    boolean s1 = !isNumber(input);
    boolean s2 = !isNumber(output);
    if(s1 || s2)
      {
        invalidCondition = new ComparatorInvalidCondition("Input value is not a number", "Output value is not a number", s1 ? input : null, s2 ? output : null);
      }
  }

  /**
   * Retorno dos parametros invalidos
   */
  @Override
  public @Nullable IInvalidCondition getInvalidCondition()
  {
    return invalidCondition;
  }

  /**
   * Método de verificação
   */
  @Override
  public boolean evaluate(@NotNull TreasureContext treasureContext)
  {
    String inputValue  = placeholderApiIntegration.setPlaceholders(treasureContext.getPlayer(), input);
    String outputValue = placeholderApiIntegration.setPlaceholders(treasureContext.getPlayer(), output);
    double doubleInput;
    double doubleOutput;
    try
      {
        doubleInput = Double.parseDouble(inputValue);
      } catch(NumberFormatException e)
      {
        String format = String.format("§8Error converting value: §c<NOT NUMBER>§8 (input: %s, parsed: %s)", input, inputValue);
        LogUtils.logError(format);
        return false;
      }
    try
      {
        doubleOutput = Double.parseDouble(outputValue);
      } catch(NumberFormatException e)
      {
        String format = String.format("§8Error converting value: §c<NOT NUMBER>§8 (output: %s, parsed: %s)", output, outputValue);
        LogUtils.logError(format);
        return false;
      }
    return comparator.compare(doubleInput, doubleOutput);
  }

  /**
   * Método de pré verificação
   */
  private boolean isNumber(String value)
  {
    if(value.matches("^%[^%]+%$"))
      {
        return true;
      }
    try
      {
        Double.parseDouble(value);
        return true;
      } catch(NumberFormatException e)
      {
        return false;
      }
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
