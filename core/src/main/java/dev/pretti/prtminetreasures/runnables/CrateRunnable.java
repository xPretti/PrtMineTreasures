package dev.pretti.prtminetreasures.runnables;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.Crate;
import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import org.bukkit.Bukkit;

public class CrateRunnable implements Runnable
{
  @Override
  public void run()
  {
    for(ICrate<?> crate : Crate.getCrates())
      {
        if(crate == null)
          {
            continue;
          }
        if(crate.isExpired())
          {
            Bukkit.getScheduler().runTaskLater(PrtMineTreasures.getInstance(), () -> crate.destroy(false), 0L);
            continue;
          }
        crate.update();
      }
  }
}
