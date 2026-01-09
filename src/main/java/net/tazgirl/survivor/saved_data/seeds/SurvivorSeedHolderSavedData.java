package net.tazgirl.survivor.saved_data.seeds;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.main_game.CoreGameData;
import net.tazgirl.survivor.main_game.data.seeds.SurvivorSeedHelper;
import net.tazgirl.survivor.main_game.data.seeds.structure.SeedHolder;
import net.tazgirl.survivor.main_game.data.seeds.structure.SurvivorSeed;
import net.tazgirl.survivor.saved_data.CoreTagNames;
import net.tazgirl.survivor.saved_data.mob_spawn_points.MobSpawnsMapSavedData;

import java.util.HashMap;
import java.util.Map;

public class SurvivorSeedHolderSavedData extends SavedData
{

    public SeedHolder seedHolder;

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider)
    {
        ListTag listTag = new ListTag();
        for(Map.Entry<String, SurvivorSeed> entry: CoreGameData.seedHolder.allSeeds.entrySet())
        {
            CompoundTag tempCompound = new CompoundTag();
            tempCompound.put(entry.getKey(), entry.getValue().asTag());
            listTag.add(tempCompound);
        }

        compoundTag.put(SurvivorSeedsEnum.survivorSeeds.toString(), listTag);

        return compoundTag;
    }

    public static SurvivorSeedHolderSavedData load(CompoundTag tag, HolderLookup.Provider lookupProvider)
    {
        SurvivorSeedHolderSavedData savedData = new SurvivorSeedHolderSavedData();
        if(!tag.getBoolean(CoreTagNames.isInGame.toString()))
        {
            savedData.seedHolder = null;
            return savedData;
        }


        Tag listTag = tag.get(SurvivorSeedsEnum.survivorSeeds.toString());

        if(!(listTag instanceof ListTag))
        {
            return null;
        }

        Map<String, SurvivorSeed> seedTags = new HashMap<>();

        ((ListTag) listTag).forEach(entry ->
        {
            CompoundTag compound = (CompoundTag) entry;
            String key = compound.getAllKeys().iterator().next();
            compound = compound.getCompound(key);

            seedTags.put(key, new SurvivorSeed(compound.getLong("seed"), compound.getInt("iteration")));
        });

        savedData.seedHolder = new SeedHolder(seedTags);

        return savedData;
    }

    public static SurvivorSeedHolderSavedData getForOverworld()
    {
        return Globals.overworldData.computeIfAbsent(new SavedData.Factory<>(SurvivorSeedHolderSavedData::new, SurvivorSeedHolderSavedData::load), SurvivorSeedsEnum.survivorSeeds.toString());
    }
}
