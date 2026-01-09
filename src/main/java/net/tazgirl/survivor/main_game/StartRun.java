package net.tazgirl.survivor.main_game;

import net.tazgirl.survivor.main_game.data.seeds.structure.SeedHolder;

import java.util.Objects;

public class StartRun
{
    public static void start(StartRunContext context)
    {
        CoreGameData.seedHolder = Objects.requireNonNullElseGet(context.preppedSeeds, SeedHolder::new);
    }
}
