package net.tazgirl.survivor.boons;

import net.tazgirl.survivor.Priority;

public class BoonProperties
{
    BoonTrigger trigger = BoonTrigger.ON_WAVE_START;

    Priority priority = Priority.NORMAL;


    public BoonProperties trigger(BoonTrigger trigger)
    {
        this.trigger = trigger;
        return this;
    }

    public BoonProperties priority(Priority priority)
    {
        this.priority = priority;
        return this;
    }


}
