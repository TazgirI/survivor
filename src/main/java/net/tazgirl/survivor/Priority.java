package net.tazgirl.survivor;

public enum Priority
{
    MINIMUM(10),
    VERY_LOW(7),
    LOW(6),
    NORMAL(5),
    HIGH(4),
    VERY_HIGH(3),
    MAXIMUM(0);

    public final int value;

    Priority(int value)
    {
        this.value = value;
    }
}