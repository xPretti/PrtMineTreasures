package dev.pretti.prtminetreasures.utils;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;

public class VersionsProviderUtils
{
  /**
   * Métodos de retornos de pacotes
   */
  public static String getVersionsPackage()
  {
    String packageName = PrtMineTreasures.class.getPackage().getName();
    return packageName + ".versions";
  }

  public static String getVersionsPackage(String version)
  {
    return getVersionsPackage() + "." + version;
  }


  /**
   * Método de retornos de classes
   */
//  public static IInventoryProvider getInventoryVersion(String version)
//  {
//    try
//      {
//        return (IInventoryProvider) Class.forName(getVersionsPackage(version) + ".InventoryProvider").getDeclaredConstructor().newInstance();
//      } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
//      {
//        Bukkit.getLogger().severe("§cInvalid inventory provider version " + version + ": " + e.getMessage());
//        return null;
//      }
//  }

  public static <T> T getClassVersion(String version, String clazzName, Class<T> clazz)
  {
    try
      {
        Class<?> foundClass = Class.forName(getVersionsPackage(version) + "." + clazzName);
        return clazz.cast(foundClass.getDeclaredConstructor().newInstance());
      } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
      {
        Bukkit.getLogger().severe("Invalid " + clazz.getSimpleName() + " provider version " + version + ": " + e.getMessage());
      }
    return null;
  }


}
