package dev.pretti.prtminetreasures.utils;

import dev.pretti.prtminetreasures.enums.EnumTypesType;

public class TypeUtils
{
  /**
   * Métodos de conversão
   */
  public static EnumTypesType getEnumType(String value)
  {
    if(isInteger(value))
      {
        return EnumTypesType.INT;
      }
    else if(isLong(value))
      {
        return EnumTypesType.LONG;
      }
    else if(isBoolean(value))
      {
        return EnumTypesType.BOOLEAN;
      }
    else if(isDouble(value))
      {
        return EnumTypesType.DOUBLE;
      }
    return EnumTypesType.STRING;
  }

  /**
   * Métodos de verificações
   */
  public static boolean isBoolean(String value)
  {
    if(value == null)
      {
        return false;
      }
    return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
  }

  public static boolean isInteger(String value)
  {
    if(value == null)
      {
        return false;
      }
    try
      {
        Integer.parseInt(value);
        return true;
      } catch(NumberFormatException e)
      {
        return false;
      }
  }

  public static boolean isDouble(String value)
  {
    if(value == null)
      {
        return false;
      }
    try
      {
        Double.parseDouble(value);
        return true;
      } catch(NumberFormatException e)
      {
        return false;
      }
  }

  public static boolean isLong(String value)
  {
    if(value == null)
      {
        return false;
      }
    try
      {
        Long.parseLong(value);
        return true;
      } catch(NumberFormatException e)
      {
        return false;
      }
  }

  /**
   * Métodos de conversão
   */
  public static boolean toBoolean(String value)
  {
    if(value == null)
      {
        return false;
      }
    return value.equalsIgnoreCase("true");
  }

  public static int toInteger(String value)
  {
    if(value == null)
      {
        return 0;
      }
    try
      {
        return Integer.parseInt(value);
      } catch(NumberFormatException e)
      {
        return 0;
      }
  }

  public static double toDouble(String value)
  {
    if(value == null)
      {
        return 0;
      }
    try
      {
        return Double.parseDouble(value);
      } catch(NumberFormatException e)
      {
        return 0;
      }
  }

  public static long toLong(String value)
  {
    if(value == null)
      {
        return 0;
      }
    try
      {
        return Long.parseLong(value);
      } catch(NumberFormatException e)
      {
        return 0;
      }
  }

  public static String toString(Object value)
  {
    if(value == null)
      {
        return "";
      }
    return value.toString();
  }
}
