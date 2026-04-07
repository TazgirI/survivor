package net.tazgirl.survivor.init;

import net.minecraft.world.level.saveddata.SavedData;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.saved_data.SavedDataNames;
import net.tazgirl.survivor.saved_data.interactive_positions.InteractivePositionsSavedData;
import net.tazgirl.survivor.saved_data.mob_spawn_points.MobSpawnsMapSavedData;
import net.tazgirl.survivor.saved_data.seeds.SurvivorSeedHolderSavedData;

public class InitSavedData
{

    public static void Init()
    {
        Globals.overworldData.computeIfAbsent(new SavedData.Factory<>(MobSpawnsMapSavedData::new, MobSpawnsMapSavedData::load), SavedDataNames.MOB_SPAWN_POINTS.toString());
        SurvivorSeedHolderSavedData.getForOverworld();
        InteractivePositionsSavedData.getForOverworld();
    }
}
