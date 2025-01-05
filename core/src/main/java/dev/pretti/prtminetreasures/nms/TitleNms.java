package dev.pretti.prtminetreasures.nms;

import dev.pretti.prtminetreasures.utils.ReflectionUtils;
import dev.pretti.prtminetreasures.utils.SystemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TitleNms
{
  private static Method         a;
  private static Object         enumTIMES;
  private static Object         enumTITLE;
  private static Object         enumSUBTITLE;
  private static Constructor<?> timeTitleConstructor;
  private static Constructor<?> textTitleConstructor;

  /**
   * Inicializador
   */
  static
    {
      load();
    }

  /**
   * Métodos de carregamentos
   */
  private static void load()
  {
    try
      {
        if(SystemUtils.getServerVersionInt() < 117)
          {
            Class<?> icbc = ReflectionUtils.getNMSClass("IChatBaseComponent");
            Class<?> ppot = ReflectionUtils.getNMSClass("PacketPlayOutTitle");
            Class<?> enumClass;

            if(ppot.getDeclaredClasses().length > 0)
              {
                enumClass = ppot.getDeclaredClasses()[0];
              }
            else
              {
                enumClass = ReflectionUtils.getNMSClass("EnumTitleAction");
              }

            if(icbc.getDeclaredClasses().length > 0)
              {
                a = icbc.getDeclaredClasses()[0].getMethod("a", String.class);
              }
            else
              {
                a = ReflectionUtils.getNMSClass("ChatSerializer").getMethod("a", String.class);
              }

            enumTIMES            = enumClass.getField("TIMES").get(null);
            enumTITLE            = enumClass.getField("TITLE").get(null);
            enumSUBTITLE         = enumClass.getField("SUBTITLE").get(null);
            timeTitleConstructor = ppot.getConstructor(enumClass, icbc, int.class, int.class, int.class);
            textTitleConstructor = ppot.getConstructor(enumClass, icbc);
          }
      } catch(Throwable e)
      {
      }
  }

  /**
   * Métodos de envio dos títulos
   */
  public static void sendTitle(Player player, String title, String subtitle, Integer fadeInTicks, Integer stayTicks,
                               Integer fadeOutTicks)
  {
    if(SystemUtils.getServerVersionInt() >= 116)
      {
        player.sendTitle(title.isEmpty() ? " " : title, subtitle.isEmpty() ? " " : subtitle, fadeInTicks, stayTicks, fadeOutTicks);
        return;
      }
    try
      {
        Object chatTitle    = a.invoke(null, "{\"text\":\"" + title + "\"}");
        Object chatSubtitle = a.invoke(null, "{\"text\":\"" + subtitle + "\"}");

        Object timeTitlePacket = timeTitleConstructor.newInstance(enumTIMES, null, fadeInTicks, stayTicks, fadeOutTicks);
        Object titlePacket     = textTitleConstructor.newInstance(enumTITLE, chatTitle);
        Object subtitlePacket  = textTitleConstructor.newInstance(enumSUBTITLE, chatSubtitle);

        ReflectionUtils.sendPacket(player, timeTitlePacket);
        ReflectionUtils.sendPacket(player, titlePacket);
        ReflectionUtils.sendPacket(player, subtitlePacket);

      } catch(Throwable e)
      {
        Bukkit.getLogger().severe("Erro ao enviar o title: " + e.getMessage());
      }
  }

  public static void sendBroadcastTitle(String title, String subtitle, Integer fadeInTicks, Integer stayTicks,
                                        Integer fadeOutTicks)
  {
    if(SystemUtils.getServerVersionInt() >= 116)
      {
        for(Player player : Bukkit.getOnlinePlayers())
          {
            player.sendTitle(title.isEmpty() ? " " : title, subtitle.isEmpty() ? " " : subtitle, fadeInTicks, stayTicks, fadeOutTicks);
          }
        return;
      }
    try
      {
        Object chatTitle    = a.invoke(null, "{\"text\":\"" + title + "\"}");
        Object chatSubtitle = a.invoke(null, "{\"text\":\"" + subtitle + "\"}");

        Object timeTitlePacket = timeTitleConstructor.newInstance(enumTIMES, null, fadeInTicks, stayTicks, fadeOutTicks);
        Object titlePacket     = textTitleConstructor.newInstance(enumTITLE, chatTitle);
        Object subtitlePacket  = textTitleConstructor.newInstance(enumSUBTITLE, chatSubtitle);

        for(Player player : Bukkit.getOnlinePlayers())
          {
            ReflectionUtils.sendPacket(player, timeTitlePacket);
            ReflectionUtils.sendPacket(player, titlePacket);
            ReflectionUtils.sendPacket(player, subtitlePacket);
          }

      } catch(Throwable e)
      {
        Bukkit.getLogger().severe("Erro ao enviar o title: " + e.getMessage());
      }
  }

}
