package net.tazgirl.survivor.wave.wave_constructor;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.tazgirl.survivor.InternalMagicValues;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.events.WaveConstructionStartedEvent;
import net.tazgirl.survivor.misc_game_stuff.CoreGameData;
import net.tazgirl.survivor.mobs.ActiveMob;
import net.tazgirl.survivor.mobs.modifiers.storage.CachedModifierStorageRecord;
import net.tazgirl.survivor.mobs.wave_mobs.CachedWaveMob;
import net.tazgirl.survivor.mobs.wave_mobs.WaveMobActiveSet;
import net.tazgirl.survivor.mobs.wave_mobs.WaveMobStorageSet;
import net.tazgirl.survivor.wave.IncomingMob;
import net.tazgirl.survivor.wave.Wave;
import net.tazgirl.tutilz.admin.Logging;

import java.util.*;

public class WaveConstructor
{
    // TODO: Split over 10 second minimum break period
    // Do the mob loop once a second until 1/5th of total mob points has been used
    // Repeat for modifiers
    // Objectify and schedule through Tutilz
    public static Wave constructWave(int mobPoints, int modifierPoints, WaveMobStorageSet set)
    {
        WaveConstructionStartedEvent.postEvent();

        WaveMobActiveSet activeSet = set.construct();

        List<IncomingMob<?>> incomingMobs = new ArrayList<>();

        while(mobPoints > 0 && activeSet.cachedValues.getFirst().cost() <= mobPoints)
        {
            CachedWaveMob cachedWaveMob = activeSet.randomAffordableMobByWeight(mobPoints);

            if(cachedWaveMob.cost() <= mobPoints)
            {
                incomingMobs.add(cachedWaveMob.waveMob().incomingMob());

                mobPoints -= cachedWaveMob.cost();
            }
            else
            {
                Logging.Debug("Fetching an affordable cached wave mob returned something unaffordable: " + cachedWaveMob, Survivor.LOGGER);
            }

        }

        Map<IncomingMob<?>, Integer> affordableModifiers = new HashMap<>();



        for(IncomingMob<?> incomingMob : incomingMobs)
        {
            int cheapest = incomingMob.waveMob.getModifiers().cheapestCachedCost();
            if(cheapest != -1)
            {
                affordableModifiers.put(incomingMob, cheapest);
            }
        }

        Random seededRandom = new Random(CoreGameData.seedHolder.waveSeed.newSeed());
        int fails = 0;

        List<Map.Entry<IncomingMob<?>, Integer>> affordable = new ArrayList<>();

        while(modifierPoints > 0)
        {
            affordable.clear();

            for(Map.Entry<IncomingMob<?>, Integer> entry : affordableModifiers.entrySet())
            {
                if(entry.getValue() <= modifierPoints)
                {
                    affordable.add(entry);
                }
            }

            if(affordable.isEmpty() || fails >= InternalMagicValues.modifierConstraint)
            {
                break;
            }

            IncomingMob<?> chosen = affordable.get(seededRandom.nextInt(0, affordable.size())).getKey();

            if(chosen == null)
            {
                fails++;
                continue;
            }

            CachedModifierStorageRecord cachedModifier = chosen.waveMob.getModifiers().getInBudgetRandomByWeight(modifierPoints);

            if(cachedModifier == null)
            {
                fails++;
                continue;
            }

            chosen.modifierStorageSet.addMobModifier(cachedModifier.modifierStorageRecord());

            modifierPoints -= cachedModifier.cost();

            fails = 0;
        }

        LinkedHashMap<LivingEntity, ActiveMob<?>> activeMobs = new LinkedHashMap<>();



        for(IncomingMob<?> incomingMob : incomingMobs)
        {
            ActiveMob<?> activeMob = incomingMob.constructActive();

            activeMobs.put(activeMob.entity, activeMob);
        }

        return new Wave(activeMobs);
    }
}
