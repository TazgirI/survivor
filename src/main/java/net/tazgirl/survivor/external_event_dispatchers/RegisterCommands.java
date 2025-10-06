package net.tazgirl.survivor.external_event_dispatchers;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.events.CollectCommandBranchesEvent;
import net.tazgirl.tutilz.commands.ArgPair;
import net.tazgirl.tutilz.commands.QuickRegisterCommand;

import java.util.Map;

@EventBusSubscriber(modid = Survivor.MODID, bus = EventBusSubscriber.Bus.GAME)
public class RegisterCommands
{
    @SubscribeEvent
    public static void OnRegisterCommands(RegisterCommandsEvent event)
    {
        QuickRegisterCommand.BuildThreeStageArgTree(event.getDispatcher(), "survivor", 4, FireBranchesEvent());
    }

    public static Map<String, ArgPair[]> FireBranchesEvent()
    {
        CollectCommandBranchesEvent branchesEvent = CollectCommandBranchesEvent.FireEvent();

        return branchesEvent.GetBranchesList();
    }
}
