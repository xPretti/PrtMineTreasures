package dev.pretti.prtminetreasures.crates.transmissions;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.configs.interfaces.ICrateConfig;
import dev.pretti.prtminetreasures.configs.interfaces.IMessagesConfig;
import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.nms.ActionBarNms;
import dev.pretti.prtminetreasures.nms.TitleNms;
import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CrateTransmission
{
  private final PlaceholderManager placeholderManager;
  // options..
  private final IMessagesConfig    messagesConfig;
  private final ICrateConfig       crateConfig;

  public CrateTransmission(PrtMineTreasures plugin)
  {
    this.placeholderManager = plugin.getPlaceholderManager();
    this.messagesConfig     = plugin.getConfigManager().getMessagesConfig();
    this.crateConfig        = plugin.getConfigManager().getCratesConfig();
  }

  /**
   * Possíveis transmissões:
   */
  public void transmitSpawn(@NotNull Player player, @NotNull ICrate<?> crate)
  {
    if(crateConfig.isSpawnActionBarMessage())
      {
        sendActionBar(player, messagesConfig.getCrateSpawnActionBarMessage(), crate);
      }
    if(crateConfig.isSpawnTitleMessage())
      {
        sendTitle(player, messagesConfig.getCrateSpawnTitleMessage(), crate);
      }
    if(crateConfig.isSpawnChatMessage())
      {
        sendChat(player, messagesConfig.getCrateSpawnChatMessage(), crate);
      }
  }

  public void transmitCollect(@NotNull Player player, @NotNull ICrate<?> crate)
  {
    if(crateConfig.isCollectActionBarMessage())
      {
        sendActionBar(player, messagesConfig.getCrateCollectActionBarMessage(), crate);
      }
    if(crateConfig.isCollectTitleMessage())
      {
        sendTitle(player, messagesConfig.getCrateCollectTitleMessage(), crate);
      }
    if(crateConfig.isCollectChatMessage())
      {
        sendChat(player, messagesConfig.getCrateCollectChatMessage(), crate);
      }
  }

  public void transmitTimer(@NotNull Player player, @NotNull ICrate<?> crate)
  {
    if(crateConfig.isTimerActionBarMessage())
      {
        sendActionBar(player, messagesConfig.getCrateTimerActionBarMessage(), crate);
      }
  }


  /**
   * Métodos de envio
   */
  void sendActionBar(Player player, String message, ICrate<?> crate)
  {
    if(player == null || (message == null || message.isEmpty()))
      {
        return;
      }
    message = placeholderManager.replaceCrateAll(message, crate);
    ActionBarNms.sendActionBar(player, message);
  }

  void sendTitle(Player player, List<String> title, ICrate<?> crate)
  {
    if(player == null || (title == null || title.isEmpty()))
      {
        return;
      }
    String t = placeholderManager.replaceCrateAll(title.get(0), crate);
    String s = "";
    if(title.size() > 1)
      {
        s = placeholderManager.replaceCrateAll(title.get(1), crate);
      }
    TitleNms.sendTitle(player, t, s, crateConfig.getTitleFadeIn(), crateConfig.getTitleStay(), crateConfig.getTitleFadeOut());
  }

  void sendChat(Player player, List<String> message, ICrate<?> crate)
  {
    if(player == null || (message == null || message.isEmpty()))
      {
        return;
      }
    List<String> msgs = placeholderManager.replaceCrateAll(message, crate);
    msgs.forEach(player::sendMessage);
  }
}
