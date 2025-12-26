package net.tazgirl.survivor.main_game.mobs;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.events.wave_setup.WaveSetupEvent;
import net.tazgirl.survivor.main_game.FullContextData;
import net.tazgirl.survivor.main_game.mobs.modifiers.ModifierStorageSet;
import net.tazgirl.tutilz.admin.Logging;
import org.jline.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WaveMob<T extends Entity> implements IWaveMob
{
    protected EntityType<T> entityType;

    protected ModifierStorageSet modifiers;

    protected int cost;

    protected int weight;

    protected int firstWave;

    protected Map<Integer, Consumer<FullContextData>> waveTriggersPre = new HashMap<>();
    protected Map<Integer, Consumer<FullContextData>> waveTriggersPost = new HashMap<>();



    public WaveMob(EntityType<T> entityType, ModifierStorageSet modifiers, int cost, int weight, int firstWave)
    {
        if(cost <= 0)
        {
            Logging.Debug("Attempted to set WaveMob cost to 0 or a negative, defaulting to 1", Survivor.LOGGER);
            cost = 1;
        }
        if(weight <= 0)
        {
            Logging.Debug("Attempted to set WaveMob weight to 0 or a negative, defaulting to 1", Survivor.LOGGER);
            weight = 1;
        }

        this.entityType = entityType;
        this.modifiers = modifiers;
        this.cost = cost;
        this.weight = weight;
        this.firstWave = firstWave;
    }

    public ModifierStorageSet getModifiers()
    {
        return modifiers;
    }


    public void WaveSetupPre(WaveSetupEvent.Pre event)
    {

    }


    public void WaveSetupPost(WaveSetupEvent.Post event)
    {

    }

    public EntityType<?> getEntityType()
    {
        return entityType;
    }

    public int getCost()
    {
        return cost;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setCost(int newCost)
    {
        if(newCost <= 0)
        {
            Logging.Error("Attempted to set a WaveMob's cost to 0 or a negative", Survivor.LOGGER);
            return;
        }

        cost = newCost;
    }

    public void setWeight(int newWeight)
    {
        if(newWeight < 0)
        {
            Logging.Error("Attempted to set a WaveMob's weight to a negative", Survivor.LOGGER);
            return;
        }

        weight = newWeight;
    }
}
