package dev.pretti.prtminetreasures.utils;

public class ReplaceUtils
{
  public static String toColorMessage(String text)
  {
    if(text != null)
      {
        return (text.replace("&", "§"));
      }
    return null;
  }
}
