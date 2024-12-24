package dev.pretti.prtminetreasures.metadatas;

import dev.pretti.prtminetreasures.integrations.types.PlaceholderApiIntegration;
import dev.pretti.prtminetreasures.metadatas.conditions.interfaces.IMetadataCondition;
import dev.pretti.prtminetreasures.metadatas.conditions.types.NumberMetaComparator;
import dev.pretti.prtminetreasures.metadatas.conditions.types.StringMetaComparator;
import dev.pretti.treasuresapi.datatypes.MetadataConditionType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemMetadataProcess
{
  private List<IMetadataCondition> metaConditions;

  /**
   * Metodo de carregamento
   */
  public boolean load(PlaceholderApiIntegration placeholderApiIntegration, List<MetadataConditionType> metadatas)
  {
    if(metadatas != null)
      {
        List<IMetadataCondition> build = new ArrayList<>();
        for(MetadataConditionType meta : metadatas)
          {
            switch(meta.getType())
              {
                case CONTAINS:
                  build.add(new StringMetaComparator(placeholderApiIntegration, meta.getKey(), meta.getValue(), String::contains));
                  break;
                case EQUALS:
                  build.add(new StringMetaComparator(placeholderApiIntegration, meta.getKey(), meta.getValue(), Objects::equals));
                  break;
                case EQUALS_IGNORE_CASE:
                  build.add(new StringMetaComparator(placeholderApiIntegration, meta.getKey(), meta.getValue(), String::equalsIgnoreCase));
                  break;
                case NOT_CONTAINS:
                  build.add(new StringMetaComparator(placeholderApiIntegration, meta.getKey(), meta.getValue(), (v1, v2) -> !v1.contains(v2)));
                  break;
                case NOT_EQUALS:
                  build.add(new StringMetaComparator(placeholderApiIntegration, meta.getKey(), meta.getValue(), (v1, v2) -> !Objects.equals(v1, v2)));
                  break;
                case NOT_EQUALS_IGNORE_CASE:
                  build.add(new StringMetaComparator(placeholderApiIntegration, meta.getKey(), meta.getValue(), (v1, v2) -> !v1.equalsIgnoreCase(v2)));
                  break;
                case NUMBER_EQUALS:
                  build.add(new NumberMetaComparator(placeholderApiIntegration, meta.getKey(), meta.getValue(), Objects::equals));
                  break;
                case NUMBER_NOT_EQUALS:
                  build.add(new NumberMetaComparator(placeholderApiIntegration, meta.getKey(), meta.getValue(), (v1, v2) -> !Objects.equals(v1, v2)));
                  break;
                case NUMBER_GREATER:
                  build.add(new NumberMetaComparator(placeholderApiIntegration, meta.getKey(), meta.getValue(), (v1, v2) -> v1 > v2));
                  break;
                case NUMBER_GREATER_OR_EQUALS:
                  build.add(new NumberMetaComparator(placeholderApiIntegration, meta.getKey(), meta.getValue(), (v1, v2) -> v1 >= v2));
                  break;
                case NUMBER_LESS:
                  build.add(new NumberMetaComparator(placeholderApiIntegration, meta.getKey(), meta.getValue(), (v1, v2) -> v1 < v2));
                  break;
                case NUMBER_LESS_OR_EQUALS:
                  build.add(new NumberMetaComparator(placeholderApiIntegration, meta.getKey(), meta.getValue(), (v1, v2) -> v1 <= v2));
                  break;
                default:
                  return false;
              }
          }
        if(!build.isEmpty())
          {
            metaConditions = build;
            return true;
          }
      }
    return false;
  }

  /**
   * Método de verificação das metadatas presentes no item
   */
  public boolean evaluate(@NotNull Player player, @NotNull ItemStack item)
  {
    if(metaConditions != null)
      {
        for(IMetadataCondition condition : metaConditions)
          {
            if(!condition.evaluate(player, item))
              {
                return false;
              }
          }
        return true;
      }
    return false;
  }
}
