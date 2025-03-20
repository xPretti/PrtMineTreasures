package dev.pretti.prtminetreasures.listeners;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.CrateManager;
import dev.pretti.prtminetreasures.events.CrateCreateEvent;
import dev.pretti.prtminetreasures.events.CrateDestroyEvent;
import dev.pretti.prtminetreasures.events.CrateLookEvent;
import dev.pretti.prtminetreasures.events.CratePrepareDestroyEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CratesListener implements Listener
{
  private final CrateManager crateManager;

  public CratesListener(PrtMineTreasures plugin)
  {
    this.crateManager = plugin.getCrateManager();
  }

  @EventHandler
  public void onCrateCreate(CrateCreateEvent event)
  {
    crateManager.onCrateCreate(event.getCrate());
  }

  @EventHandler
  public void onCratePrepareDestroy(CratePrepareDestroyEvent event)
  {
    crateManager.onCratePrepareDestroy(event.getCrate(), event.getTrigger());
  }

  @EventHandler
  public void onCrateDestroy(CrateDestroyEvent event)
  {
    crateManager.onCrateDestroy(event.getCrate());
  }

  @EventHandler
  public void onCrateLook(CrateLookEvent event)
  {
    crateManager.onCrateLook(event.getCrate(), event.getPlayer());
  }
}
