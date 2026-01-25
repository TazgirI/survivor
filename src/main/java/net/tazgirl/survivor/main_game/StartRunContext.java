package net.tazgirl.survivor.main_game;

import net.tazgirl.survivor.main_game.data.seeds.structure.SeedHolder;

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
