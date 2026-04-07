package net.tazgirl.survivor.saved_data.mob_spawn_points;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.events.CollectCommandBranchesEvent;
import net.tazgirl.tutilz.Tutilz;
import net.tazgirl.tutilz.commands.ArgBranch;
import net.tazgirl.tutilz.commands.ArgPair;
import net.tazgirl.tutilz.commands.QuickRegisterCommand;

import java.util.List;

@EventBusSubscriber(modid = Survivor.MODID)
public class MobSpawnPointCommands
{
    final static String branchName = "mobSpawnPoints";

    static final ArgBranch[] argPairs = List.of(
            ArgBranch.New("saveMobSpawnPoints", ManageMobSpawnPoints::Save),
            ArgBranch.New("clearMobSpawnPoints", ManageMobSpawnPoints::Clear),
            ArgBranch.New("visualiseMobSpawnPoints", ManageMobSpawnPoints::Show),
            ArgBranch.New("visualiseAndClearMobSpawnPoints", ManageMobSpawnPoints::ShowAndClear)
    ).toArray(new ArgBranch[0]);

    @SubscribeEvent
    public static void OnRegisterCommands(CollectCommandBranchesEvent event)
    {
        event.AddBranch(branchName, argPairs);
    }
}
