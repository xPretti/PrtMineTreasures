package dev.pretti.prtminetreasures.integrations;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.integrations.types.VaultApiIntegration;
import org.jetbrains.annotations.Nullable;

public class IntegrationManager
{
  private final PlaceholderApiIntegration placeholderApiIntegration = new PlaceholderApiIntegration();
  private final VaultApiIntegration       vaultApiIntegration       = new VaultApiIntegration();

  /**
   * Métodos de inicialização
   */
  public boolean loader()
  {
    boolean loaded = placeholderApiIntegration.load();
    loaded = vaultApiIntegration.load() && loaded;
    return loaded;
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
  public VaultApiIntegration getVaultApi()
  {
    return vaultApiIntegration;
  }
}
