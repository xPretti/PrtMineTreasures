package dev.pretti.prtminetreasures.integrations.interfaces;

import dev.pretti.prtminetreasures.holograms.handlers.interfaces.IHologramHandler;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IHologramIntegration
{
  @Nullable
  IHologramHandler createHologram(@NotNull Location location);
}
