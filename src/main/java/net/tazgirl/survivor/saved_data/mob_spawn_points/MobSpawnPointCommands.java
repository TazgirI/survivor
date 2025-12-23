package net.tazgirl.survivor.saved_data.mob_spawn_points;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.events.CollectCommandBranchesEvent;
import net.tazgirl.tutilz.Tutilz;
import net.tazgirl.tutilz.commands.ArgPair;
import net.tazgirl.tutilz.commands.QuickRegisterCommand;

import java.util.List;

@EventBusSubscriber(modid = Survivor.MODID)
public class MobSpawnPointCommands
{
    final static String branchName = "mobSpawnPoints";

    static final ArgPair[] argPairs = List.of(
            new ArgPair("saveMobSpawnPoints", ManageMobSpawnPoints::Save),
            new ArgPair("clearMobSpawnPoints", ManageMobSpawnPoints::Clear),
            new ArgPair("visualiseMobSpawnPoints", ManageMobSpawnPoints::Show),
            new ArgPair("visualiseAndClearMobSpawnPoints", ManageMobSpawnPoints::ShowAndClear)
    ).toArray(new ArgPair[0]);

    @SubscribeEvent
    public static void OnRegisterCommands(CollectCommandBranchesEvent event)
    {
        event.AddBranch(branchName, argPairs);
    }
}
