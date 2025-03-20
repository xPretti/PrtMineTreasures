package dev.pretti.prtminetreasures.integrations.types;

import dev.pretti.prtminetreasures.handlers.HDHologramHandler;
import dev.pretti.prtminetreasures.handlers.interfaces.IHologramHandler;
import dev.pretti.prtminetreasures.integrations.base.Integration;
import dev.pretti.prtminetreasures.integrations.interfaces.IHologramIntegration;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HDApiIntegration extends Integration implements IHologramIntegration
{
  /**
   * Construtor da classe
   */
  public HDApiIntegration()
  {
    super("HolographicDisplays", false);
  }

  /**
   * Métodos da biblioteca
   */
  @Override
  @Nullable
  public IHologramHandler createHologram(@NotNull Location location)
  {
    if(isLoaded())
      {
        IHologramHandler handler = new HDHologramHandler();
        handler.create(location);
        return handler;
      }
    return null;
  }
}
