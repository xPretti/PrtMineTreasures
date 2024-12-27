package dev.pretti.prtminetreasures.metadatas.conditions.types;

import de.tr7zw.changeme.nbtapi.NBT;
import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.metadatas.conditions.interfaces.ILogicMetaComparator;
import dev.pretti.prtminetreasures.metadatas.conditions.interfaces.IMetadataCondition;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StringMetaComparator implements IMetadataCondition
{
  private final ILogicMetaComparator<String> comparator;

  private final PlaceholderApiIntegration placeholderApiIntegration;

  private final String input;
  private final String output;

  /**
   * Construtor da classe
   */
  public StringMetaComparator(PlaceholderApiIntegration placeholderApiIntegration, String input, String output, ILogicMetaComparator<String> comparator)
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
    String inputValue  = getKey(itemStack, placeholderApiIntegration.setPlaceholders(player, input));
    String outputValue = placeholderApiIntegration.setPlaceholders(player, output);
    return comparator.compare(inputValue, outputValue);
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
  private String getKey(ItemStack itemStack, String key)
  {
    return NBT.readNbt(itemStack).getOrDefault(key, "");
  }
}
