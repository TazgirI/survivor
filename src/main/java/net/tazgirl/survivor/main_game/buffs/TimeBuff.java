package net.tazgirl.survivor.main_game.buffs;

import net.tazgirl.survivor.Priority;
import net.tazgirl.survivor.main_game.CoreGameData;

public abstract class TimeBuff extends Buff
{
    Long tickWhenApplied;
    int duration;


    public TimeBuff(Type type, Priority priority, int duration, double value)
    {
        super(type, priority, value);

        this.tickWhenApplied = CoreGameData.zeroedTicks;
        this.duration = duration;
    }

    @Override
    public boolean expired()
    {
        // i.e. applied on tick 20, with a duration of 10 and current tick is 31: 31 - 20 (11) > 10
        return CoreGameData.zeroedTicks - tickWhenApplied > duration;
    }
}
