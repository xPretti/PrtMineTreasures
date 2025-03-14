package dev.pretti.prtminetreasures.integrations.base;

import dev.pretti.prtminetreasures.utils.LogUtils;
import org.bukkit.Bukkit;

public class Integration
{
  private final String  libraryName;
  private final boolean isRequired;
  private       boolean isLoaded;

  /**
   * Construtor da classe
   */
  public Integration(String libraryName, boolean required)
  {
    this.libraryName = libraryName;
    isRequired       = required;
  }

  /**
   * Métodos de inicialização
   */
  public boolean load()
  {
    boolean hook = tryLoad();
    sendHookMessage();
    return hook;
  }

  public void unload()
  {
    this.isLoaded = false;
  }

  /**
   * Carrega a biblioteca
   */
  protected boolean tryLoad()
  {
    if(libraryName != null && !libraryName.isEmpty())
      {
        this.isLoaded = Bukkit.getPluginManager().getPlugin(getLibraryName()) != null;
        return !isRequired() || isLoaded;
      }
    this.isLoaded = false;
    return !isRequired();
  }

  /**
   * Métodos de retornos
   */
  public String getLibraryName()
  {
    return this.libraryName;
  }

  public boolean isRequired()
  {
    return this.isRequired;
  }

  public boolean isLoaded()
  {
    return this.isLoaded;
  }

  /**
   * Mensagens padrões
   */
  protected void sendHookMessage()
  {
    if(isLoaded())
      {
        hookSuccess();
      }
    else if(isRequired())
      {
        hookError();
      }
    else
      {
        hookWarn();
      }
  }

  private void hookSuccess()
  {
    LogUtils.logSuccess(String.format("%s successful integration.", getLibraryName()));
  }

  private void hookWarn()
  {
    LogUtils.logWarn(String.format("%s unsuccessful integration. §e(Optional)", getLibraryName()));
  }

  private void hookError()
  {
    LogUtils.logError(String.format("%s unsuccessful integration. §c(Required)", getLibraryName()));
  }
}
