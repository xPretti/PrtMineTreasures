package dev.pretti.prtminetreasures.structs;

import org.bukkit.entity.Player;

public class PlayerStruct
{
  public String displayName;
  public double X;
  public double Y;
  public double Z;
  public String world;

  /**
   * Construtor da classe
   */
  public PlayerStruct(Player player)
  {
    this.displayName = player.getDisplayName();
    this.X           = player.getLocation().getX();
    this.Y           = player.getLocation().getY();
    this.Z           = player.getLocation().getZ();
    this.world       = player.getWorld().getName();
  }

}
