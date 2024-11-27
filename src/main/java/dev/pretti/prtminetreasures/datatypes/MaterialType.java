package dev.pretti.prtminetreasures.datatypes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class MaterialType
{
  private Material material;
  private byte     data;
  private boolean  useData;

  /**
   * Construtor da classe
   */
  public MaterialType()
  {
    this(Material.AIR, (byte) 0, true);
  }

  public MaterialType(Material material, byte data)
  {
    this(material, data, true);
  }

  public MaterialType(Material material, byte data, boolean useData)
  {
    this.material = material;
    this.data     = data;
    this.useData  = useData;
  }

  public MaterialType(ItemStack itemStack)
  {
    this(itemStack.getType(), (byte) itemStack.getDurability(), true);
  }

  /**
   * Retornos e Definições
   */
  public Material getMaterial()
  {
    return material;
  }

  public void setMaterial(Material material)
  {
    this.material = material;
  }

  public byte getData()
  {
    return data;
  }

  public void setData(byte data)
  {
    this.data = data;
  }

  public boolean isUseData()
  {
    return useData;
  }

  public void setUseData(boolean useData)
  {
    this.useData = useData;
  }


  /**
   * Métodos de verificações
   */
  public boolean equals(Material material)
  {
    return this.material.equals(material);
  }

  public boolean equals(Material material, byte data)
  {
    return (this.material.equals(material) && this.data == data);
  }

  @Override
  public boolean equals(Object o)
  {
    if(this == o)
      {
        return true;
      }
    if(o == null || getClass() != o.getClass())
      {
        return false;
      }
    MaterialType that = (MaterialType) o;
    if(that.isUseData() || isUseData())
      {
        return equals(that.getMaterial(), that.getData());
      }
    return equals(that.getMaterial());
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(material);
  }
}
