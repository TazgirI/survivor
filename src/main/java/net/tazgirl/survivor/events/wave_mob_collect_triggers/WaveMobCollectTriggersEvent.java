package net.tazgirl.survivor.events.wave_mob_collect_triggers;

import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;
import net.tazgirl.survivor.main_game.FullContextData;
import net.tazgirl.survivor.main_game.mobs.wave_mobs.IWaveMob;
import net.tazgirl.survivor.main_game.mobs.wave_mobs.WaveMob;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WaveMobCollectTriggersEvent extends Event
{
    public enum EventType
    {
        WAVE_SETUP_PRE,
        WAVE_SETUP_POST
    }

    protected IWaveMob constructedMobInterface;

    protected Map<Integer, Consumer<FullContextData>> waveTriggersPost = new HashMap<>();
    protected Map<Integer, Consumer<FullContextData>> waveTriggersPre = new HashMap<>();


    Map<EventType, Map<Integer, Consumer<FullContextData>>> assigners = Map.of(
            EventType.WAVE_SETUP_POST, waveTriggersPost,
            EventType.WAVE_SETUP_PRE, waveTriggersPre);

    protected WaveMobCollectTriggersEvent(WaveMob<?> constructedMob)
    {
        this.constructedMobInterface = constructedMob;
    }

    public IWaveMob getWaveMobInterface()
    {
        return constructedMobInterface;
    }

    public void addWaveMobTrigger(int triggerWave, Consumer<FullContextData> function, EventType type)
    {
        assigners.get(type).put(triggerWave, function);
    }

    public Map<Integer, Consumer<FullContextData>> getWaveTriggers(EventType type)
    {
        return assigners.get(type);
    }

    protected static WaveMobCollectTriggersEvent Fire(WaveMob<?> waveMob)
    {
        return NeoForge.EVENT_BUS.post(new WaveMobCollectTriggersEvent(waveMob));
    }

    // Sends event
    // Accepts Map<Integer, consumer> pairs and enum
    // Uses enum to place the consumer in the correct List within WaveMob
    // TODO: How to handle during wave events?




}
