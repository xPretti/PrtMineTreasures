package dev.pretti.prtminetreasures.treasures.outputs;

import dev.pretti.prtminetreasures.utils.ExpUtils;
import dev.pretti.treasuresapi.contexts.TreasureContext;
import dev.pretti.treasuresapi.processors.interfaces.outputs.IXpOutput;
import org.jetbrains.annotations.NotNull;

public class XpOutput implements IXpOutput
{
  @Override
  public boolean process(@NotNull TreasureContext context, int i, boolean b)
  {
    if(b)
      {
        ExpUtils.addLevel(context.getPlayer(), i);
      }
    else
      {
        ExpUtils.addExp(context.getPlayer(), i);
      }
    return true;
  }
}
