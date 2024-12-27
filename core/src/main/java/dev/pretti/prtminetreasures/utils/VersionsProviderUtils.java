package dev.pretti.prtminetreasures.utils;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.versions.interfaces.IInventoryVersion;

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
  public static IInventoryVersion getInventoryVersion(String version)
  {
    try
      {
        return (IInventoryVersion) Class.forName(getVersionsPackage(version) + ".InventoryVersion").getDeclaredConstructor().newInstance();
      } catch(ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e)
      {
        System.err.println("Erro ao criar instancia de inventario da versão " + version + ": " + e.getMessage());
        return null;
      }
  }
}
