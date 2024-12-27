package dev.pretti.prtminetreasures.enums;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum EnumMaterialSolutionType
{
  //ARMORS
  // helmet
  LEATHER_HELMET,
  GOLDEN_HELMET("GOLD_HELMET"),
  CHAINMAIL_HELMET,
  IRON_HELMET,
  TURTLE_HELMET,
  DIAMOND_HELMET,
  NETHERITE_HELMET,

  // chestplate
  LEATHER_CHESTPLATE,
  GOLDEN_CHESTPLATE("GOLD_CHESTPLATE"),
  CHAINMAIL_CHESTPLATE,
  IRON_CHESTPLATE,
  DIAMOND_CHESTPLATE,
  NETHERITE_CHESTPLATE,

  // leggings
  LEATHER_LEGGINGS,
  GOLDEN_LEGGINGS("GOLD_LEGGINGS"),
  CHAINMAIL_LEGGINGS,
  IRON_LEGGINGS,
  DIAMOND_LEGGINGS,
  NETHERITE_LEGGINGS,

  // boots
  LEATHER_BOOTS,
  GOLDEN_BOOTS("GOLD_BOOTS"),
  CHAINMAIL_BOOTS,
  IRON_BOOTS,
  DIAMOND_BOOTS,
  NETHERITE_BOOTS;

  /**
   * Variáveis e Definições
   */
  public static final EnumMaterialSolutionType[] VALUES = values();

  private static final HashMap<String, EnumMaterialSolutionType>                VALUES_NAMES      = new HashMap<>();
  private static final HashMap<String, HashMap<Byte, EnumMaterialSolutionType>> VALUES_NAMES_DATA = new HashMap<>();

  private final Material material;
  private final byte     data;
  private final String[] legacyNames;
  private       boolean  isLegacy = false;

  /**
   * Inicializador
   */
  static
    {
      for(EnumMaterialSolutionType material : VALUES)
        {
          VALUES_NAMES.put(material.name(), material);
          if(material.getData() == 0)
            {
              for(String legacy : material.legacyNames)
                {
                  VALUES_NAMES.put(legacy, material);
                }
            }
          else
            {
              HashMap<Byte, EnumMaterialSolutionType> map = VALUES_NAMES_DATA.computeIfAbsent(material.name(), k -> new HashMap<>());
              map.put(material.getData(), material);
            }
        }
    }

  /**
   * Construtor
   */
  EnumMaterialSolutionType()
  {
    this(0, "");
  }

  EnumMaterialSolutionType(String... legacy)
  {
    this(0, legacy);
  }

  EnumMaterialSolutionType(int data, String... legacy)
  {
    Material material1;
    this.legacyNames = legacy;
    material1        = Material.matchMaterial(this.name());
    if(material1 == null)
      {
        for(String name : legacyNames)
          {
            if((material1 = Material.matchMaterial(name)) != null)
              {
                isLegacy = true;
                break;
              }
          }
      }
    this.material = material1;
    this.data     = (byte) data;
  }

  /**
   * Verificações
   */
  public boolean isLegacy()
  {
    return isLegacy;
  }

  /**
   * Métodos de retornos
   */
  public String[] getLegacyNames()
  {
    return legacyNames;
  }

  public Material getMaterial()
  {
    return material;
  }

  public byte getData()
  {
    return data;
  }

  /**
   * Método de conversão
   */
  public Material tryConvertToLegacy(int legacyIndex)
  {
    if(legacyIndex >= 0 && legacyIndex < legacyNames.length)
      {
        return Material.matchMaterial(legacyNames[legacyIndex]);
      }
    return material;
  }

  @Nullable
  public static EnumMaterialSolutionType tryConvert(@NotNull Material material)
  {
    return tryConvert(material, (byte) 0);
  }

  @Nullable
  public static EnumMaterialSolutionType tryConvert(@NotNull Material material, byte data)
  {
    EnumMaterialSolutionType materialType = VALUES_NAMES.get(material.name());
    if(materialType == null)
      {
        Map<Byte, EnumMaterialSolutionType> map = VALUES_NAMES_DATA.get(material.name());
        materialType = map != null ? map.get(data) : null;
      }
    return materialType;
  }
}
