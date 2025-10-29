package dev.pretti.prtminetreasures.integrations.types;

import de.tr7zw.changeme.nbtapi.NBT;
import dev.pretti.prtminetreasures.configs.interfaces.IDependenciesConfig;
import dev.pretti.prtminetreasures.integrations.base.Integration;
import dev.pretti.prtminetreasures.utils.LogUtils;
import dev.pretti.prtminetreasures.utils.TypeUtils;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NbtApiIntegration extends Integration
{
  private final IDependenciesConfig dependenciesConfig;

  /**
   * Construtor da classe
   */
  public NbtApiIntegration(IDependenciesConfig dependenciesConfig)
  {
    super("NBTAPI", false);
    this.dependenciesConfig = dependenciesConfig;
  }

  /**
   * Implementação do carregamento
   */
  @Override
  public boolean load()
  {
    if(!dependenciesConfig.useServerNBT())
      {
        if(tryLoadShaded())
          {
            return true;
          }
      }
    boolean loadByPlugins = super.load();
    if(!isLoaded() && dependenciesConfig.useServerNBT())
      {
        if(tryLoadShaded())
          {
            return true;
          }
      }
    return loadByPlugins;
  }

  private boolean tryLoadShaded()
  {
    if(NBT.preloadApi())
      {
        LogUtils.logSuccess(String.format("%s successful shaded integration.", getLibraryName()));
        setLoaded(true);
        return true;
      }
    LogUtils.logWarn(String.format("%s unsuccessful shaded integration.", getLibraryName()));
    return false;
  }

  /**
   * Métodos da biblioteca
   */
  public void setMeta(@NotNull ItemStack itemStack, String key, String value)
  {
    switch(TypeUtils.getEnumType(value))
      {
        case BOOLEAN:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setBoolean(key, TypeUtils.toBoolean(value));
            });
          break;
        case INT:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setInteger(key, TypeUtils.toInteger(value));
            });
          break;
        case LONG:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setLong(key, TypeUtils.toLong(value));
            });
          break;
        case DOUBLE:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setDouble(key, TypeUtils.toDouble(value));
            });
          break;
        default:
          NBT.modify(itemStack, nbt ->
            {
              nbt.setString(key, value);
            });
          break;
      }
  }
}
