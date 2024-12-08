package dev.pretti.prtminetreasures.placeholders.types;


import dev.pretti.prtminetreasures.placeholders.types.base.Placeholder;

public class XpRewardPlaceholder extends Placeholder<Integer>
{
  /**
   * Construtor da classe
   */
  public XpRewardPlaceholder()
  {
    super("@exp");
    getPlaceholders().put("@exp", struct -> String.format("%d", struct));
  }
}