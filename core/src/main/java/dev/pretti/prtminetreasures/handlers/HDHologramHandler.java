package dev.pretti.prtminetreasures.handlers;

import dev.pretti.prtminetreasures.PrtMineTreasures;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.line.HologramLine;
import me.filoghost.holographicdisplays.api.hologram.line.ItemHologramLine;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HDHologramHandler implements IHologramHandler
{
  private final static HolographicDisplaysAPI api = HolographicDisplaysAPI.get(PrtMineTreasures.getInstance());

  private Hologram hologram;

  @Override
  public boolean create(@NotNull Location location)
  {
    hologram = api.createHologram(location);
    return true;
  }

  @Override
  public boolean delete()
  {
    if(hologram == null)
      {
        return false;
      }
    this.hologram.delete();
    this.hologram = null;
    return true;
  }

  @Override
  public boolean isDeleted()
  {
    if(hologram == null)
      {
        return true;
      }
    return hologram.isDeleted();
  }

  @Override
  public void setTextLine(int line, @NotNull String text)
  {
    if(size() <= line)
      {
        hologram.getLines().insertText(line, text);
        return;
      }
    HologramLine l = hologram.getLines().get(line);
    if(l instanceof TextHologramLine)
      {
        ((TextHologramLine) l).setText(text);
        return;
      }
    hologram.getLines().remove(line);
    hologram.getLines().insertText(line, text);
  }

  @Override
  public void setItemLine(int line, @NotNull ItemStack item)
  {
    if(size() <= line)
      {
        hologram.getLines().insertItem(line, item);
        return;
      }
    HologramLine l = hologram.getLines().get(line);
    if(l instanceof ItemHologramLine)
      {
        ((ItemHologramLine) l).setItemStack(item);
        return;
      }
    hologram.getLines().remove(line);
    hologram.getLines().insertItem(line, item);
  }

  @Override
  public void addTextLine(@NotNull String text)
  {
    hologram.getLines().appendText(text);
  }

  @Override
  public void addItemLine(@NotNull ItemStack item)
  {
    hologram.getLines().appendItem(item);
  }

  @Override
  public void removeLine(int line)
  {
    hologram.getLines().remove(line);
  }

  @Override
  public void removeLines()
  {
    hologram.getLines().clear();
  }

  @Override
  public void removeLines(int count)
  {
    if(count > 0 && count <= size())
      {
        for(int i = size() - 1; i >= 0 && i >= size() - count; i--)
          {
            hologram.getLines().remove(i);
          }
      }
  }

  @Override
  public int size()
  {
    return hologram.getLines().size();
  }
}
