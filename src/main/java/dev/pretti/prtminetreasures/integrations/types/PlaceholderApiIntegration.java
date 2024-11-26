package dev.pretti.prtminetreasures.integrations.types;

import dev.pretti.prtminetreasures.integrations.Integration;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.List;

public class PlaceholderApiIntegration extends Integration
{
  /**
   * Construtor da classe
   */
  public PlaceholderApiIntegration()
  {
    super("PlaceholderAPI", false);
  }

  /**
   * Métodos da biblioteca
   */
  public String setPlaceholders(Player player, String message)
  {
    if(isLoaded())
      {
        return PlaceholderAPI.setPlaceholders(player, message);
      }
    return message;
  }

  public List<String> setPlaceholders(Player player, List<String> message)
  {
    if(isLoaded())
      {
        return PlaceholderAPI.setPlaceholders(player, message);
      }
    return message;
  }
}
