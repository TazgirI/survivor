package net.tazgirl.survivor.saved_data.mob_spawn_points;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.saved_data.SavedDataNames;

import java.util.HashMap;
import java.util.Map;

public class MobSpawnsMapSavedData extends SavedData
{

    private Map<Vec3, Vec2> mobSpawnsMap = new HashMap<>();

    public static MobSpawnsMapSavedData create()
    {
        return new MobSpawnsMapSavedData();
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider)
    {
        ListTag listTag = new ListTag();


        CompoundTag pairTag;
        Vec3 vec3;
        Vec2 vec2;

        for(Map.Entry<Vec3, Vec2> entry: mobSpawnsMap.entrySet())
        {
            pairTag = new CompoundTag();
            vec3 = entry.getKey();
            vec2 = entry.getValue();

            pairTag.putDouble("3x", vec3.x);
            pairTag.putDouble("3y", vec3.y);
            pairTag.putDouble("3z", vec3.z);

            pairTag.putDouble("2x", vec2.x);
            pairTag.putDouble("2y", vec2.y);

            listTag.add(pairTag);
        }

        tag.put("MobSpawnEntries", listTag);
        return tag;
    }

    public static MobSpawnsMapSavedData load(CompoundTag tag, HolderLookup.Provider lookupProvider)
    {
        MobSpawnsMapSavedData mobSpawnsMapSavedData = new MobSpawnsMapSavedData();

        ListTag listTag = tag.getList("MobSpawnEntries", 10);

        CompoundTag pairTag;

        Vec3 position;
        Vec2 rotation;

        for (int i = 0; i < listTag.size(); i++)
        {
            pairTag = listTag.getCompound(i);

            position = new Vec3(pairTag.getDouble("3x"),pairTag.getDouble("3y"),pairTag.getDouble("3z"));
            rotation = new Vec2((float) pairTag.getDouble("2x"), (float) pairTag.getDouble("2y"));

            mobSpawnsMapSavedData.mobSpawnsMap.put(position, rotation);
        }

        return mobSpawnsMapSavedData;
    }

    public Map<Vec3, Vec2> getMobSpawnsMap()
    {
        return mobSpawnsMap;
    }

    void addSpawnPoint(Vec3 position, Vec2 rotation)
    {
        mobSpawnsMap.put(position, rotation);
        this.setDirty();
    }

    void clearSpawnPoints()
    {
        mobSpawnsMap = new HashMap<>();
        this.setDirty();
    }

    public static MobSpawnsMapSavedData getForOverworld()
    {
        return Globals.overworldData.computeIfAbsent(new SavedData.Factory<>(MobSpawnsMapSavedData::create, MobSpawnsMapSavedData::load), SavedDataNames.MOB_SPAWN_POINTS.toString());
    }


}
