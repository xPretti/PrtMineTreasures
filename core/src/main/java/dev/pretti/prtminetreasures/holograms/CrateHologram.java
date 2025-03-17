package dev.pretti.prtminetreasures.holograms;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.enums.EnumCrateHologramStateType;
import dev.pretti.prtminetreasures.handlers.IHologramHandler;
import dev.pretti.prtminetreasures.integrations.types.HDApiIntegration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class CrateHologram
{
  private final ICrate<?>        crate;
  private       IHologramHandler hologram;

  // AQUI SERÁ TROCADO POR UMA HOLOGRAM SETTINGS PARA QUE SEJA COMPATÍVEL COM /mt reload
  private boolean  show         = true;
  private String[] lines        = {"§6Tesouro", "", "§eDono: §7@owner", "§eTempo: §c@time", "", "§a[Clique para abrir]"};
  private String[] linesDestroy = {"§6Tesouro", "", "§cTesouro vazio"};
  private double   height       = 3;
  private int      distance     = 10;

  private EnumCrateHologramStateType stateType = EnumCrateHologramStateType.NORMAL;

  /**
   * Contrutor da classe
   */
  public CrateHologram(@NotNull ICrate<?> crate)
  {
    this.crate = crate;
  }

  /**
   * Definição das propriedades
   */
  public boolean isShow()
  {
    return show;
  }

  public CrateHologram setShow(boolean show)
  {
    this.show = show;
    return this;
  }

  public String[] getLines()
  {
    return lines;
  }

  public CrateHologram setLines(String[] lines, String[] linesDestroy)
  {
    this.lines        = lines;
    this.linesDestroy = linesDestroy;
    return this;
  }

  public String[] getLinesDestroy()
  {
    return linesDestroy;
  }

  public double getHeight()
  {
    return height;
  }

  public CrateHologram setHeight(double height)
  {
    this.height = height;
    return this;
  }

  public int getDistance()
  {
    return distance;
  }

  public CrateHologram setDistance(int distance)
  {
    this.distance = distance;
    return this;
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
    if(!Bukkit.isPrimaryThread())
      {
        Location location = crate.getLocation();
        World    world;
        if(location == null || (world = location.getWorld()) == null)
          {
            delete(true);
            return;
          }
        Collection<Entity> rangePlayers = world.getNearbyEntities(location, distance, distance, distance);
        if(rangePlayers.isEmpty())
          {
            delete(true);
            return;
          }
        hologram.clearVisibility();
        boolean toShow = false;
        for(Entity entity : rangePlayers)
          {
            if(entity instanceof Player)
              {
                if(entity.getLocation().distance(location) <= distance)
                  {
                    if(hologram.isDeleted())
                      {
                        recreate();
                        new BukkitRunnable()
                        {
                          public void run()
                          {
                            CrateHologram.this.update();
                          }
                        }.runTaskLater(PrtMineTreasures.getInstance(), 0L);
                        return;
                      }
                    hologram.setVisibility((Player) entity, true);
                    toShow = true;
                  }
              }
          }

        if(!toShow)
          {
            delete(true);
            return;
          }
      }
    if(hologram.isDeleted())
      {
        return;
      }
    if(!Bukkit.isPrimaryThread())
      {
        new BukkitRunnable()
        {
          public void run()
          {
            CrateHologram.this.update();
          }
        }.runTaskLater(PrtMineTreasures.getInstance(), 0L);
        return;
      }
    String[] activeLines = (stateType == EnumCrateHologramStateType.NORMAL) ? lines : linesDestroy;
    if(activeLines == null)
      {
        delete();
        return;
      }

    int excessLines = hologram.size() - activeLines.length;
    hologram.removeLines(excessLines);

    for(int i = 0; i < activeLines.length; i++)
      {
        hologram.setTextLine(i, getLinePlaceholder(activeLines[i]));
      }
  }

  private String getLinePlaceholder(String line)
  {
    return line.replaceAll("@owner", crate.getOwner().getDisplayName()).replaceAll("@time", String.valueOf(crate.getTimeLeft()));
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
    if(!Bukkit.isPrimaryThread())
      {
        new BukkitRunnable()
        {
          public void run()
          {
            CrateHologram.this.delete(hologramOnly);
          }
        }.runTaskLater(PrtMineTreasures.getInstance(), 0L);
        return;
      }

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
  public void recreate()
  {
    if(hologram == null || !hologram.isDeleted())
      {
        return;
      }
    if(!Bukkit.isPrimaryThread())
      {
        new BukkitRunnable()
        {
          public void run()
          {
            CrateHologram.this.recreate();
          }
        }.runTaskLater(PrtMineTreasures.getInstance(), 0L);
        return;
      }
    hologram.recreate();
  }

  public void create(Location loc)
  {
    if(!show)
      {
        delete();
      }
    HDApiIntegration holoApi = PrtMineTreasures.getInstance().getIntegrationManager().getHDApi();
    if(holoApi != null)
      {
        int size = lines.length;
        if(size > 0)
          {
            Location location = loc.clone();
            location.add(0.5, height, 0.5);
            hologram = holoApi.createHologram(location);
            if(hologram == null)
              {
                return;
              }
            hologram.setVisibility(crate.getOwner(), true);
            update();
          }
      }
  }

}
