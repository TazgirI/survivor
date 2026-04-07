package net.tazgirl.survivor.data.seeds.structure;

import net.tazgirl.survivor.Survivor;
import net.tazgirl.tutilz.admin.Logging;

import javax.annotation.ParametersAreNullableByDefault;
import java.util.Map;
import java.util.Random;

public class SeedHolder
{
    SurvivorSeed overallSeed;

    public SurvivorSeed generalSeed;
    public SurvivorSeed waveSeed;
    public SurvivorSeed itemSeed;
    public SurvivorSeed modifierSeed;

    public Map<String, SurvivorSeed> allSeeds;

    public SeedHolder(Long seed)
    {
        if(seed == Long.MIN_VALUE)
        {
            seed = new Random().nextLong();
        }
        overallSeed = new SurvivorSeed(seed);
        Logging.Log("New overallSeed is: " + overallSeed.seed, Survivor.LOGGER);
        fillSeeds();
    }

    @ParametersAreNullableByDefault
    public SeedHolder(long overallSeed, long generalSeed, long waveSeed, long itemSeed, long modifierSeed)
    {
        this.overallSeed = new SurvivorSeed(overallSeed);

        this.generalSeed = new SurvivorSeed(generalSeed);
        this.waveSeed = new SurvivorSeed(waveSeed);
        this.itemSeed = new SurvivorSeed(itemSeed);
        this.modifierSeed = new SurvivorSeed(modifierSeed);
    }

    public SeedHolder(Map<String, SurvivorSeed> map)
    {
        this.overallSeed = map.get("overallSeed");

        this.generalSeed = map.get("generalSeed");
        this.waveSeed = map.get("waveSeed");
        this.itemSeed = map.get("itemSeed");
        this.modifierSeed = map.get("modifierSeed");

        fillSeeds();
    }

    private void fillSeeds()
    {
        // Always increment even if unused so if you provide an overall, general and wave seed, itemSeed will still receive the third seed in overallSeed as it would if there was no provided general and wave seed
        long nextSeed = overallSeed.newSeed();

        if(generalSeed == null)
        {
            generalSeed = new SurvivorSeed(nextSeed);
        }

        nextSeed = overallSeed.newSeed();
        if(waveSeed == null)
        {
            waveSeed = new SurvivorSeed(nextSeed);
        }

        nextSeed = overallSeed.newSeed();
        if (itemSeed == null)
        {
            itemSeed = new SurvivorSeed(nextSeed);
        }

        nextSeed = overallSeed.newSeed();
        if(modifierSeed == null)
        {
            modifierSeed = new SurvivorSeed(nextSeed);
        }

        allSeeds = Map.of(
                "overallSeed", overallSeed,
                "generalSeed", generalSeed,
                "waveSeed", waveSeed,
                "itemSeed", itemSeed,
                "modifierSeed", modifierSeed
        );
    }

    public static SeedHolder newRandom()
    {
        return new SeedHolder(Long.MIN_VALUE);
    }
}
