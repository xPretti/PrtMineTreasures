package dev.pretti.prtminetreasures.placeholders.base;


import dev.pretti.prtminetreasures.placeholders.types.base.Placeholder;
import dev.pretti.prtminetreasures.utils.ReplaceUtils;

import java.util.List;

public class Placeholders<T>
{
  protected final Placeholder<T> _placeholder;

  /**
   * Construtor da classe;
   */
  public Placeholders(Placeholder<T> placeholder)
  {
    this._placeholder = placeholder;
  }

  /**
   * Métodos de replace do jogador
   */
  public String replace(T struct, String text)
  {
    if(toContinue(text))
      {
        text = _placeholder.replace(struct, text);
      }
    return (ReplaceUtils.toColorMessage(text));
  }

  public boolean replace(T struct, List<String> texts)
  {
    if(texts != null)
      {
        if(!texts.isEmpty())
          {
            texts.replaceAll(s -> replace(struct, s));
            return (true);
          }
      }
    return (false);
  }


  /**
   * Métodos de verificações
   */
  protected boolean toContinue(String text)
  {
    if(!text.isEmpty())
      {
        if(_placeholder.usePlaceholderReference())
          {
            return (text.contains(_placeholder.getPlaceholderReference()));
          }
        return (true);
      }
    return (false);
  }

}
