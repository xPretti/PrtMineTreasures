package dev.pretti.prtminetreasures.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtils
{
  private static final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
  private static       Method getHandle;
  private static       Method sendPacket;
  private static       Field  playerConnectionField;

  /**
  * Inicializador
  */
  static
  {
    try
      {
        getHandle             = getOBClass("entity.CraftPlayer").getMethod("getHandle");
        playerConnectionField = getNMSClass("EntityPlayer").getField("playerConnection");
        sendPacket            = getNMSClass("PlayerConnection").getMethod("sendPacket", getNMSClass("Packet"));
      } catch(Throwable ignored)
      {
      }
  }

  /**
  * Métodos de retornos
  */
  public static Class<?> getNMSClass(String name) throws ClassNotFoundException
  {
    return Class.forName("net.minecraft.server." + version + "." + name);
  }

  public static Class<?> getOBClass(String name) throws ClassNotFoundException
  {
    return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
  }

  public static void sendPacket(Player player, Object packet)
  {
    try
      {
        if(SystemUtils.getServerVersionInt() < 117)
          {
            Object entityPlayer     = getHandle.invoke(player);
            Object playerConnection = playerConnectionField.get(entityPlayer);
            sendPacket.invoke(playerConnection, packet);
          } else
          {
            Object   handle           = player.getClass().getMethod("getHandle").invoke(player);
            Object   playerConnection = handle.getClass().getField("b").get(handle);
            Class<?> packetClass      = Class.forName("net.minecraft.network.protocol.Packet");
            playerConnection.getClass().getMethod("sendPacket", packetClass).invoke(playerConnection, packet);
          }

      } catch(Throwable e)
      {
        e.printStackTrace();
      }
  }
}