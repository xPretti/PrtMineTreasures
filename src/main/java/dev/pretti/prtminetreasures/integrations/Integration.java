package dev.pretti.prtminetreasures.integrations;

import dev.pretti.prtminetreasures.utils.LogUtils;
import org.bukkit.Bukkit;

public class Integration
{
  private final String  _libraryName;
  private final boolean _isRequired;
  private       boolean _isLoaded;

  /**
   * Construtor da classe
   */
  public Integration(String libraryName, boolean required)
  {
    _libraryName = libraryName;
    _isRequired = required;
  }

  /**
   * Métodos de inicialização
   */
  public boolean loader()
  {
    if(_libraryName != null)
      {
        if(!_libraryName.isEmpty())
          {
            this._isLoaded = Bukkit.getPluginManager().getPlugin(getLibraryName()) != null;
            checkHook();
            return isRequired() ? _isLoaded : true;
          }
      }
    this._isLoaded = false;
    checkHook();
    return !isRequired();
  }

  /**
   * Métodos de retornos
   */
  public String getLibraryName()
  {
    return this._libraryName;
  }
  public boolean isRequired()
  {
    return this._isRequired;
  }

  public boolean isLoaded()
  {
    return this._isLoaded;
  }

  /**
   * Mensagens padrões
   */
  private void checkHook()
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
    LogUtils.logWarn(String.format("%s unsuccessful integration. §b(Optional)", getLibraryName()));
  }
  private void hookError()
  {
    LogUtils.logError(String.format("%s unsuccessful integration. §c(Required)", getLibraryName()));
  }
}
