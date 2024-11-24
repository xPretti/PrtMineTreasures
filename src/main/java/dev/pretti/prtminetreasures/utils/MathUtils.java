package dev.pretti.prtminetreasures.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtils
{
  private static final Random RANDOM = new Random();

  /**
   * Métodos de geração de ocorrencias
   */
  public static int getExpectedOccurrencesInt(double chance, int amount)
  {
    return (int) Math.round(getExpectedOccurrences(chance, amount));
  }

  public static int getUnexpectedOccurrencesInt(double chance, int amount)
  {
    return (int) Math.round(getUnexpectedOccurrences(chance, amount));
  }

  public static int getOccurrences(double chance, int amount, int min, int max)
  {
    if(amount > 0 && chance > 0)
      {
        double successes    = getExpectedOccurrences(chance, amount);
        double variability  = RANDOM.nextDouble() * (max - min) * 0.1; // 10% de variabilidade
        double averageValue = (min + max) / 2.0 + variability * (RANDOM.nextBoolean() ? 1 : -1);
        int    totalValue   = (int) Math.round(successes * averageValue);
        return Math.max(0, Math.min(totalValue, max * amount));
      }
    return 0;
  }

  public static double getExpectedOccurrences(double chance, int amount)
  {
    if(amount > 0 && chance > 0)
      {
        chance = Math.min(100, chance);
        double chanceDivisor = chance / 100;
        double expectedValue = amount * chanceDivisor;
        if(expectedValue < 1)
          {
            return Math.random() < expectedValue ? 1 : 0;
          }
        double gaussian    = RANDOM.nextGaussian();
        double sqrt        = Math.sqrt(amount * chanceDivisor * (1 - chanceDivisor));
        double occurrences = Math.round(expectedValue + (gaussian * sqrt));
        return Math.max(0, occurrences);
      }
    return 0;
  }

  public static double getUnexpectedOccurrences(double chance, int amount)
  {
    if(amount > 0 && chance > 0)
      {
        chance = Math.min(100, chance);
        double chanceDivisor = chance / 100;
        double expectedValue = amount * (1 - chanceDivisor);
        if(expectedValue < 1)
          {
            return Math.random() < expectedValue ? 1 : 0;
          }
        double gaussian    = RANDOM.nextGaussian();
        double sqrt        = Math.sqrt(amount * (1 - chanceDivisor) * chanceDivisor);
        double occurrences = Math.round(expectedValue + (gaussian * sqrt));
        return Math.max(0, occurrences);
      }
    return 0;
  }

  /**
   * Retorna uma chance gradativa começando por 100% e diminuindo conforme o level aumenta
   */
  public static double getSmoothChance(int level, int limit, double minChance)
  {
    if (level >= limit) {
      return 100.0;
    }
    double progress = (double) level / limit;
    double smoothChance = minChance + progress * (1.0 - minChance);
    return smoothChance * 100;
  }

  public static double getSmoothChance(int level, int limit, double startChance, double minChance)
  {
    if(level < 1)
      {
        return 0;
      }
    if(level >= limit)
      {
        return startChance * 100;
      }
    double progress     = (double) (level - 1) / (limit - 1);
    double smoothChance = minChance + progress * (startChance - minChance);
    return smoothChance * 100;
  }

  public static double getSmoothChanceReverse(int level, int limit, double minChance)
  {
    if(level >= limit)
      {
        return minChance * 100;
      }
    return (1.0 - ((double) level / limit) * (1 - minChance)) * 100;
  }

  public static double getSmoothChanceReverse(int level, int levelLimit, double startChance, double minChance)
  {
    if(level < 1)
      {
        return 100.0;
      }
    if(level >= levelLimit)
      {
        return minChance * 100;
      }
    double progress     = (double) (level - 1) / (levelLimit - 1);
    double smoothChance = startChance - progress * (startChance - minChance);
    return smoothChance * 100;
  }

  /**
   * Métodos de conversão de chances
   */
  public static boolean isChance(double chance)
  {
    return (isChance(0.01, 100, chance));
  }

  public static boolean isChance(double min, double max, double chance)
  {
    double newChance = getChance(min, max);
    return (chance >= newChance);
  }

  public static boolean isChance(int min, int max, int chance)
  {
    double newChance = getRandom(min, max);
    return (chance <= newChance);
  }

  public static double getChance(double min, double max)
  {
    return (min + ((Math.random() * (max - min))));
  }

  /**
   * Retorna um numero aletório
   */
  public static int getRandom(int start, int end)
  {
    if(++end > start)
      {
        return (ThreadLocalRandom.current().nextInt(start, end));
      }
    return (start);
  }

  public static double getRandom(double start, double end)
  {
    if(end > start)
      {
        return (ThreadLocalRandom.current().nextDouble(start, end));
      }
    return (start);
  }
}
