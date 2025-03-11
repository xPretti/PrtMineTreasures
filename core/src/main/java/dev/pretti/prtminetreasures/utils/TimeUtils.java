package dev.pretti.prtminetreasures.utils;

public class TimeUtils
{
  /**
  * Retorna o tempo atual em segundos
  */
  public static long getCurrentTime()
  {
    return System.currentTimeMillis() / 1000;
  }
}
