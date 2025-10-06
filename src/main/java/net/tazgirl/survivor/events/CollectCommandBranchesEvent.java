package net.tazgirl.survivor.events;

import net.neoforged.bus.EventBus;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.tazgirl.tutilz.commands.ArgPair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectCommandBranchesEvent extends Event
{
    private Map<String, ArgPair[]> branchesMap = new HashMap<>();

    public CollectCommandBranchesEvent()
    {

    }

    public void AddBranch(String branchName, ArgPair[] branchFuncs)
    {
        branchesMap.put(branchName, branchFuncs);
    }

    public Map<String, ArgPair[]> GetBranchesList()
    {
        return branchesMap.isEmpty() ? null: branchesMap;
    }

    public static CollectCommandBranchesEvent FireEvent()
    {
        CollectCommandBranchesEvent newEvent = new CollectCommandBranchesEvent();

        NeoForge.EVENT_BUS.post(newEvent);

        return newEvent;

    }
}
