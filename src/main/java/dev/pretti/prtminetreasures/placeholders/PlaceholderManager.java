package dev.pretti.prtminetreasures.placeholders;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.placeholders.base.Placeholders;
import dev.pretti.prtminetreasures.placeholders.types.*;
import dev.pretti.treasuresapi.datatypes.ItemType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlaceholderManager
{
  private final PlaceholderApiIntegration placeholderApi;

  private final Placeholders<Player>   playerPlaceholders     = new Placeholders<>(new PlayerPlaceholder());
  private final Placeholders<ItemType> itemRewardPlaceholders = new Placeholders<>(new ItemRewardPlaceholder());
  private final Placeholders<Integer>  xpRewardPlaceholders   = new Placeholders<>(new XpRewardPlaceholder());
  private final Placeholders<Double>   moneyPlaceholders      = new Placeholders<>(new MoneyRewardPlaceholder());
  private final Placeholders<Location> eventPlaceholders      = new Placeholders<>(new EventPlaceholder());

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
  public String replaceAll(String text, Player player, @Nullable Location eventLocation, @Nullable ItemType itemType, int xp, double money)
  {
    if(text == null)
      {
        return null;
      }
    text = placeholderApi.setPlaceholders(player, text);
    text = playerPlaceholders.replace(player, text);
    if(eventLocation != null)
      {
        text = eventPlaceholders.replace(eventLocation, text);
      }
    if(itemType != null)
      {
        text = itemRewardPlaceholders.replace(itemType, text);
      }
    text = xpRewardPlaceholders.replace(xp, text);
    text = moneyPlaceholders.replace(money, text);
    return text;
  }

  public List<String> replaceAll(List<String> texts, Player player, @Nullable Location eventLocation, @Nullable ItemType itemType, int xp, double money)
  {
    if(texts == null || texts.isEmpty())
      {
        return texts;
      }
    texts = placeholderApi.setPlaceholders(player, texts);
    playerPlaceholders.replace(player, texts);
    if(eventLocation != null)
      {
        eventPlaceholders.replace(eventLocation, texts);
      }
    if(itemType != null)
      {
        itemRewardPlaceholders.replace(itemType, texts);
      }
    xpRewardPlaceholders.replace(xp, texts);
    moneyPlaceholders.replace(money, texts);
    return texts;
  }

  /**
   * Retornos
   */
  public PlaceholderApiIntegration getPlaceholderApi()
  {
    return placeholderApi;
  }
}
