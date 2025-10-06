package net.tazgirl.survivor;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.tazgirl.survivor.saved_data.mob_spawn_points.MobSpawnsMapSavedData;
import org.slf4j.Logger;

import java.util.Map;

public class Globals
{
    static Vec3 spawnPos = new Vec3(0,0,0);

    public static MinecraftServer server = null;

    public static DimensionDataStorage overworldData = null;

    public static Logger logger = Survivor.LOGGER;


    public static void OnServerStarting(ServerStartingEvent event)
    {
        server = event.getServer();

        overworldData = server.overworld().getDataStorage();
    }


    public static Map<Vec3, Vec2> getMobSpawnPoints() {return MobSpawnsMapSavedData.getForOverworld().getMobSpawnsMap();}


}
