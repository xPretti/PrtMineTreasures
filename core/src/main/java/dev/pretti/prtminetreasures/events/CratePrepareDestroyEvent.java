package dev.pretti.prtminetreasures.events;


import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.events.base.CrateEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CratePrepareDestroyEvent extends CrateEvent
{
  private static final HandlerList HANDLERS = new HandlerList();
  private final Player trigger;

  public CratePrepareDestroyEvent(@NotNull ICrate<?> crate, Player trigger)
  {
    super(crate);
    this.trigger = trigger;
  }

  public Player getTrigger()
  {
    return trigger;
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
