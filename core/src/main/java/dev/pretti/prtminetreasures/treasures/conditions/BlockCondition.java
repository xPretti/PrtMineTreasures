package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.treasuresapi.conditions.interfaces.IInvalidCondition;
import dev.pretti.treasuresapi.conditions.invalids.ListInvalidCondition;
import dev.pretti.treasuresapi.conditions.types.IBlockCondition;
import dev.pretti.treasuresapi.contexts.TreasureContext;
import dev.pretti.treasuresapi.datatypes.MaterialType;
import dev.pretti.treasuresapi.enums.EnumAccessType;
import dev.pretti.treasuresapi.utils.BlockDataUtils;
import dev.pretti.treasuresapi.utils.MaterialUtils;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BlockCondition implements IBlockCondition
{
  private final EnumAccessType        accessType;
  private final HashSet<MaterialType> blockNames;

  private ListInvalidCondition invalidCondition;

  /**
   * Construtor da classe
   */
  public BlockCondition(@NotNull EnumAccessType enumAccessType, @NotNull List<String> list)
  {
    this.accessType = enumAccessType;

    List<String> invalid = new ArrayList<>();
    this.blockNames = MaterialUtils.toHashSetMaterialType(list, invalid);

    if(!invalid.isEmpty())
      {
        invalidCondition = new ListInvalidCondition("Invalid materials", invalid);
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
    if(blockNames.isEmpty())
      {
        return accessType.equals(EnumAccessType.BLACKLIST);
      }
    Block   block  = treasureContext.getEventLocation().getBlock();
    boolean result = blockNames.contains(new MaterialType(block.getType(), (byte) BlockDataUtils.getData(block), false));
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
  public @NotNull HashSet<MaterialType> getBlocks()
  {
    return blockNames;
  }
}
