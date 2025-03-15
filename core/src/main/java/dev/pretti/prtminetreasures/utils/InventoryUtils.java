package dev.pretti.prtminetreasures.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class InventoryUtils
{
  /**
   * Adiciona um item ao inventário corrigindo o tamanho do item caso for necessário
   */
  @Nullable
  public static Collection<ItemStack> addItem(Inventory inventory, ItemStack item, boolean fixStackSize)
  {
    if(inventory != null && item != null)
      {
        Collection<ItemStack> leftoverItems = new ArrayList<>();
        int                   amount        = item.getAmount();
        int                   maxStackSize  = item.getMaxStackSize();
        if(fixStackSize && amount > maxStackSize)
          {
            int fullStacks = amount / maxStackSize;
            int remaining  = amount % maxStackSize;
            for(int i = 0; i < fullStacks; i++)
              {
                ItemStack newItemStack = item.clone();
                newItemStack.setAmount(maxStackSize);
                leftoverItems.addAll(inventory.addItem(newItemStack).values());
              }
            if(remaining > 0)
              {
                ItemStack newItemStack = item.clone();
                newItemStack.setAmount(remaining);
                leftoverItems.addAll(inventory.addItem(newItemStack).values());
              }
            return leftoverItems;
          }
        return inventory.addItem(item).values();
      }
    return null;
  }

  /**
   * Retorna se o inventário estiver vazio
   */
  public static boolean isEmpty(@Nullable Inventory inventory)
  {
    if(inventory != null)
      {
        for(int i = 0; i < inventory.getSize(); i++)
          {
            if(inventory.getItem(i) != null)
              {
                return false;
              }
          }
      }
    return true;
  }

}
