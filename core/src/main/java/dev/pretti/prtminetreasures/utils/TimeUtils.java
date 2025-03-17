package dev.pretti.prtminetreasures.utils;

public class TimeUtils
{
  /**
   * Retorna o tempo atual em segundos
   */
  public static long getSystemTime()
  {
    return System.currentTimeMillis() / 1000;
  }

  public static long getSeconds(long seconds)
  {
    return seconds % 60;
  }

  public static long getMinutes(long seconds)
  {
    return (seconds % 3600) / 60;
  }

  public static long getHours(long seconds)
  {
    return seconds / 3600;
  }
}
