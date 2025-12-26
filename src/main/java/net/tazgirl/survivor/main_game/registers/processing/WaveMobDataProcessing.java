package net.tazgirl.survivor.main_game.registers.processing;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.tazgirl.magicjson.Logging;
import net.tazgirl.magicjson.data.Constants;
import net.tazgirl.magicjson.helpers.InputStreamToJson;
import net.tazgirl.survivor.main_game.mobs.WaveMob;
import net.tazgirl.survivor.main_game.registers.MobModifierStorageRecordRegister;
import net.tazgirl.survivor.main_game.mobs.modifiers.ModifierStorageSet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WaveMobDataProcessing
{
    public static void ProcessWaveMobData(ServerStartingEvent event)
    {
        for(Map.Entry<ResourceLocation, Resource> entry : GetAllMobsData().entrySet())
        {
            try(InputStream inputStream = entry.getValue().open())
            {

            }
            catch (IOException e)
            {
                Logging.Warn(entry.getKey().toString() + " could not be opened, skipping file");
                continue;
            }
        }
    }

    static WaveMob<?> entryToWaveMob(Map.Entry<ResourceLocation, Resource> entry)
    {

        JsonObject jsonObject;

        try (InputStream inputStream = entry.getValue().open())
        {
            jsonObject = InputStreamToJson.getJson(inputStream);
        }
        catch (IOException exit)
        {
            Logging.Warn("Could not process WaveMob .json: " + entry.getKey());
            return null;
        }

        if (jsonObject == null)
        {
            return null;
        }

        String entityTypeLocation;
        List<String> modifierStorageAddresses = new ArrayList<>();
        int cost;
        int weight;
        int firstWave;
        try
        {
            entityTypeLocation = jsonObject.get("entity_type").getAsString();

            jsonObject.get("modifiers").getAsJsonArray().forEach(element -> modifierStorageAddresses.add(element.getAsString()));

            cost = jsonObject.get("cost").getAsInt();
            weight = jsonObject.get("weight").getAsInt();
            firstWave = jsonObject.get("firstWave").getAsInt();
        }
        catch (IllegalStateException | NullPointerException e)
        {
            Logging.Warn("JsonObject \"" + entry.getKey() + "\" failed to process. Please verify all expected values are present and in an acceptable form even if empty");
            return null;
        }

        EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(entityTypeLocation));

        ModifierStorageSet storageSet = new ModifierStorageSet(MobModifierStorageRecordRegister.getList(modifierStorageAddresses));

        return new WaveMob<>(entityType, storageSet, cost, weight, firstWave);
    }

    public static Map<ResourceLocation, Resource> GetAllMobsData()
    {
        ResourceManager resourceManager = Constants.server.getResourceManager();

        return resourceManager.listResources("survivor/wave_mob/", path -> path.getPath().endsWith(".json"));
    }
}
