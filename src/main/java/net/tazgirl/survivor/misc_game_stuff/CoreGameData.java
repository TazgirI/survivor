package net.tazgirl.survivor.misc_game_stuff;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.tazgirl.survivor.data.seeds.structure.SeedHolder;
import net.tazgirl.survivor.wave.Wave;
import net.tazgirl.survivor.saved_data.interactive_positions.InteractivePositionsSavedData;
import net.tazgirl.survivor.saved_data.mob_spawn_points.MobSpawnsMapSavedData;

import java.util.Map;

@EventBusSubscriber
public class CoreGameData
{
    public static SeedHolder seedHolder = null;

    public static Wave currentWave = null;
    public static int waveNum = 0;

    public static long zeroedTicks = 0L;

    public static Map<Vec3, Vec2> getMobSpawnPoints() {return MobSpawnsMapSavedData.getForOverworld().getMobSpawnsMap();}
    public static Map<BlockPos, String> getInteractionPositions() {return InteractivePositionsSavedData.getForOverworld().getInteractionPositionsMap();}


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onServerTick(ServerTickEvent.Pre event)
    {
        zeroedTicks++;
    }

    @SubscribeEvent
    public static void onServerStop(ServerStoppedEvent event)
    {
        zeroedTicks = 0L;
    }
}
