package dev.pretti.prtminetreasures.json;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class JSONMessage
{
  private String          message;
  private BaseComponent[] hoverMessages;
  private String          suggestCommand;
  private String          executeCommand;

  /**
   * Contrutor da classe
   */
  public JSONMessage()
  {
    this("", null, null, null);
  }

  public JSONMessage(String message)
  {
    this(message, null, null, null);
  }

  public JSONMessage(@NotNull String message, @Nullable BaseComponent[] hoverMessages, @Nullable String suggestCommand, @Nullable String executeCommand)
  {
    this.message        = message;
    this.hoverMessages  = hoverMessages;
    this.suggestCommand = suggestCommand;
    this.executeCommand = executeCommand;
  }

  /**
   * Condifugurador
   */
  public JSONMessage setMessage(@NotNull String message)
  {
    this.message = message;
    return this;
  }

  public JSONMessage setHover(@Nullable List<String> list)
  {
    if(list == null || list.isEmpty())
      {
        hoverMessages = null;
        return this;
      }
    hoverMessages = new BaseComponent[list.size()];
    int count = 0;
    for(String text : list)
      {
        hoverMessages[count] = new TextComponent(count == list.size() - 1 ? text : text + "\n");
        count++;
      }
    return this;
  }

  public JSONMessage setSuggestCommand(@Nullable String command)
  {
    this.suggestCommand = command;
    return this;
  }

  public JSONMessage setExecuteCommand(@Nullable String command)
  {
    this.executeCommand = command;
    return this;
  }

  /**
   * Método de retornos
   */
  @NotNull
  public TextComponent getTextComponent()
  {
    TextComponent textComponent = new TextComponent(this.message);
    if(hoverMessages != null)
      {
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessages));
      }
    if(suggestCommand != null)
      {
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggestCommand));
      }
    if(executeCommand != null)
      {
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, executeCommand));
      }
    return textComponent;
  }

  /**
   * Método de envio
   */
  public void sendMessage(@NotNull Player player)
  {
    player.spigot().sendMessage(getTextComponent());
  }

  /**
   * Método de envio estáticos
   */
  public static void sendMessage(@NotNull Player player, @NotNull JSONMessage... providers)
  {
    BaseComponent[] components = Stream.of(providers).map(JSONMessage::getTextComponent).toArray(BaseComponent[]::new);
    player.spigot().sendMessage(components);
  }

  public static void sendMessage(@NotNull Player player, @NotNull TextComponent... components)
  {
    player.spigot().sendMessage(components);
  }
}
