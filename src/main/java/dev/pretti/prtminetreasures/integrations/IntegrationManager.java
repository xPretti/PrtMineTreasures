package dev.pretti.prtminetreasures.integrations;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import org.jetbrains.annotations.Nullable;

public class IntegrationManager
{
  private static IntegrationManager        instance;
  private        PlaceholderApiIntegration placeholderApiIntegration = new PlaceholderApiIntegration();

  /**
   * Construtor da classe
   */
  public IntegrationManager()
  {
    instance = this;
  }

  /**
   * Métodos de inicialização
   */
  public boolean loader()
  {
    return placeholderApiIntegration.loader();
  }


  /**
   * Métodos de retornos
   */
  @Nullable
  public PlaceholderApiIntegration getPlaceholderApi()
  {
    return placeholderApiIntegration;
  }

  @Nullable
  public static IntegrationManager getInstance()
  {
    return instance;
  }
}
