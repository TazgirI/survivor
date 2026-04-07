package net.tazgirl.survivor.mobs.wave_mobs;

import net.tazgirl.survivor.misc_game_stuff.CoreGameData;
import net.tazgirl.survivor.saved_data.registers.mobs.wave_mob.RegisterWaveMob;

import java.util.*;

public class WaveMobActiveSet
{
    int totalWeight;
    public final List<CachedWaveMob> cachedValues = new ArrayList<>();

    public WaveMobActiveSet(List<WaveMob<?>> waveMobs)
    {
        removeInactive();
        sort();

        for(WaveMob<?> waveMob : waveMobs)
        {
            cachedValues.add(waveMob.cacheValues());
        }

        totalWeight = cachedValues.stream().mapToInt(CachedWaveMob::weight).sum();
    }

    public static WaveMobActiveSet fromAddressList(List<String> addresses)
    {
        List<WaveMob<?>> mobs = new ArrayList<>(RegisterWaveMob.getList(addresses));
        return new WaveMobActiveSet(mobs);
    }



    public void sort()
    {
        cachedValues.sort(Comparator.comparingInt(CachedWaveMob::cost).reversed());
    }

    public void removeInactive()
    {
        cachedValues.removeIf(cache -> cache.waveMob().firstWave.get() > CoreGameData.waveNum);
    }

    public CachedWaveMob randomAffordableMobByWeight(int budget)
    {
        return randomWaveMobByWeight(affordableMobs(budget));
    }

    public List<CachedWaveMob> affordableMobs(int budget)
    {

        if(cachedValues.getLast().cost() <= budget)
        {
            return cachedValues;
        }

        List<CachedWaveMob> returnList = new ArrayList<>();

        for (CachedWaveMob cache : cachedValues)
        {
            if (cache.cost() <= budget)
            {
                returnList.add(cache);
            }
        }

        return returnList;
    }

    public CachedWaveMob randomWaveMobByWeight(List<CachedWaveMob> cache)
    {
        Random random = new Random(CoreGameData.seedHolder.waveSeed.newSeed());
        int index = random.nextInt(0, totalWeight + 1);
        int total = 0;

        for(CachedWaveMob waveMob : cache)
        {
            total += waveMob.weight();

            if(total >= index)
            {
                return waveMob;
            }
        }

        return cache.get(random.nextInt(0, cache.size()));
    }

}
