package net.tazgirl.survivor.Inits;

import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.saved_data.SavedDataNames;
import net.tazgirl.survivor.saved_data.mob_spawn_points.MobSpawnsMapSavedData;

public class InitSavedData
{

    public static void OnServerStarting(ServerStartingEvent event)
    {
        Globals.overworldData.computeIfAbsent(new SavedData.Factory<>(MobSpawnsMapSavedData::create, MobSpawnsMapSavedData::load), SavedDataNames.MOB_SPAWN_POINTS.toString());
    }
}
