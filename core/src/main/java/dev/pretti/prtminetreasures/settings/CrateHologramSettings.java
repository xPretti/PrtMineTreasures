package dev.pretti.prtminetreasures.settings;

import org.jetbrains.annotations.Nullable;

public class CrateHologramSettings
{
  private boolean  show;
  private String[] waiting;
  private String[] destroy;
  private double   height;
  private int      distance;

  public CrateHologramSettings()
  {
    this(false, null, null, 3, 5);
  }

  public CrateHologramSettings(boolean show, @Nullable String[] waiting, @Nullable String[] destroy, double height, int distance)
  {
    this.show     = show;
    this.waiting  = waiting;
    this.destroy  = destroy;
    this.height   = height;
    this.distance = distance;
  }

  public int getDistance()
  {
    return distance;
  }

  public void setDistance(int distance)
  {
    this.distance = distance;
  }

  public double getHeight()
  {
    return height;
  }

  public void setHeight(double height)
  {
    this.height = height;
  }

  @Nullable
  public String[] getDestroy()
  {
    return destroy;
  }

  public void setDestroy(@Nullable String[] destroy)
  {
    this.destroy = destroy;
  }

  @Nullable
  public String[] getWaiting()
  {
    return waiting;
  }

  public void setWaiting(@Nullable String[] waiting)
  {
    this.waiting = waiting;
  }

  public boolean isShow()
  {
    return show;
  }

  public void setShow(boolean show)
  {
    this.show = show;
  }
}
