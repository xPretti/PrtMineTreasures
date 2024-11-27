package dev.pretti.prtminetreasures.treasures.conditions;

import dev.pretti.prtminetreasures.datatypes.MaterialType;
import dev.pretti.prtminetreasures.utils.LogUtils;
import dev.pretti.prtminetreasures.utils.MaterialUtils;
import dev.pretti.treasuresapi.conditions.interfaces.ICondition;
import dev.pretti.treasuresapi.enums.EnumAccessType;
import dev.pretti.treasuresapi.processors.context.TreasureContext;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BlockCondition implements ICondition
{
  private final EnumAccessType        accessType;
  private final HashSet<MaterialType> blockNames;

  /**
   * Construtor da classe
   */
  public BlockCondition(@NotNull EnumAccessType enumAccessType, @NotNull List<String> list) throws IllegalArgumentException
  {
    this.accessType = enumAccessType;

    List<String> invalid = new ArrayList<>();
    this.blockNames = MaterialUtils.toHashSetMaterialType(list, invalid);

    if(!invalid.isEmpty())
      {
        for(String name : invalid)
          {
            LogUtils.logError(String.format("§8Invalid block name: §c%s§8.", name));
          }
        throw new IllegalArgumentException("Invalid materials.");
      }
  }

  /**
   * Método de verificação
   */
  @Override
  public boolean evaluate(@NotNull TreasureContext treasureContext)
  {
    if(blockNames.isEmpty())
      {
        return true;
      }
    Block   block  = treasureContext.getEventLocation().getBlock();
    boolean result = blockNames.contains(new MaterialType(block.getType(), block.getData(), false));
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
}
