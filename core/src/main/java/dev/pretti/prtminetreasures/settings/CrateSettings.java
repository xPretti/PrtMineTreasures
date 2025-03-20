package dev.pretti.prtminetreasures.settings;

import dev.pretti.prtminetreasures.datatypes.SoundType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CrateSettings
{
  private int       destroySeconds;
  private String    title;
  private SoundType openSound;
  private SoundType closeSound;
  private boolean   ownerOnly;
  private int       crateRows;

  public CrateSettings()
  {
    this(300, "", null, null, true, 3);
  }

  public CrateSettings(int destroySeconds, @NotNull String title, @Nullable SoundType openSound, @Nullable SoundType closeSound, boolean ownerOnly, int crateRows)
  {
    this.destroySeconds = destroySeconds;
    this.title          = title;
    this.openSound      = openSound;
    this.closeSound     = closeSound;
    this.ownerOnly      = ownerOnly;
    this.crateRows      = crateRows;
  }

  public int getDestroySeconds()
  {
    return destroySeconds;
  }

  public void setDestroySeconds(int destroySeconds)
  {
    this.destroySeconds = destroySeconds;
  }

  @NotNull
  public String getTitle()
  {
    return title;
  }

  public void setTitle(@NotNull String title)
  {
    this.title = title;
  }

  @Nullable
  public SoundType getOpenSound()
  {
    return openSound;
  }

  public void setOpenSound(@Nullable SoundType openSound)
  {
    this.openSound = openSound;
  }

  @Nullable
  public SoundType getCloseSound()
  {
    return closeSound;
  }

  public void setCloseSound(@Nullable SoundType closeSound)
  {
    this.closeSound = closeSound;
  }

  public boolean isOwnerOnly()
  {
    return ownerOnly;
  }

  public void setOwnerOnly(boolean ownerOnly)
  {
    this.ownerOnly = ownerOnly;
  }

  public int getCrateRows()
  {
    return crateRows;
  }

  public void setCrateRows(int crateRows)
  {
    this.crateRows = crateRows;
  }
}
