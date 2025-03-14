package dev.pretti.prtminetreasures.datatypes;

import org.bukkit.Sound;

public class SoundType
{
  private final Sound sound;
  private final float volume;
  private final float pitch;

  /**
   * Construtor
   */
  public SoundType(Sound sound, float volume, float pitch)
  {
    this.sound  = sound;
    this.volume = volume;
    this.pitch  = pitch;
  }

  /**
   * Métodos de retornos
   */
  public Sound getSound()
  {
    return sound;
  }

  public float getPitch()
  {
    return pitch;
  }

  public float getVolume()
  {
    return volume;
  }
}
