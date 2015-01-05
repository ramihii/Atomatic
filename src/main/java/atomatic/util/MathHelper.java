package atomatic.util;

import java.util.Random;

public class MathHelper
{
    public static final double E = Math.E;
    public static final double PI = Math.PI;

    private static Random random = new Random();

    private MathHelper()
    {

    }

    public static int clampInt(int value, int min, int max)
    {
        return value < min ? min : value > max ? max : value;
    }

    public static double clampDouble(double value, double min, double max)
    {
        return value < min ? min : value > max ? max : value;
    }

    public static float clampFloat(float value, float min, float max)
    {
        return value < min ? min : value > max ? max : value;
    }

    public static int clampInt(int value, int max)
    {
        return clampInt(value, 0, max);
    }

    public static double clampDouble(double value, double max)
    {
        return clampDouble(value, 0.0D, max);
    }

    public static float clampFloat(float value, float max)
    {
        return clampFloat(value, 0.0F, max);
    }

    public static float getFloatE()
    {
        return ((Double) E).floatValue();
    }

    public static float getFloatPi()
    {
        return ((Double) PI).floatValue();
    }

    public static int nextInt(int last, int max)
    {
        return nextInt(last, random, max);
    }

    public static int nextInt(int last, Random random, int max)
    {
        int i = random.nextInt(max);
        return i != last ? i : nextInt(last, random, max);
    }

    public static int nextInt(int last)
    {
        return nextInt(last, random);
    }

    public static int nextInt(int last, Random random)
    {
        int i = random.nextInt();
        return i != last ? i : nextInt(last, random);
    }

    public static int getFibonacciNumber(int n)
    {
        if (n == 1 || n == 2)
        {
            return 1;
        }
        else
        {
            return getFibonacciNumber(n - 1) + getFibonacciNumber(n - 2);
        }
    }
}
