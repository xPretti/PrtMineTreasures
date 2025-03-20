package dev.pretti.prtminetreasures.runnables;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.crate.Crate;
import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.crates.interfaces.ICrateManager;
import org.bukkit.Bukkit;

public class CrateRunnable implements Runnable
{
  private final ICrateManager crateManager;

  public CrateRunnable(ICrateManager crateManager)
  {
    this.crateManager = crateManager;
  }


  @Override
  public void run()
  {
    for(ICrate<?> crate : Crate.getCrates())
      {
        if(crate == null)
          {
            continue;
          }
        crateManager.onUpdate(crate);
        if(crate.isExpired())
          {
            Bukkit.getScheduler().runTaskLater(PrtMineTreasures.getInstance(), () -> crate.destroy(false), 0L);
            continue;
          }
        crate.update();
      }
  }
}
