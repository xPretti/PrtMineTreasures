package dev.pretti.prtminetreasures.utils;

import org.bukkit.Bukkit;

public class SystemUtils
{
  private static final String  SERVER_VERSION_STRING;
  private static final Integer SERVER_VERSION_INT;
  private static final Integer SERVER_VERSION_INT_FULL;

  /**
   * Carregamentos
   */
  static
    {
      SERVER_VERSION_STRING   = toServerVersion();
      SERVER_VERSION_INT      = toServerVersionInt(false);
      SERVER_VERSION_INT_FULL = toServerVersionInt(true);
    }

  private static String toServerVersion()
  {
    try
      {
        String info = Bukkit.getVersion();
        return info.split("MC: ")[1].split("\\)")[0];
      } catch(Throwable e)
      {
        System.err.println("Erro ao obter a versão do servidor: " + e.getMessage());
        return null;
      }
  }

  private static Integer toServerVersionInt(boolean full)
  {
    String versionString = getServerVersion();
    if(versionString != null)
      {
        String[] versionParts = versionString.split("\\.");
        if(versionParts.length >= 2)
          {
            try
              {
                int major = Integer.parseInt(versionParts[0]);
                int minor = Integer.parseInt(versionParts[1]);
                if(full)
                  {
                    int patch = versionParts.length >= 3 ? Integer.parseInt(versionParts[2]) : 0;
                    return major * 10000 + minor * 100 + patch;
                  }
                return major * 100 + minor;
              } catch(NumberFormatException e)
              {
                System.err.println("Erro ao converter versão do servidor: " + e.getMessage());
                return -1;
              }
          }
      }
    return -1;
  }

  /**
   * Retorna a versão NMS
   */
  public static String getNMSVersion()
  {
    String packageName = Bukkit.getServer().getClass().getPackage().getName();
    return packageName.substring(packageName.lastIndexOf('.') + 1);
  }

  /**
   * Métodos de retornos da versão do servidor
   */
  public static boolean isLegacyVersion()
  {
    return getServerVersionInt() < 113;
  }

  public static boolean isNewVersion()
  {
    return getServerVersionInt() >= 113;
  }

  /**
   * Retorna a versão string do servidor
   * 1.8.8
   * 1.9.0 ...
   */
  public static String getServerVersion()
  {
    return SERVER_VERSION_STRING;
  }

  /**
   * Retorna a versão numérica do servidor
   * 1.8 = 108
   * 1.9 = 109
   * 1.10 = 110 ...
   */
  public static int getServerVersionInt()
  {
    return SERVER_VERSION_INT;
  }

  /**
   * Retorna a versão numérica do servidor
   * 1.8.8 = 10808
   * 1.9 = 10900
   * 1.10.4 = 11004 ...
   */
  public static int getServerVersionIntFull()
  {
    return SERVER_VERSION_INT_FULL;
  }
}
