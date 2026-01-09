package net.tazgirl.survivor.main_game.data.seeds.structure;

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

    public Map<String, SurvivorSeed> allSeeds;

    public SeedHolder()
    {
        new SeedHolder(new Random().nextLong());
    }

    public SeedHolder(long seed)
    {
        overallSeed = new SurvivorSeed(seed);
        Logging.Log("New overallSeed is: " + overallSeed.seed, Survivor.LOGGER);
        fillSeeds();
    }

    @ParametersAreNullableByDefault
    public SeedHolder(long overallSeed, long generalSeed, long waveSeed, long itemSeed)
    {
        this.overallSeed = new SurvivorSeed(overallSeed);

        this.generalSeed = new SurvivorSeed(generalSeed);
        this.waveSeed = new SurvivorSeed(waveSeed);
        this.itemSeed = new SurvivorSeed(itemSeed);
    }

    public SeedHolder(Map<String, SurvivorSeed> map)
    {
        this.overallSeed = map.get("overallSeed");

        this.generalSeed = map.get("generalSeed");
        this.waveSeed = map.get("waveSeed");
        this.itemSeed = map.get("itemSeed");

        fillSeeds();
    }

    private void fillSeeds()
    {
        if(generalSeed == null)
        {
            generalSeed = new SurvivorSeed(overallSeed.newSeed());
        }
        if(waveSeed == null)
        {
            waveSeed = new SurvivorSeed(overallSeed.newSeed());
        }
        if (itemSeed == null)
        {
            itemSeed = new SurvivorSeed(overallSeed.newSeed());
        }

        allSeeds = Map.of(
                "overallSeed", overallSeed,
                "generalSeed", generalSeed,
                "waveSeed", waveSeed,
                "itemSeed", itemSeed
        );
    }

}
