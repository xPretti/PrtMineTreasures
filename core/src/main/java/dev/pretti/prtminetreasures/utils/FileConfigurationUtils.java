package dev.pretti.prtminetreasures.utils;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.function.BiFunction;

public class FileConfigurationUtils
{
  private static final BiFunction<FileConfiguration, String, Boolean> booleanFunction = MemorySection::getBoolean;
  private static final BiFunction<FileConfiguration, String, Long>    longFunction    = MemorySection::getLong;
  private static final BiFunction<FileConfiguration, String, Integer> integerFunction = MemorySection::getInt;
  private static final BiFunction<FileConfiguration, String, Double> doubleFunction = MemorySection::getDouble;
  private static final BiFunction<FileConfiguration, String, String>  stringFunction  = MemorySection::getString;

  /**
   * Retornos de double
   */
  public static Double getDouble(FileConfiguration tryOne, FileConfiguration tryTwo, String search)
  {
    return getResult(doubleFunction, tryOne, tryTwo, search);
  }

  public static Double getDouble(FileConfiguration configuration, String search)
  {
    return getResult(doubleFunction, configuration, search);
  }

  /**
   * Retornos de int
   */
  public static Integer getInt(FileConfiguration tryOne, FileConfiguration tryTwo, String search)
  {
    return getResult(integerFunction, tryOne, tryTwo, search);
  }

  public static Integer getInt(FileConfiguration configuration, String search)
  {
    return getResult(integerFunction, configuration, search);
  }

  /**
   * Retornos de long
   */
  public static Long getLong(FileConfiguration tryOne, FileConfiguration tryTwo, String search)
  {
    return getResult(longFunction, tryOne, tryTwo, search);
  }

  public static Long getLong(FileConfiguration configuration, String search)
  {
    return getResult(longFunction, configuration, search);
  }

  /**
   * Retornos de boolean
   */
  public static Boolean getBoolean(FileConfiguration tryOne, FileConfiguration tryTwo, String search)
  {
    return getResult(booleanFunction, tryOne, tryTwo, search);
  }

  public static Boolean getBoolean(FileConfiguration configuration, String search)
  {
    return getResult(booleanFunction, configuration, search);
  }


  /**
   * Retornos de string
   */
  public static String getString(FileConfiguration tryOne, FileConfiguration tryTwo, String search)
  {
    return getResult(stringFunction, tryOne, tryTwo, search);
  }

  public static String getString(FileConfiguration configuration, String search)
  {
    return getResult(stringFunction, configuration, search);
  }

  /**
   * Retornos de listas de strings
   */
  public static List<String> getStringList(FileConfiguration tryOne, FileConfiguration tryTwo, String search)
  {
    if(search != null)
      {
        List<String> result = getStringList(tryOne, search);
        if(result == null)
          {
            return getStringList(tryTwo, search);
          }
        return result;
      }
    return null;
  }

  public static List<String> getStringList(FileConfiguration configuration, String search)
  {
    if(search != null && configuration != null)
      {
        if(configuration.contains(search))
          {
            return configuration.getStringList(search);
          }
      }
    return null;
  }


  /**
   * Retornos dos resultados usando Operation
   */
  protected static <R> R getResult(BiFunction<FileConfiguration, String, R> operation, FileConfiguration tryOne, FileConfiguration tryTwo, String search)
  {
    if(search != null)
      {
        R result = getResult(operation, tryOne, search);
        if(result == null)
          {
            return getResult(operation, tryTwo, search);
          }
        return result;
      }
    return null;
  }

  protected static <R> R getResult(BiFunction<FileConfiguration, String, R> operation, FileConfiguration configuration, String search)
  {
    if(search != null && configuration != null)
      {
        return operation.apply(configuration, search);
      }
    return null;
  }
}
