package dev.pretti.prtminetreasures.holograms;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.enums.EnumCrateHologramStateType;
import dev.pretti.prtminetreasures.holograms.handlers.interfaces.IHologramHandler;
import dev.pretti.prtminetreasures.integrations.types.HDApiIntegration;
import dev.pretti.prtminetreasures.placeholders.PlaceholderManager;
import dev.pretti.prtminetreasures.settings.CrateHologramSettings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CrateHologram
{
  private final PrtMineTreasures   plugin;
  private final PlaceholderManager placeholderManager;

  private final ICrate<?>        crate;
  private       IHologramHandler hologram;

  private final CrateHologramSettings settings;

  private EnumCrateHologramStateType stateType = EnumCrateHologramStateType.NORMAL;

  /**
   * Contrutor da classe
   */
  public CrateHologram(PrtMineTreasures plugin, @NotNull ICrate<?> crate, CrateHologramSettings settings)
  {
    this.plugin             = plugin;
    this.crate              = crate;
    this.placeholderManager = plugin.getPlaceholderManager();
    this.settings           = settings;
  }

  /**
   * Definição das propriedades
   */
  public boolean isShow()
  {
    return settings.isShow();
  }

  public String[] getLines()
  {
    return settings.getWaiting();
  }

  public String[] getLinesDestroy()
  {
    return settings.getDestroy();
  }

  public double getHeight()
  {
    return settings.getHeight();
  }

  public int getDistance()
  {
    return settings.getDistance();
  }

  /**
   * Definções de comportamento
   */
  public void setStateType(EnumCrateHologramStateType stateType)
  {
    this.stateType = stateType;
    update();
  }

  /**
   * Método de atualização do holograma
   */
  public void update()
  {
    if(hologram == null)
      {
        return;
      }

    Location location = crate.getLocation();
    World    world;
    if(location == null || (world = location.getWorld()) == null)
      {
        syncApply(null);
        return;
      }
    Collection<Entity> rangePlayers = world.getNearbyEntities(location, getDistance(), getDistance(), getDistance());
    if(rangePlayers.isEmpty())
      {
        syncApply(null);
        return;
      }
    List<Player> vissiblePlayers = new ArrayList<>();
    for(Entity entity : rangePlayers)
      {
        if(entity instanceof Player)
          {
            if(entity.getLocation().distance(location) <= getDistance())
              {
                vissiblePlayers.add((Player) entity);
              }
          }
      }
    syncApply(vissiblePlayers);
  }

  private String getLinePlaceholder(String line)
  {
    return placeholderManager.replaceCrateAll(line, crate);
  }

  /**
   * Aplica as alterações de forma síncrona
   */
  protected void syncApply(@Nullable List<Player> visiblePlayers)
  {
    if(hologram == null)
      {
        return;
      }

    if(!isShow())
      {
        delete();
        return;
      }

    if(!Bukkit.isPrimaryThread())
      {
        new BukkitRunnable()
        {
          public void run()
          {
            CrateHologram.this.syncApply(visiblePlayers);
          }
        }.runTaskLater(plugin, 0L);
        return;
      }

    String[] activeLines = (stateType == EnumCrateHologramStateType.NORMAL) ? getLines() : getLinesDestroy();
    if(activeLines == null || activeLines.length == 0)
      {
        delete();
        return;
      }

    boolean containsPlayers = visiblePlayers != null && !visiblePlayers.isEmpty();
    if(!containsPlayers)
      {
        delete(true);
        return;
      }
    if(hologram.isDeleted())
      {
        hologram.recreate();
      }

    int excessLines = hologram.size() - activeLines.length;
    hologram.removeLines(excessLines);

    for(int i = 0; i < activeLines.length; i++)
      {
        hologram.setTextLine(i, getLinePlaceholder(activeLines[i]));
      }

    hologram.clearVisibility();
    visiblePlayers.forEach(player -> hologram.setVisibility(player, true));
  }


  /**
   * Delete o holograma
   */
  public void delete()
  {
    delete(false);
  }

  public void delete(boolean hologramOnly)
  {
    if(hologram != null)
      {
        hologram.delete();
        if(!hologramOnly)
          {
            hologram = null;
          }
      }
  }

  /**
   * Cria o holograma
   */
  public void create(Location loc)
  {
    if(!isShow())
      {
        delete();
        return;
      }
    HDApiIntegration holoApi = plugin.getIntegrationManager().getHDApi();
    if(holoApi != null)
      {
        String[] lines = getLines();
        if(lines == null || lines.length == 0)
          {
            return;
          }
        double   height   = getHeight();
        Location location = loc.clone();
        location.add(0.5, height, 0.5);
        hologram = holoApi.createHologram(location);
        if(hologram == null)
          {
            return;
          }
        syncApply(Collections.singletonList(crate.getOwner()));

      }
  }

}
