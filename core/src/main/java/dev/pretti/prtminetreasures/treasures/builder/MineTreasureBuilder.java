package dev.pretti.prtminetreasures.treasures.builder;

import dev.pretti.prtminetreasures.configs.interfaces.IOptionsConfig;
import dev.pretti.prtminetreasures.integrations.IntegrationManager;
import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.prtminetreasures.treasures.outputs.CommandOutput;
import dev.pretti.prtminetreasures.treasures.outputs.ItemOutput;
import dev.pretti.prtminetreasures.treasures.outputs.MoneyOutput;
import dev.pretti.prtminetreasures.treasures.outputs.XpOutput;
import dev.pretti.treasuresapi.processors.TreasureProcessor;
import dev.pretti.treasuresapi.processors.interfaces.ITreasureBuilder;
import dev.pretti.treasuresapi.processors.interfaces.ITreasureProcessor;
import dev.pretti.treasuresapi.processors.interfaces.outputs.ICommandOutput;
import dev.pretti.treasuresapi.processors.interfaces.outputs.IItemOutput;
import dev.pretti.treasuresapi.processors.interfaces.outputs.IMoneyOutput;
import dev.pretti.treasuresapi.processors.interfaces.outputs.IXpOutput;
import dev.pretti.treasuresapi.rewards.Treasure;

public class MineTreasureBuilder implements ITreasureBuilder
{
  private final IntegrationManager integrationManager;
  private final PlaceholderManager placeholderManager;
  private final IOptionsConfig     optionsConfig;


  /**
   * Contrutor da classe
   */
  public MineTreasureBuilder(IntegrationManager integrationManager, PlaceholderManager placeholderManager, IOptionsConfig optionsConfig)
  {
    this.integrationManager = integrationManager;
    this.placeholderManager = placeholderManager;
    this.optionsConfig      = optionsConfig;
  }

  /**
   * Retorna o processador
   */
  @Override
  public ITreasureProcessor build(Treasure treasure)
  {
    return new TreasureProcessor(treasure, getXpOutput(), getMoneyOutput(), getCommandOutput(), getItemOutput());
  }

  /**
   * Retorna os outputs
   */
  private IXpOutput getXpOutput()
  {
    return new XpOutput();
  }

  private IMoneyOutput getMoneyOutput()
  {
    return new MoneyOutput(integrationManager.getVaultApi());
  }

  private ICommandOutput getCommandOutput()
  {
    return new CommandOutput(placeholderManager);
  }

  private IItemOutput getItemOutput()
  {
    return new ItemOutput(placeholderManager, optionsConfig.isDropToInventory(), optionsConfig.isDiscardExcess());
  }
}
