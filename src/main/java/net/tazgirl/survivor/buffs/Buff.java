package net.tazgirl.survivor.buffs;

import net.tazgirl.survivor.Priority;

public abstract class Buff
{
    public double value;
    public Type type;
    public Modifier modifier;
    public Priority priority;

    public abstract boolean valid(BuffFetchContext context);
    public abstract boolean expired();

    public Buff(Type type, Priority priority, double value)
    {
        this.type = type;
        this.priority = priority;
        this.value = value;
    }

    public enum Type
    {
        ARMOUR,
        THREAT
    }

    public enum Modifier
    {
        RAW,
        PERCENTILE,
        ORIGINAL_PERCENTILE
    }
}
