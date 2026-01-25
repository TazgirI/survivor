package net.tazgirl.survivor.main_game.relics;

import net.tazgirl.survivor.Priority;

public class RelicProperties
{
    RelicTrigger trigger = RelicTrigger.ON_WAVE_START;

    Priority priority = Priority.NORMAL;


    public RelicProperties trigger(RelicTrigger trigger)
    {
        this.trigger = trigger;
        return this;
    }

    public RelicProperties priority(Priority priority)
    {
        this.priority = priority;
        return this;
    }


}
