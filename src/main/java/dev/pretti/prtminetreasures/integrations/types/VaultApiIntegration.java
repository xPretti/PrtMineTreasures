package dev.pretti.prtminetreasures.integrations.types;

import dev.pretti.prtminetreasures.integrations.Integration;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultApiIntegration extends Integration
{
  private Economy economy;

  /**
   * Construtor da classe
   */
  public VaultApiIntegration()
  {
    super("Vault", false);
  }

  /**
   * Implementação do carregamento
   */
  @Override
  public boolean load()
  {
    if(tryLoad())
      {
        try
          {
            RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
            if(rsp != null && (economy = rsp.getProvider()) != null)
              {
                sendHookMessage();
                return true;
              }
          } catch(Throwable ignored)
          {
            sendHookMessage();
            unload();
          }
      }
    return !isRequired();
  }

  /**
   * Métodos da biblioteca
   */
  public boolean depositOrWithdraw(Player player, double amount)
  {
    return amount < 0 ? withdraw(player, Math.abs(amount)) : deposit(player, amount);
  }

  public boolean deposit(Player player, double amount)
  {
    if(isLoaded())
      {
        economy.depositPlayer(player, amount);
        return true;
      }
    return false;
  }

  public boolean withdraw(Player player, double amount)
  {
    if(isLoaded())
      {
        economy.withdrawPlayer(player, amount);
        return true;
      }
    return false;
  }
}
