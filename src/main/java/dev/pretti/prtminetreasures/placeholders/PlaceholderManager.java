package dev.pretti.prtminetreasures.placeholders;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.placeholders.base.Placeholders;
import dev.pretti.prtminetreasures.placeholders.types.PlayerPlaceholder;
import dev.pretti.prtminetreasures.structs.PlayerStruct;
import org.bukkit.entity.Player;

import java.util.List;

public class PlaceholderManager
{
  private final PlaceholderApiIntegration placeholderApi;

  private final Placeholders<PlayerStruct> playerPlaceholders = new Placeholders<>(new PlayerPlaceholder());

  /**
   * Construtor da classe
   */
  public PlaceholderManager(PlaceholderApiIntegration placeholderApi)
  {
    this.placeholderApi = placeholderApi;
  }

  /**
   * Métodos de replace geral
   */
  public String replaceAll(String text, Player player)
  {
    if(text == null)
      {
        return null;
      }
    text = placeholderApi.setPlaceholders(player, text);
    text = playerPlaceholders.replace(new PlayerStruct(player), text);
    return text;
  }

  public void replaceAll(List<String> texts, Player player)
  {
    if(texts == null || texts.isEmpty())
      {
        return;
      }
    texts = placeholderApi.setPlaceholders(player, texts);
    playerPlaceholders.replace(new PlayerStruct(player), texts);
  }

  /**
   * Retornos individuais
   */
  public Placeholders<PlayerStruct> getPlayerPlaceholders()
  {
    return (playerPlaceholders);
  }
}
