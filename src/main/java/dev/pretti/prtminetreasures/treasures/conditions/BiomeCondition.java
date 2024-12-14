package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.treasuresapi.conditions.interfaces.IInvalidCondition;
import dev.pretti.treasuresapi.conditions.invalids.ListInvalidCondition;
import dev.pretti.treasuresapi.conditions.types.IBiomeCondition;
import dev.pretti.treasuresapi.contexts.TreasureContext;
import dev.pretti.treasuresapi.enums.EnumAccessType;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BiomeCondition implements IBiomeCondition
{
  private final EnumAccessType  accessType;
  private final HashSet<String> biomeNames = new HashSet<>();

  private ListInvalidCondition invalidCondition;

  /**
   * Construtor da classe
   */
  public BiomeCondition(@NotNull EnumAccessType enumAccessType, @NotNull List<String> list)
  {
    this.accessType = enumAccessType;

    List<String> invalidMaterials = new ArrayList<>();
    for(String name : list)
      {
        try
          {
            Biome.valueOf(name);
            biomeNames.add(name);
          } catch(Throwable e)
          {
            invalidMaterials.add(name);
          }
      }
    if(!invalidMaterials.isEmpty())
      {
        invalidCondition = new ListInvalidCondition("Invalid biomes", invalidMaterials);
      }
  }

  /**
   * Retorna o invalidCondition
   */
  @Override
  public @Nullable IInvalidCondition getInvalidCondition()
  {
    return invalidCondition;
  }

  /**
   * Método de verificação
   */
  @Override
  public boolean evaluate(@NotNull TreasureContext treasureContext)
  {
    if(biomeNames.isEmpty())
      {
        return accessType.equals(EnumAccessType.BLACKLIST);
      }
    Player  player = treasureContext.getPlayer();
    int     x      = player.getLocation().getBlockX();
    int     z      = player.getLocation().getBlockZ();
    boolean result = biomeNames.contains(player.getWorld().getBiome(x, z).name());
    if(accessType.equals(EnumAccessType.WHITELIST))
      {
        return result;
      }
    else if(accessType.equals(EnumAccessType.BLACKLIST))
      {
        return !result;
      }
    return true;
  }

  /**
   * Retornos
   */
  @Override
  public @NotNull EnumAccessType getAccessType()
  {
    return accessType;
  }

  @Override
  public @NotNull HashSet<String> getBiomes()
  {
    return biomeNames;
  }
}
