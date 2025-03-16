package dev.pretti.prtminetreasures.crates;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import dev.pretti.prtminetreasures.enums.EnumCrateHologramStateType;
import dev.pretti.prtminetreasures.handlers.IHologramHandler;
import dev.pretti.prtminetreasures.integrations.types.HDApiIntegration;
import org.bukkit.Location;

public class CrateHologram
{
  private IHologramHandler hologram;

  private boolean  show         = true;
  private String[] lines        = {"§6Tesouro", "", "§eDono: §7@owner", "§eTempo: §c@time", "", "§a[Clique para abrir]"};
  private String[] linesDestroy = {"§6Tesouro", "", "§cTesouro vazio"};
  private double   height       = 3;

  private EnumCrateHologramStateType stateType = EnumCrateHologramStateType.NORMAL;

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

  public CrateHologram setLines(String[] lines)
  {
    this.lines = lines;
    return this;
  }

  public String[] getLinesDestroy()
  {
    return linesDestroy;
  }

  public CrateHologram setLinesDestroy(String[] linesDestroy)
  {
    this.linesDestroy = linesDestroy;
    return this;
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
        hologram.setTextLine(i, activeLines[i]);
      }
  }


  /**
   * Delete o holograma
   */
  public void delete()
  {
    if(hologram != null)
      {
        hologram.delete();
        hologram = null;
      }
  }

  /**
   * Cria o holograma
   */
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
            update();
          }
      }
  }

}
