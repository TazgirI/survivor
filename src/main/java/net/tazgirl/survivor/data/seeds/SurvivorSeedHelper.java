package net.tazgirl.survivor.data.seeds;

import net.tazgirl.survivor.data.seeds.structure.SeedHolder;
import net.tazgirl.survivor.saved_data.seeds.SurvivorSeedHolderSavedData;

public class SurvivorSeedHelper
{
    public static boolean hasSavedSeeds()
    {
        return SurvivorSeedHolderSavedData.getForOverworld().seedHolder != null;
    }

    public static SeedHolder getSavedSeeds()
    {
        return SurvivorSeedHolderSavedData.getForOverworld().seedHolder;
    }
}
