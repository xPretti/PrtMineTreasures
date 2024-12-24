package dev.pretti.prtminetreasures.metadatas.conditions.types;

import de.tr7zw.changeme.nbtapi.NBT;
import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.metadatas.conditions.interfaces.ILogicMetaComparator;
import dev.pretti.prtminetreasures.metadatas.conditions.interfaces.IMetadataCondition;
import dev.pretti.prtminetreasures.utils.LogUtils;
import dev.pretti.prtminetreasures.utils.TypeUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NumberMetaComparator implements IMetadataCondition
{
  private final ILogicMetaComparator<Double> comparator;

  private final PlaceholderApiIntegration placeholderApiIntegration;

  private final String input;
  private final String output;

  /**
   * Construtor da classe
   */
  public NumberMetaComparator(PlaceholderApiIntegration placeholderApiIntegration, String input, String output, ILogicMetaComparator<Double> comparator)
  {
    this.comparator                = comparator;
    this.placeholderApiIntegration = placeholderApiIntegration;
    this.input                     = input;
    this.output                    = output;
  }

  /**
   * Método de verificação
   */
  @Override
  public boolean evaluate(@NotNull Player player, @NotNull ItemStack itemStack)
  {
    String inputValue  = placeholderApiIntegration.setPlaceholders(player, input);
    String outputValue = placeholderApiIntegration.setPlaceholders(player, output);
    double doubleInput = getKey(itemStack, inputValue);
    double doubleOutput;
    try
      {
        doubleOutput = TypeUtils.isBoolean(outputValue) ? TypeUtils.toBoolean(outputValue) ? 1 : 0 : Double.parseDouble(outputValue);
      } catch(NumberFormatException e)
      {
        String format = String.format("§8Error converting value: §c<NOT NUMBER>§8 (value: %s, parsed: %s)", output, outputValue);
        LogUtils.logError(format);
        return false;
      }
    return comparator.compare(doubleInput, doubleOutput);
  }

  /**
   * Retornos
   */
  public @NotNull String getInput()
  {
    return input;
  }

  public @NotNull String getOutput()
  {
    return output;
  }

  /**
   * Método de retorno das keys
   */
  private double getKey(ItemStack itemStack, String key)
  {
    return NBT.readNbt(itemStack).getOrDefault(key, 0);
  }
}
