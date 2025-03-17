package dev.pretti.prtminetreasures.placeholders.types;


import dev.pretti.prtminetreasures.crates.interfaces.ICrate;
import dev.pretti.prtminetreasures.placeholders.types.base.Placeholder;
import dev.pretti.prtminetreasures.utils.TimeUtils;

public class CratePlaceholder extends Placeholder<ICrate<?>>
{
  /**
   * Construtor da classe
   */
  public CratePlaceholder()
  {
    super("@crate");
    getPlaceholders().put("@crate_left_hour", struct -> String.format("%02d", TimeUtils.getHours(struct.getTimeLeft())));
    getPlaceholders().put("@crate_left_min", struct -> String.format("%02d", TimeUtils.getMinutes(struct.getTimeLeft())));
    getPlaceholders().put("@crate_left_sec", struct -> String.format("%02d", TimeUtils.getSeconds(struct.getTimeLeft())));
    getPlaceholders().put("@crate_left", struct -> String.valueOf(struct.getTimeLeft()));
  }
}