package dev.pretti.prtminetreasures.placeholders;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.placeholders.base.Placeholders;
import dev.pretti.prtminetreasures.placeholders.types.ItemRewardPlaceholder;
import dev.pretti.prtminetreasures.placeholders.types.PlayerPlaceholder;
import dev.pretti.prtminetreasures.placeholders.types.XpRewardPlaceholder;
import dev.pretti.prtminetreasures.structs.PlayerStruct;
import dev.pretti.treasuresapi.datatypes.ItemType;
import org.bukkit.entity.Player;

import java.util.List;

public class PlaceholderManager
{
  private final PlaceholderApiIntegration placeholderApi;

  private final Placeholders<PlayerStruct> playerPlaceholders     = new Placeholders<>(new PlayerPlaceholder());
  private final Placeholders<ItemType>     itemRewardPlaceholders = new Placeholders<>(new ItemRewardPlaceholder());
  private final Placeholders<Integer>      xpRewardPlaceholders   = new Placeholders<>(new XpRewardPlaceholder());

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
  public String replaceAll(String text, Player player, ItemType itemType, Integer xp)
  {
    if(text == null)
      {
        return null;
      }
    text = placeholderApi.setPlaceholders(player, text);
    text = playerPlaceholders.replace(new PlayerStruct(player), text);
    if(itemType != null)
      {
        text = itemRewardPlaceholders.replace(itemType, text);
      }
    if(xp != null)
      {
        text = xpRewardPlaceholders.replace(xp, text);
      }
    return text;
  }

  public List<String> replaceAll(List<String> texts, Player player, ItemType itemType, Integer xp)
  {
    if(texts == null || texts.isEmpty())
      {
        return texts;
      }
    texts = placeholderApi.setPlaceholders(player, texts);
    playerPlaceholders.replace(new PlayerStruct(player), texts);
    if(itemType != null)
      {
        itemRewardPlaceholders.replace(itemType, texts);
      }
    if(xp != null)
      {
        xpRewardPlaceholders.replace(xp, texts);
      }
    return texts;
  }

  /**
   * Retornos individuais
   */
  public Placeholders<PlayerStruct> getPlayerPlaceholders()
  {
    return (playerPlaceholders);
  }
}
