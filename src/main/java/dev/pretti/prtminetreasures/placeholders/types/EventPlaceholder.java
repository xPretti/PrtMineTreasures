package dev.pretti.prtminetreasures.placeholders.types;


import dev.pretti.prtminetreasures.placeholders.types.base.Placeholder;
import org.bukkit.Location;

public class EventPlaceholder extends Placeholder<Location>
{
  /**
   * Construtor da classe
   */
  public EventPlaceholder()
  {
    super("@event");
    getPlaceholders().put("@event_x", struct -> String.format("%.2f", struct.getX()));
    getPlaceholders().put("@event_y", struct -> String.format("%.2f", struct.getY()));
    getPlaceholders().put("@event_z", struct -> String.format("%.2f", struct.getZ()));
    getPlaceholders().put("@event_w", struct -> struct.getWorld().getName());
  }
}