package net.tazgirl.survivor.saved_data.registers.wave_mob;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.EntityType;
import net.tazgirl.magicjson.optionals.OptionalFromElement;
import net.tazgirl.magicjson.optionals.numbers.IntegerStatementOptional;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.saved_data.registers.modifier_group.RegisterModifierGroup;
import net.tazgirl.tutilz.admin.Logging;
import net.tazgirl.magicjson.data.Constants;
import net.tazgirl.magicjson.helpers.InputStreamToJson;
import net.tazgirl.survivor.main_game.mobs.wave_mobs.WaveMob;
import net.tazgirl.survivor.saved_data.registers.modifier.RegisterModifierStorageRecord;
import net.tazgirl.survivor.main_game.mobs.modifiers.storage.ModifierStorageSet;
import net.tazgirl.tutilz.registers.MakeRegistryAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WaveMobDataProcessing
{
    // TODO: All the data processing is a mess, fix much later

    public static String dataPath = "survivor/wave_mob";

    public static void ProcessWaveMobData()
    {
        for(Map.Entry<ResourceLocation, Resource> entry : GetAllMobsData().entrySet())
        {
            WaveMob<?> entryMob = entryToWaveMob(entry);
            if(entryMob != null)
            {
                RegisterWaveMob.put(MakeRegistryAddress.withPath(entry.getKey(), dataPath),entryMob);
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
        JsonElement modifiersElement = jsonObject.get("modifiers");
        JsonElement guaranteedModifiersElement = jsonObject.get("guaranteed_modifiers");
        JsonElement firstWaveElement = jsonObject.get("first_wave");

        String entityTypeLocation = null;

        List<String> modifierStorageAddresses = new ArrayList<>();
        List<String> guaranteedModifierStorageAddresses = new ArrayList<>();

        IntegerStatementOptional cost = OptionalFromElement.INT(jsonObject.get("cost"));
        IntegerStatementOptional weight = OptionalFromElement.INT(jsonObject.get("weight"));

        IntegerStatementOptional firstWave = IntegerStatementOptional.from(0);

        if(cost == null || weight == null)
        {
            logFail(entry);
            return null;
        }

        JsonElement entityTypeLocationElement = jsonObject.get("entity_type");
        if(entityTypeLocationElement != null && entityTypeLocationElement.isJsonPrimitive())
        {
            entityTypeLocation = entityTypeLocationElement.getAsString();
        }
        else
        {
            logFail(entry);
            return null;
        }

        if(modifiersElement != null && modifiersElement.isJsonArray())
        {
            modifiersElement.getAsJsonArray().forEach(element ->
            {
                if(element.isJsonPrimitive())
                {
                    String elementString = element.getAsString();

                    if(RegisterModifierStorageRecord.hasAddress(elementString))
                    {
                        modifierStorageAddresses.add(elementString);
                    }
                    else if(RegisterModifierGroup.hasAddress(elementString))
                    {
                        modifierStorageAddresses.addAll(RegisterModifierGroup.get(elementString));
                    }
                }
            });
        }

        if(guaranteedModifiersElement != null && guaranteedModifiersElement.isJsonArray())
        {
            guaranteedModifiersElement.getAsJsonArray().forEach(element ->
            {
                if(element.isJsonPrimitive())
                {
                    String elementString = element.getAsString();

                    if(RegisterModifierStorageRecord.hasAddress(elementString))
                    {
                        guaranteedModifierStorageAddresses.add(elementString);
                    }
                    else if(RegisterModifierGroup.hasAddress(elementString))
                    {
                        guaranteedModifierStorageAddresses.addAll(RegisterModifierGroup.get(elementString));
                    }
                }
            });
        }

        if(firstWaveElement != null)
        {
            firstWave = OptionalFromElement.INT(firstWaveElement);
        }


        EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(entityTypeLocation));

        ModifierStorageSet storageSet = new ModifierStorageSet(RegisterModifierStorageRecord.getList(modifierStorageAddresses));
        ModifierStorageSet guaranteedStorageSet = new ModifierStorageSet(RegisterModifierStorageRecord.getList(guaranteedModifierStorageAddresses));

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

    private static void logFail(Map.Entry<ResourceLocation, Resource> entry)
    {
        Logging.Warn("JsonObject \"" + entry.getKey() + "\" failed to process. Please verify all expected values are present and in an acceptable form even if empty", Survivor.LOGGER);
    }
}
