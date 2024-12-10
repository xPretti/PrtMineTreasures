package dev.pretti.prtminetreasures.treasures.outputs;

import dev.pretti.prtminetreasures.nms.ActionBarNms;
import dev.pretti.prtminetreasures.nms.TitleNms;
import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.treasuresapi.datatypes.commands.*;
import dev.pretti.treasuresapi.processors.context.RewardContext;
import dev.pretti.treasuresapi.processors.context.TreasureContext;
import dev.pretti.treasuresapi.processors.interfaces.outputs.ICommandOutput;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandOutput implements ICommandOutput
{
  private final PlaceholderManager placeholderManager;

  /**
   * Construtor da classe
   */
  public CommandOutput(PlaceholderManager placeholderManager)
  {
    this.placeholderManager = placeholderManager;
  }

  /**
   * Método de processamento do comando
   */
  @Override
  public boolean process(@NotNull TreasureContext context, CommandType commandType)
  {
    if(commandType != null)
      {
        if(commandType instanceof RangeTextCommandType)
          {
            return process(context, (RangeTextCommandType) commandType);
          }
        else if(commandType instanceof TitleCommandType)
          {
            return process(context, (TitleCommandType) commandType);
          }
        else if(commandType instanceof TextCommandType)
          {
            return process(context, (TextCommandType) commandType);
          }
        else if(commandType instanceof SoundCommandType)
          {
            return process(context, (SoundCommandType) commandType);
          }
      }
    return false;
  }

  @Override
  public boolean process(@NotNull TreasureContext context, RangeTextCommandType command)
  {
    if(command != null)
      {
        command.setText(getReplaceCommand(context, command.getText()));
        return runRangeMessage(context, command.getRange(), command.getText());
      }
    return false;
  }

  @Override
  public boolean process(@NotNull TreasureContext context, TitleCommandType command)
  {
    if(command != null)
      {
        command.setTitle(getReplaceCommand(context, command.getTitle()));
        command.setSubtitle(getReplaceCommand(context, command.getSubtitle()));
        switch(command.getType())
          {
            case TITLE:
              return runPlayerTitle(context, command.getTitle(), command.getSubtitle(), command.getFadeInTicks(), command.getStayTicks(),
                                    command.getFadeOutTicks());
            case BROADCAST_TITLE:
              return runBroadcastTitle(context, command.getTitle(), command.getSubtitle(), command.getFadeInTicks(), command.getStayTicks(),
                                       command.getFadeOutTicks());
            default:
              return false;
          }
      }
    return false;
  }

  @Override
  public boolean process(@NotNull TreasureContext context, TextCommandType command)
  {
    if(command != null)
      {
        command.setText(getReplaceCommand(context, command.getText()));
        switch(command.getType())
          {
            case CONSOLE:
              return runConsole(context, command.getText());
            case MESSAGE:
              return runMessage(context, command.getText());
            case BROADCAST:
              return runBroadcast(context, command.getText());
            case PLAYER:
              return runPlayer(context, command.getText());
            case ACTIONBAR:
              return runPlayerActionBar(context, command.getText());
            case BROADCAST_ACTIONBAR:
              return runBroadcastActionBar(context, command.getText());
            default:
              return false;
          }
      }
    return false;
  }

  @Override
  public boolean process(@NotNull TreasureContext context, SoundCommandType command)
  {
    if(command != null)
      {
        switch(command.getType())
          {
            case SOUND:
              return runPlayerSound(context, command.getSound(), command.getVolume(), command.getPitch());
            case WORLD_SOUND:
              return runWorldSound(context, command.getSound(), command.getVolume(), command.getPitch());
            case EVENT_SOUND:
              return runPlayerEventSound(context, command.getSound(), command.getVolume(),
                                         command.getPitch());
            case WORLD_EVENT_SOUND:
              return runWorldEventSound(context, command.getSound(), command.getVolume(), command.getPitch());
            default:
              return false;
          }
      }
    return false;
  }

  /**
   * Métodos para serem implementados
   */
  protected String getReplaceCommand(TreasureContext context, String current)
  {
    RewardContext rewardContext = context.getRewardContext();
    return placeholderManager.replaceAll(current, context.getPlayer(),
                                         context.getEventLocation(),
                                         rewardContext.getItemType(),
                                         rewardContext.getXp(),
                                         rewardContext.getMoney());
  }


  /**
   * Ações protegidas de execução
   */
  protected boolean runConsole(TreasureContext context, String command)
  {
    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
    Bukkit.dispatchCommand(console, command);
    return true;
  }

  protected boolean runMessage(TreasureContext context, String command)
  {
    Player player = context.getPlayer();
    player.sendMessage(command);
    return true;
  }

  protected boolean runBroadcast(TreasureContext context, String command)
  {
    Bukkit.broadcastMessage(command);
    return true;
  }

  protected boolean runPlayer(TreasureContext context, String command)
  {
    Player player = context.getPlayer();
    player.chat(command);
    return true;
  }

  protected boolean runRangeMessage(TreasureContext context, double range, String command)
  {
    Player player = context.getPlayer();
    double distance;
    for(Player p : player.getWorld().getPlayers())
      {
        distance = p.getLocation().distance(player.getLocation());
        if(distance <= range)
          {
            p.sendMessage(command);
          }
      }
    return true;
  }

  protected boolean runPlayerSound(TreasureContext context, Sound sound, float volume, float pitch)
  {
    Player   player = context.getPlayer();
    Location loc    = player.getLocation();
    player.playSound(loc, sound, volume, pitch);
    return true;
  }

  protected boolean runWorldSound(TreasureContext context, Sound sound, float volume, float pitch)
  {
    Player   player = context.getPlayer();
    Location loc    = player.getLocation();
    player.getWorld().playSound(loc, sound, volume, pitch);
    return true;
  }

  protected boolean runPlayerEventSound(TreasureContext context, Sound sound,
                                        float volume, float pitch)
  {
    Player   player        = context.getPlayer();
    Location eventLocation = context.getEventLocation();
    player.playSound(eventLocation, sound, volume, pitch);
    return true;
  }

  protected boolean runWorldEventSound(TreasureContext context, Sound sound,
                                       float volume, float pitch)
  {
    Player   player        = context.getPlayer();
    Location eventLocation = context.getEventLocation();
    player.getWorld().playSound(eventLocation, sound, volume, pitch);
    return true;
  }

  protected boolean runPlayerTitle(TreasureContext context, String title, String subtitle,
                                   Integer fadeInTicks, Integer stayTicks,
                                   Integer fadeOutTicks)
  {
    Player player = context.getPlayer();
    TitleNms.sendTitle(player, title, subtitle, fadeInTicks, stayTicks, fadeOutTicks);
    return true;
  }

  protected boolean runBroadcastTitle(TreasureContext context, String title, String subtitle,
                                      Integer fadeInTicks, Integer stayTicks,
                                      Integer fadeOutTicks)
  {
    TitleNms.sendBroadcastTitle(title, subtitle, fadeInTicks, stayTicks, fadeOutTicks);
    return true;
  }

  protected boolean runPlayerActionBar(TreasureContext context, String message)
  {
    Player player = context.getPlayer();
    ActionBarNms.sendActionBar(player, message);
    return true;
  }

  protected boolean runBroadcastActionBar(TreasureContext context, String message)
  {
    ActionBarNms.sendBroadcastActionBar(message);
    return true;
  }
}
