package dev.pretti.prtminetreasures.utils;

import dev.pretti.treasuresapi.datatypes.MaterialType;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MaterialUtils
{
  /**
   * Retorna uma lista de MaterialType
   */
  @Nullable
  public static List<MaterialType> toListMaterialType(List<String> names)
  {
    List<MaterialType> result = new ArrayList<>();
    for(String value : names)
      {
        MaterialType materialType = toMaterialType(value);
        if(materialType != null)
          {
            result.add(materialType);
          }
      }
    return result.isEmpty() ? null : result;
  }

  /**
   * Retorna uma Set de MaterialType
   */
  @Nullable
  public static HashSet<MaterialType> toHashSetMaterialType(List<String> names)
  {
    return toHashSetMaterialType(names, null);
  }

  @Nullable
  public static HashSet<MaterialType> toHashSetMaterialType(List<String> names, List<String> invalidMaterials)
  {
    HashSet<MaterialType> result = new HashSet<>();
    for(String value : names)
      {
        MaterialType materialType = toMaterialType(value);
        if(materialType != null)
          {
            result.add(materialType);
          }
        else if(invalidMaterials != null)
          {
            invalidMaterials.add(value);
          }
      }
    return result.isEmpty() ? null : result;
  }

  /**
   * Método de conversão de texto para o novo Material
   */
  @Nullable
  public static MaterialType toMaterialType(String text)
  {
    String[] params = text.split(":");
    if(params.length > 0)
      {
        Material material = Material.getMaterial(params[0].trim());
        if(material != null)
          {
            byte data = params.length > 1 ? Byte.parseByte(params[1].trim()) : 0;
            return new MaterialType(material, data, params.length > 1);
          }
      }
    return null;
  }

  /**
   * Retorna o nome do material
   */
  public static String getMaterialName(Material material)
  {
    if(material == null)
      {
        return "";
      }
    return WordUtils.capitalize(material.name().replace("_", " ").toLowerCase());
  }
}
