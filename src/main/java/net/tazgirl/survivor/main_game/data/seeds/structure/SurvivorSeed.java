package net.tazgirl.survivor.main_game.data.seeds.structure;

import net.minecraft.nbt.CompoundTag;

import java.util.Random;

public class SurvivorSeed
{
    public long seed;
    public int iteration = 0;

    public SurvivorSeed()
    {
        seed = new Random().nextLong();
    }

    public SurvivorSeed(long seed)
    {
        this.seed = seed;
    }

    public SurvivorSeed(long seed, int iteration)
    {
        this.seed = seed;
        this.iteration = iteration;
    }

    public long newSeed()
    {
        long returnValue = seed + iteration;
        increment();
        return returnValue;
    }

    public Random nextRandom()
    {
        Random returnRandom = new Random(seed + iteration);
        increment();
        return returnRandom;
    }

    void increment()
    {
        iteration++;
    }

    public CompoundTag asTag()
    {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putLong("seed", seed);
        compoundTag.putInt("iteration", iteration);
        return compoundTag;
    }

}
