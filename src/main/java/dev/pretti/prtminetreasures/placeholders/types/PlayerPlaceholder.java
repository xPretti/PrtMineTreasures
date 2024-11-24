package dev.pretti.prtminetreasures.placeholders.types;


import dev.pretti.prtminetreasures.placeholders.types.base.Placeholder;
import dev.pretti.prtminetreasures.structs.PlayerStruct;

public class PlayerPlaceholder extends Placeholder<PlayerStruct>
{
  /**
   * Construtor da classe
   */
  public PlayerPlaceholder()
  {
    super("@player");
    getPlaceholders().put("@player_x", struct -> String.format("%.2f", struct.X));
    getPlaceholders().put("@player_y", struct -> String.format("%.2f", struct.Y));
    getPlaceholders().put("@player_z", struct -> String.format("%.2f", struct.Z));
    getPlaceholders().put("@player_w", struct -> struct.world);
    getPlaceholders().put("@player", struct -> struct.displayName);
  }
}
