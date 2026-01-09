package net.tazgirl.survivor.main_game.data.seeds;

import net.tazgirl.magicjson.data.Constants;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.main_game.CoreGameData;
import net.tazgirl.survivor.main_game.data.seeds.structure.SeedHolder;
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
