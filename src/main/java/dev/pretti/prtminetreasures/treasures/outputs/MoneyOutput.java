package dev.pretti.prtminetreasures.treasures.outputs;

import dev.pretti.prtminetreasures.integrations.types.VaultApiIntegration;
import dev.pretti.treasuresapi.processors.context.TreasureContext;
import dev.pretti.treasuresapi.processors.interfaces.outputs.IMoneyOutput;
import org.jetbrains.annotations.NotNull;

public class MoneyOutput implements IMoneyOutput
{
  private final VaultApiIntegration vaultApiIntegration;

  /**
   * Construtor da classe
   */
  public MoneyOutput(VaultApiIntegration vaultApiIntegration)
  {
    this.vaultApiIntegration = vaultApiIntegration;
  }

  @Override
  public boolean process(@NotNull TreasureContext context, double value)
  {
    vaultApiIntegration.depositOrWithdraw(context.getPlayer(), value);
    return true;
  }
}
