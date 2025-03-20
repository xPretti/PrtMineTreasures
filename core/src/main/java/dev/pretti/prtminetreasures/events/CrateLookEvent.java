package dev.pretti.prtminetreasures.events;


import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.events.base.CrateEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CrateLookEvent extends CrateEvent
{
  private static final HandlerList HANDLERS = new HandlerList();
  private final        Player      player;

  public CrateLookEvent(@NotNull ICrate<?> crate, Player player)
  {
    super(crate);
    this.player = player;
  }

  public Player getPlayer()
  {
    return player;
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
