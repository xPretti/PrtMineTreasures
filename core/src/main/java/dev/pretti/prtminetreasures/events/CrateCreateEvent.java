package dev.pretti.prtminetreasures.events;


import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.events.base.CrateEvent;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CrateCreateEvent extends CrateEvent
{
  private static final HandlerList HANDLERS = new HandlerList();

  public CrateCreateEvent(@NotNull ICrate<?> crate)
  {
    super(crate);
  }


  public static HandlerList getHandlerList()
  {
    return HANDLERS;
  }
  @Override
  public @NotNull HandlerList getHandlers()
  {
    return HANDLERS;
  }
}
