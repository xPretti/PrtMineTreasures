package dev.pretti.prtminetreasures.placeholders.types.base;


import java.util.HashMap;
import java.util.function.Function;

public class Placeholder<T>
{
  private String _placeholderReference;

  private final HashMap<String, Function<T, String>> _placeholders = new HashMap<>();

  /**
   * Construtor da classe
   */
  public Placeholder()
  {
  }

  public Placeholder(String reference)
  {
    _placeholderReference = reference;
  }

  /**
   * Métodos de conversões
   */
  public String replace(T struct, String message)
  {
    if(struct != null && message != null)
      {
        if(!message.isEmpty())
          {
            for(HashMap.Entry<String, Function<T, String>> entry : _placeholders.entrySet())
              {
                message = message.replace(entry.getKey(), entry.getValue().apply(struct));
              }
          }
      }
    return message;
  }

  /**
   * Métodos de retornos
   */
  protected HashMap<String, Function<T, String>> getPlaceholders()
  {
    return _placeholders;
  }

  public boolean usePlaceholderReference()
  {
    return _placeholderReference != null;
  }

  public String getPlaceholderReference()
  {
    return _placeholderReference;
  }
}
