package dev.pretti.prtminetreasures.nms;

import dev.pretti.prtminetreasures.utils.ReflectionUtils;
import dev.pretti.prtminetreasures.utils.SystemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ActionBarNms
{
  private static Method         a;
  private static Object         typeMessage;
  private static Constructor<?> chatConstructor;

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
        Class<?> typeMessageClass;
        Class<?> icbc = ReflectionUtils.getNMSClass("IChatBaseComponent");
        Class<?> ppoc = ReflectionUtils.getNMSClass("PacketPlayOutChat");

        if(icbc.getDeclaredClasses().length > 0)
          {
            a = icbc.getDeclaredClasses()[0].getMethod("a", String.class);
          } else
          {
            a = ReflectionUtils.getNMSClass("ChatSerializer").getMethod("a", String.class);
          }

        if(SystemUtils.getServerVersionInt() >= 112)
          {
            typeMessageClass = ReflectionUtils.getNMSClass("ChatMessageType");
            typeMessage      = typeMessageClass.getEnumConstants()[2];
          } else
          {
            typeMessageClass = byte.class;
            typeMessage      = (byte) 2;
          }

        chatConstructor = ppoc.getConstructor(icbc, typeMessageClass);
      } catch(Throwable ignored)
      {
      }
  }

  /**
   * Métodos de envio do actionbar
   */
  public static void sendActionBar(Player player, String message)
  {
    try
      {
        Object chatMessage = a.invoke(null, "{\"text\":\"" + message + "\"}");
        Object packet      = chatConstructor.newInstance(chatMessage, typeMessage);
        ReflectionUtils.sendPacket(player, packet);
      } catch(Throwable e)
      {
        e.printStackTrace();
      }
  }

  public static void sendBroadcastActionBar(String message)
  {
    try
      {
        Object chatMessage = a.invoke(null, "{\"text\":\"" + message + "\"}");
        Object packet      = chatConstructor.newInstance(chatMessage, typeMessage);
        for(Player player : Bukkit.getOnlinePlayers())
          {
            ReflectionUtils.sendPacket(player, packet);
          }
      } catch(Throwable e)
      {
        e.printStackTrace();
      }
  }
}