package net.tazgirl.survivor.misc_game_stuff;

import net.tazgirl.survivor.data.seeds.structure.SeedHolder;

public class StartRunContext
{
    public SeedHolder preppedSeeds;

    public StartRunContext(SeedHolder preppedSeeds)
    {
        if(preppedSeeds == null)
        {
            preppedSeeds = new SeedHolder(Long.MIN_VALUE);
        }

        this.preppedSeeds = preppedSeeds;
    }
}
