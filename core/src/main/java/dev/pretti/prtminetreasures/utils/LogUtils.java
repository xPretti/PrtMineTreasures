package dev.pretti.prtminetreasures.utils;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.enums.EnumLoggerType;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class LogUtils
{
  private static final ConsoleCommandSender _console;
  private static final String _prefix;
  private static final String _logFormatNormal;
  private static final String _logFormatError;
  private static final String _logFormatWarn;
  private static final String _logFormatSuccess;

  /**
   * Construtor estático
   */
  static
    {
      _console          = Bukkit.getConsoleSender();
      _prefix           = PrtMineTreasures.class.getSimpleName();
      _logFormatNormal  = "§7[%s] - §8%s§r";
      _logFormatError   = "§7[%s] §7- §c<Error> §8%s§r";
      _logFormatWarn    = "§7[%s] §7- §e<Warn> §8%s§r";
      _logFormatSuccess = "§7[%s] §7- §a<Success> §8%s§r";
    }

  /**
   * Métodos de envio de logs
   */
  public static void log(String message)
  {
    _console.sendMessage(ReplaceUtils.toColorMessage(message));
  }

  public static void log(EnumLoggerType type, String message)
  {
    switch(type)
      {
        case ERROR:
          logError(message);
          break;
        case SUCCESS:
          logSuccess(message);
          break;
        case WARN:
          logWarn(message);
          break;
        default:
          logNormal(message);
          break;
      }
  }

  public static void logNormal(String message)
  {
    message = String.format(_logFormatNormal, _prefix, message);
    log(message);
  }

  public static void logWarn(String message)
  {
    message = String.format(_logFormatWarn, _prefix, message);
    log(message);
  }

  public static void logError(String message)
  {
    message = String.format(_logFormatError, _prefix, message);
    log(message);
  }

  public static void logSuccess(String message)
  {
    message = String.format(_logFormatSuccess, _prefix, message);
    log(message);
  }
}
