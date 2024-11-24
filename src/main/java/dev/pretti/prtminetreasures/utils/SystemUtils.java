package dev.pretti.prtminetreasures.utils;

import org.bukkit.Bukkit;

public class SystemUtils
{
  private static Integer _cacheVersion;

  /**
   * Métodos de retornos da versão do servidor
   */
  public static String getServerVersion()
  {
    try
      {
        String info = Bukkit.getVersion();
        return info.split("MC: ")[1].split("\\)")[0];
      } catch(Throwable e)
      {
        return null;
      }
  }

  public static int getServerVersionInt()
  {
    if(_cacheVersion == null)
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
                    _cacheVersion = major * 100 + minor;
                    return _cacheVersion;
                  } catch(NumberFormatException ignored)
                  {
                  }
              }
          }
        return -1;
      }
    return _cacheVersion;
  }
}
