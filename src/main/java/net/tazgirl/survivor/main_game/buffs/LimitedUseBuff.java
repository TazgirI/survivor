package net.tazgirl.survivor.main_game.buffs;

import net.tazgirl.survivor.Priority;

public abstract class LimitedUseBuff extends Buff
{
    int uses = 0;
    int maxUses;

    public LimitedUseBuff(Type type, Priority priority, double value, int maxUses)
    {
        super(type, priority, value);
        this.maxUses = maxUses;
    }

    @Override
    public boolean valid(BuffFetchContext context)
    {
        if(!context.display)
        {
            uses++;
        }

        return true;
    }

    @Override
    public boolean expired()
    {
        return uses >= maxUses;
    }
}
