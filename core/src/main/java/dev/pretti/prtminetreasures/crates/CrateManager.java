package dev.pretti.prtminetreasures.crates;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.crate.Crate;
import dev.pretti.prtminetreasures.crates.crate.Crates;
import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.crates.interfaces.ICrateManager;
import dev.pretti.prtminetreasures.crates.transmissions.CrateTransmission;
import dev.pretti.prtminetreasures.runnables.CrateRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CrateManager implements ICrateManager
{
  private final PrtMineTreasures plugin;
  private final Crates           crates;

  private final CrateTransmission crateTransmission;
  private       int               taskId = -1;

  public CrateManager(PrtMineTreasures plugin)
  {
    this.plugin            = plugin;
    this.crates            = new Crates(plugin);
    this.crateTransmission = new CrateTransmission(plugin);
  }

  /**
   * Métodos de inicialização e finalização
   */
  @Override
  public void init()
  {
    crates.init();
  }

  @Override
  public void deinit()
  {
    stopScheduler();
    crates.deinit();
  }

  /**
   * Actions
   */
  @Override
  public void onUpdate(@NotNull ICrate<?> crate)
  {
    Crate.LOOK_CRATES.forEach(crateTransmission::transmitTimer);
  }

  public void onCrateCreate(@NotNull ICrate<?> crate)
  {
    if(Crate.Total() > 0)
      {
        startScheduler();
      }
    crateTransmission.transmitSpawn(crate.getOwner(), crate);
  }

  public void onCratePrepareDestroy(@NotNull ICrate<?> crate, Player trigger)
  {
    crateTransmission.transmitCollect(trigger, crate);
    Crate.LOOK_CRATES.forEach((player, crt) ->
                                {
                                  Crate.unlookCrate(player);
                                  crateTransmission.transmitCollect(player, crt);
                                });
  }

  public void onCrateDestroy(@NotNull ICrate<?> crate)
  {
    if(Crate.Total() <= 0)
      {
        stopScheduler();
      }
  }

  public void onCrateLook(ICrate<?> crate, Player player)
  {
    //crateTransmission.transmitTimer(player, crate);
  }

  /**
   * Retornos
   */
  public Crates getCrates()
  {
    return crates;
  }

  /**
   * Gerenciamento de schedulers
   */
  private void startScheduler()
  {
    if(taskId != -1)
      {
        return;
      }
    taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new CrateRunnable(this), 20L, 20L).getTaskId();
  }

  private void stopScheduler()
  {
    if(taskId != -1)
      {
        Bukkit.getScheduler().cancelTask(taskId);
        taskId = -1;
      }
  }

}
