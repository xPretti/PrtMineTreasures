package dev.pretti.prtminetreasures.placeholders.types;


import dev.pretti.prtminetreasures.placeholders.types.base.Placeholder;

public class MoneyRewardPlaceholder extends Placeholder<Double>
{
  /**
   * Construtor da classe
   */
  public MoneyRewardPlaceholder()
  {
    super("@money");
    getPlaceholders().put("@money", struct -> String.format("%.2f", struct));
  }
}