package net.tazgirl.survivor.saved_data.registers.wave_mob;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.saved_data.registers.modifier_group.ModifierGroupRegister;
import net.tazgirl.tutilz.admin.Logging;
import net.tazgirl.magicjson.data.Constants;
import net.tazgirl.magicjson.helpers.InputStreamToJson;
import net.tazgirl.survivor.main_game.mobs.wave_mobs.WaveMob;
import net.tazgirl.survivor.saved_data.registers.modifier.ModifierStorageRecordRegister;
import net.tazgirl.survivor.main_game.mobs.modifiers.storage.ModifierStorageSet;
import net.tazgirl.tutilz.registers.MakeRegistryAddress;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WaveMobDataProcessing
{
    public static String dataPath = "survivor/wave_mob";

    public static void ProcessWaveMobData()
    {
        for(Map.Entry<ResourceLocation, Resource> entry : GetAllMobsData().entrySet())
        {
            WaveMob<?> entryMob = entryToWaveMob(entry);
            if(entryMob != null)
            {
                WaveMobRegister.put(MakeRegistryAddress.withPath(entry.getKey(), dataPath),entryMob);
            }
        }
    }

    // Should be broken into multiple functions that get handed the WaveMob and JsonObject but... cba
    static WaveMob<?> entryToWaveMob(Map.Entry<ResourceLocation, Resource> entry)
    {
        JsonObject jsonObject;

        try (InputStream inputStream = entry.getValue().open())
        {
            jsonObject = Objects.requireNonNull(InputStreamToJson.getJson(inputStream));
        }
        catch (Exception e)
        {
            Logging.Warn(entry.getKey().toString() + " could not be opened, skipping file", Survivor.LOGGER);
            return null;
        }

        // Optionals
        JsonElement modifiersElement = jsonObject.get("modifiers");;
        JsonElement guaranteedModifiersElement = jsonObject.get("guaranteed_modifiers");
        JsonElement firstWaveElement = jsonObject.get("first_wave");;

        String entityTypeLocation;
        List<String> modifierStorageAddresses = new ArrayList<>();
        List<String> guaranteedModifierStorageAddresses = new ArrayList<>();
        int cost;
        int weight;
        int firstWave = 0;
        try
        {
            // Must exist
            entityTypeLocation = jsonObject.get("entity_type").getAsString();
            cost = jsonObject.get("cost").getAsInt();
            weight = jsonObject.get("weight").getAsInt();
        }
        catch (IllegalStateException | NullPointerException e)
        {
            Logging.Warn("JsonObject \"" + entry.getKey() + "\" failed to process. Please verify all expected values are present and in an acceptable form even if empty", Survivor.LOGGER);
            return null;
        }

        try
        {
            if(modifiersElement instanceof JsonArray array)
            {
                array.forEach(element ->
                {
                    String elementString = element.getAsString();

                    if(ModifierStorageRecordRegister.hasAddress(elementString))
                    {
                        modifierStorageAddresses.add(elementString);
                    }
                    else if(ModifierGroupRegister.hasAddress(elementString))
                    {
                        modifierStorageAddresses.addAll(ModifierGroupRegister.get(elementString));
                    }


                });
            }
        }
        catch (Exception ignored){}

        try
        {
            if(guaranteedModifiersElement instanceof JsonArray array)
            {
                array.forEach(element -> guaranteedModifierStorageAddresses.add(element.getAsString()));
            }
        }
        catch (Exception ignored){}

        try
        {
            if(firstWaveElement instanceof JsonPrimitive primitive)
            {
                firstWave = primitive.getAsInt();
            }
        }
        catch (Exception ignored){}


        EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(entityTypeLocation));

        ModifierStorageSet storageSet = new ModifierStorageSet(ModifierStorageRecordRegister.getList(modifierStorageAddresses));
        ModifierStorageSet guaranteedStorageSet = new ModifierStorageSet(ModifierStorageRecordRegister.getList(guaranteedModifierStorageAddresses));

        WaveMob<?> waveMob = new WaveMob<>(entityType, storageSet, cost, weight);

        waveMob.setFirstWave(firstWave);

        waveMob.addModifiers(storageSet);
        waveMob.addGuaranteedModifiers(guaranteedStorageSet);

        return waveMob;
    }

    public static Map<ResourceLocation, Resource> GetAllMobsData()
    {
        ResourceManager resourceManager = Constants.server.getResourceManager();

        return resourceManager.listResources(dataPath, path -> path.getPath().endsWith(".json"));
    }
}
