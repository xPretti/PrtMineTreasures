package dev.pretti.prtminetreasures.crates.transmissions;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.nms.ActionBarNms;
import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CrateTransmission
{
  private final PlaceholderManager placeholderManager;
  // options..
  private       String             testMessage     = "§eMensagem de teste §a@player§e tempo até destruir tesouro §c@crate_left";
  private       String             testMessage2     = "§aTesouro coletado";
  private       String             titleMessage    = "§eTesouro de §a@player";
  private       String             subtitleMessage = "§c@crate_left";

  public CrateTransmission(PrtMineTreasures plugin)
  {
    this.placeholderManager = plugin.getPlaceholderManager();
  }

  /**
   * Possíveis transmissões:
   */
  public void transmitTimer(@NotNull Player player, @NotNull ICrate<?> crate)
  {
    String message  = placeholderManager.replaceCrateAll(testMessage, crate);
    ActionBarNms.sendActionBar(player, message);
  }

  public void transmitCollect(@NotNull Player player, @NotNull ICrate<?> crate)
  {
    String message = placeholderManager.replaceCrateAll(testMessage2, crate);
    ActionBarNms.sendActionBar(player, message);
  }

}
