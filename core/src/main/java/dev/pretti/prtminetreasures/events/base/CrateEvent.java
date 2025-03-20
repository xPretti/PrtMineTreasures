package dev.pretti.prtminetreasures.events.base;

import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class CrateEvent extends Event
{
  private final        ICrate<?>   crate;

  public CrateEvent(@NotNull ICrate<?> crate)
  {
    this.crate = crate;
  }

  /**
   * Métodos de retornos
   */
  @NotNull
  public ICrate<?> getCrate()
  {
    return crate;
  }
}
