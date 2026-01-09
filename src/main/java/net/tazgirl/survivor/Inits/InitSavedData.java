package net.tazgirl.survivor.Inits;

import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.saved_data.SavedDataNames;
import net.tazgirl.survivor.saved_data.mob_spawn_points.MobSpawnsMapSavedData;
import net.tazgirl.survivor.saved_data.seeds.SurvivorSeedHolderSavedData;
import net.tazgirl.survivor.saved_data.seeds.SurvivorSeedsEnum;

public class InitSavedData
{

    public static void Init()
    {
        Globals.overworldData.computeIfAbsent(new SavedData.Factory<>(MobSpawnsMapSavedData::new, MobSpawnsMapSavedData::load), SavedDataNames.MOB_SPAWN_POINTS.toString());
        SurvivorSeedHolderSavedData.getForOverworld();

    }
}
