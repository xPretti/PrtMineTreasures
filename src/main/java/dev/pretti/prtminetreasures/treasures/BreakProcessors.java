package dev.pretti.prtminetreasures.treasures;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.prtminetreasures.treasures.builder.MineTreasureBuilder;
import dev.pretti.treasuresapi.TreasuresApi;
import dev.pretti.treasuresapi.processors.TreasuresProcessors;
import dev.pretti.treasuresapi.processors.context.TreasureContext;
import dev.pretti.treasuresapi.processors.interfaces.ITreasureBuilder;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BreakProcessors
{
  private final PlaceholderManager placeholderManager;

  private TreasuresProcessors treasuresProcessors;

  public BreakProcessors(PrtMineTreasures plugin)
  {
    this.placeholderManager = plugin.getPlaceholderManager();
  }

  /**
   * Método de carregamento
   */
  public void load(String folder)
  {
    treasuresProcessors = TreasuresApi.loader(folder, getBuilder());
  }

  /**
   * Método de processamento do evento
   */
  public boolean process(Player player, Location location)
  {
    return treasuresProcessors.processAll(new TreasureContext(player, location));
  }

  /**
   * Retorna o builder
   */
  private ITreasureBuilder getBuilder()
  {
    return new MineTreasureBuilder(placeholderManager);
  }

}
