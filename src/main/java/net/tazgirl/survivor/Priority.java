package net.tazgirl.survivor;

import net.minecraft.world.entity.projectile.windcharge.BreezeWindCharge;

public enum Priority
{
    MINIMUM(0),
    VERY_LOW(1),
    LOW(2),
    NORMAL(3),
    HIGH(4),
    VERY_HIGH(5),
    MAXIMUM(6);

    public final int value;

    Priority(int value)
    {
        this.value = value;
    }

    public static Priority fromInt(int num)
    {
        if(num < 0 || num > 6)
        {
            SurvivorLogging.Debug("Attempted to construct a priority from a value outside of 0-6 inclusive, defaulting to NORMAL (3)");
            return NORMAL;
        }

        return Priority.values()[num];
    }
}