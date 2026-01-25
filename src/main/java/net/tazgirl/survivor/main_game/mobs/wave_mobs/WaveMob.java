package net.tazgirl.survivor.main_game.mobs.wave_mobs;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.tazgirl.magicjson.optionals.numbers.IntegerStatementOptional;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.events.WaveConstructionStartedEvent;
import net.tazgirl.survivor.events.WaveStartedEvent;
import net.tazgirl.survivor.events.wave_setup.WaveSetupEvent;
import net.tazgirl.survivor.main_game.FullContextData;
import net.tazgirl.survivor.main_game.mobs.modifiers.storage.ModifierStorageRecord;
import net.tazgirl.survivor.main_game.mobs.modifiers.storage.ModifierStorageSet;
import net.tazgirl.survivor.main_game.players.PlayerEvents;
import net.tazgirl.survivor.main_game.wave.IncomingMob;
import net.tazgirl.tutilz.admin.Logging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WaveMob<T extends Entity> implements IWaveMob
{
    protected EntityType<T> entityType;

    protected ModifierStorageSet modifiers;
    protected ModifierStorageSet guaranteedModifiers;

    protected IntegerStatementOptional cost;

    protected IntegerStatementOptional weight;

    protected IntegerStatementOptional firstWave = new IntegerStatementOptional(0,0);

    int cheapestModifier;

    protected Map<IntegerStatementOptional, Consumer<FullContextData>> waveTriggersPre = new HashMap<>();
    protected Map<IntegerStatementOptional, Consumer<FullContextData>> waveTriggersPost = new HashMap<>();



    public WaveMob(EntityType<T> entityType, ModifierStorageSet modifiers, IntegerStatementOptional cost, IntegerStatementOptional weight)
    {
        this.entityType = entityType;
        this.modifiers = modifiers;
        this.cost = cost;
        this.weight = weight;

        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST,WaveConstructionStartedEvent.class, this::onWaveConstructionStart);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST,WaveStartedEvent.class, this::onWaveStart);
    }

    public ModifierStorageSet getModifiers()
    {
        return modifiers;
    }

    public EntityType<?> getEntityType()
    {
        return entityType;
    }

    public int getCost()
    {
        return cost.get();
    }

    public int getWeight()
    {
        return weight.get();
    }

    public IntegerStatementOptional getCostRaw()
    {
        return cost;
    }

    public IntegerStatementOptional getWeightRaw()
    {
        return weight;
    }

    public void setCost(IntegerStatementOptional newCost)
    {
        cost = newCost;
    }

    public void setWeight(IntegerStatementOptional newWeight)
    {
        weight = newWeight;
    }

    public void setFirstWave(IntegerStatementOptional firstWave)
    {
        this.firstWave = firstWave;
    }

    public void addModifiers(ModifierStorageSet modifierStorageSet)
    {
        if(modifiers == null)
        {
            modifiers = modifierStorageSet;
        }
        else
        {
            modifiers.addSetOfMobModifiers(modifierStorageSet.getModifiers());
        }
    }

    public void addGuaranteedModifiers(ModifierStorageSet modifierStorageSet)
    {
        if(guaranteedModifiers == null)
        {
            guaranteedModifiers = modifierStorageSet;
        }
        else
        {
            guaranteedModifiers.addSetOfMobModifiers(modifierStorageSet.getModifiers());
        }
    }

    public IncomingMob<T> incomingMob()
    {
        return new IncomingMob<>(this);
    }

    public void onWaveConstructionStart(WaveConstructionStartedEvent event)
    {
        modifiers.enterCacheMode();
    }

    public void onWaveStart(WaveStartedEvent event)
    {
        modifiers.exitCacheMode();
    }

    public CachedWaveMob cacheValues()
    {
        return new CachedWaveMob(this, cost.get(), weight.get());
    }

    @Override
    public String toString()
    {
        return "WaveMob<" + entityType +">( cost: " + cost + " weight: " + weight + " first wave: " + firstWave + " modifiers: " + modifiers.toString() + " guaranteed modifiers: " + guaranteedModifiers.toString() + " )";
    }
}
