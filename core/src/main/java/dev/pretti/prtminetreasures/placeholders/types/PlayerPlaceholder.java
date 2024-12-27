package dev.pretti.prtminetreasures.placeholders.types;


import dev.pretti.prtminetreasures.placeholders.types.base.Placeholder;
import org.bukkit.entity.Player;

public class PlayerPlaceholder extends Placeholder<Player>
{
  /**
   * Construtor da classe
   */
  public PlayerPlaceholder()
  {
    super("@player");
    getPlaceholders().put("@player_x", struct -> String.format("%.2f", struct.getLocation().getX()));
    getPlaceholders().put("@player_y", struct -> String.format("%.2f", struct.getLocation().getY()));
    getPlaceholders().put("@player_z", struct -> String.format("%.2f", struct.getLocation().getZ()));
    getPlaceholders().put("@player_w", struct -> struct.getWorld().getName());
    getPlaceholders().put("@player", Player::getDisplayName);
  }
}