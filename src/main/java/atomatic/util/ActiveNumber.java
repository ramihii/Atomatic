package atomatic.util;

public class ActiveNumber
{
    public static final int MODE_NOTHING = 0;
    public static final int MODE_OSCILLATE = 1;
    public static final int MODE_OSCILLATE_SMOOTH = 2;
    public static final int MODE_KEEP_WITHIN_BOUNDS = 3;

    public static final int DIR_INC = 4;
    public static final int DIR_DEC = 5;
    public static final int DIR_STATIC = 6;

    private double min_osc_value = 0.001d;

    private boolean isStatic = false;
    private boolean increase = true;
    private double actValue;
    private int mode;
    private double bounds_min;
    private double bounds_max;

    public ActiveNumber(int mode)
    {
        this.mode = mode;
    }

    public ActiveNumber(int mode, double initialValue)
    {
        this(mode);
        this.actValue = initialValue;
    }

    public ActiveNumber setBounds(double min, double max)
    {
        this.bounds_max = max;
        this.bounds_min = min;
        return this;
    }

    public ActiveNumber setDirection(int dir)
    {
        if (dir == DIR_DEC)
        {
            this.increase = false;
            return this;
        }

        if (dir == DIR_INC)
        {
            this.increase = true;
            return this;
        }

        this.isStatic = true;

        return this;

    }

    public double update(double value)
    {
        if (this.isStatic)
        {
            return this.actValue;
        }

        switch (this.mode)
        {
            case MODE_OSCILLATE:
            {
                if (this.increase)
                {
                    this.actValue = MathHelper.clampDouble(this.actValue + Math.abs(value), this.bounds_min, this.bounds_max);
                }
                else
                {
                    this.actValue = MathHelper.clampDouble(this.actValue - Math.abs(value), this.bounds_min, this.bounds_max);
                }

                if (this.actValue <= this.bounds_min + 0.0001d)
                { // Small marginal because of the nature of non-whole numbers
                    this.increase = true;
                }
                if (this.actValue >= this.bounds_max - 0.0001d)
                { // Small marginal because of the nature of non-whole numbers
                    this.increase = false;
                }
                break;
            }
            case MODE_OSCILLATE_SMOOTH:
                {
                    double delta = MathHelper.clampDouble(value * (Math.toDegrees(Math.atan(distanceToBounds(this.actValue) / (distBetweenBounds() / 2))) / 45d), this.min_osc_value, value);
                    if (this.increase)
                    {
                        this.actValue = MathHelper.clampDouble(this.actValue + delta, this.bounds_min - 0.05d, this.bounds_max + 0.05d);
                    }
                    else
                    {
                        this.actValue = MathHelper.clampDouble(this.actValue - delta, this.bounds_min - 0.05d, this.bounds_max + 0.05d);
                    }
                    //			System.out.println("Delta: " + delta);
                    //			System.out.println("Angs: " + (Math.toDegrees(Math.atan(distanceToBounds(this.actValue)/(distBetweenBounds()/2))))/45d);
                    if (this.actValue <= this.bounds_min + 0.05d)
                    { // Small marginal because of the nature of non-whole numbers
                        this.increase = true;
                    }
                    if (this.actValue >= this.bounds_max - 0.05d)
                    { // Small marginal because of the nature of non-whole numbers
                        this.increase = false;
                    }
                    break;
                }
            case MODE_KEEP_WITHIN_BOUNDS:
            {
                this.actValue += value;

                while (this.actValue < this.bounds_min || this.actValue > this.bounds_max)
                {
                    if (this.actValue < this.bounds_min)
                    {
                        this.actValue = this.bounds_max + (this.actValue - bounds_min);
                    }
                    else
                    {
                        this.actValue = this.bounds_min + (this.actValue - bounds_max);
                    }
                }
                break;
            }
        }

        return this.actValue;
    }

    private double distanceToBounds(double value)
    {
        return Math.min(Math.abs(value - this.bounds_min), Math.abs(value - this.bounds_max));
    }

    private double distBetweenBounds()
    {
        return Math.abs(this.bounds_max - this.bounds_min);
    }

    public double getValue()
    {
        return this.actValue;
    }
}
