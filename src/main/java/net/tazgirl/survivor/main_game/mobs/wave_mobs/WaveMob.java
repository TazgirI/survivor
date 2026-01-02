package net.tazgirl.survivor.main_game.mobs.wave_mobs;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.tazgirl.magicjson.optionals.numbers.IntegerStatementOptional;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.events.wave_setup.WaveSetupEvent;
import net.tazgirl.survivor.main_game.FullContextData;
import net.tazgirl.survivor.main_game.mobs.modifiers.storage.ModifierStorageSet;
import net.tazgirl.tutilz.admin.Logging;

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

    protected Map<IntegerStatementOptional, Consumer<FullContextData>> waveTriggersPre = new HashMap<>();
    protected Map<IntegerStatementOptional, Consumer<FullContextData>> waveTriggersPost = new HashMap<>();



    public WaveMob(EntityType<T> entityType, ModifierStorageSet modifiers, IntegerStatementOptional cost, IntegerStatementOptional weight)
    {
        this.entityType = entityType;
        this.modifiers = modifiers;
        this.cost = cost;
        this.weight = weight;
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

    @Override
    public String toString()
    {
        return "WaveMob<" + entityType +">( cost: " + cost + " weight: " + weight + " first wave: " + firstWave + " modifiers: " + modifiers.toString() + " guaranteed modifiers: " + guaranteedModifiers.toString() + " )";
    }
}
