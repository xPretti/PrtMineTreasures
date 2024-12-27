package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.treasuresapi.conditions.interfaces.IInvalidCondition;
import dev.pretti.treasuresapi.conditions.invalids.ListInvalidCondition;
import dev.pretti.treasuresapi.conditions.types.IBiomeCondition;
import dev.pretti.treasuresapi.contexts.TreasureContext;
import dev.pretti.treasuresapi.enums.EnumAccessType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BiomeCondition implements IBiomeCondition
{
  private final EnumAccessType accessType;
  private final HashSet<Biome> biomeNames = new HashSet<>();

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
            Biome biome = Biome.valueOf(name);
            biomeNames.add(biome);
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
    Block   block  = treasureContext.getEventLocation().getBlock();
    boolean result = biomeNames.contains(block.getBiome());
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
  public @NotNull HashSet<Biome> getBiomes()
  {
    return biomeNames;
  }
}
