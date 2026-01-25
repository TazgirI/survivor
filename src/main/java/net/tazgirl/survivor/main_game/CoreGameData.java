package net.tazgirl.survivor.main_game;

import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.tazgirl.survivor.InternalMagicValues;
import net.tazgirl.survivor.main_game.data.seeds.structure.SeedHolder;
import net.tazgirl.survivor.main_game.wave.Wave;
import net.tazgirl.survivor.saved_data.mob_spawn_points.MobSpawnsMapSavedData;

import java.util.Map;

public class CoreGameData
{
    public static SeedHolder seedHolder = null;

    public static Wave currentWave = null;
    public static int waveNum = 0;

    public static Long zeroedTicks = 0L;

    public static Map<Vec3, Vec2> getMobSpawnPoints() {return MobSpawnsMapSavedData.getForOverworld().getMobSpawnsMap();}

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onServerTick(ServerTickEvent event)
    {
        zeroedTicks++;
    }

    @SubscribeEvent
    public static void onServerStop(ServerStoppedEvent event)
    {
        zeroedTicks = 0L;
    }
}
