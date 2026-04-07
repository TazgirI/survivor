package net.tazgirl.survivor.saved_data.registers.mobs.wave_mob;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.tazgirl.magicjson.optionals.OptionalFromElement;
import net.tazgirl.magicjson.optionals.numbers.IntegerStatementOptional;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.saved_data.registers.RegisterDataProcessing;
import net.tazgirl.survivor.saved_data.registers.mobs.ai.RegisterAi;
import net.tazgirl.survivor.saved_data.registers.modifier_group.RegisterModifierGroup;
import net.tazgirl.tutilz.admin.Logging;
import net.tazgirl.magicjson.data.Constants;
import net.tazgirl.magicjson.helpers.InputStreamToJson;
import net.tazgirl.survivor.mobs.wave_mobs.WaveMob;
import net.tazgirl.survivor.saved_data.registers.modifier.RegisterModifierStorageRecord;
import net.tazgirl.survivor.mobs.modifiers.storage.ModifierStorageSet;
import net.tazgirl.tutilz.registers.MakeRegistryAddress;

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
        for(Map.Entry<ResourceLocation, Resource> entry : RegisterDataProcessing.getJsons(dataPath).entrySet())
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
        JsonElement aiTypeElement = jsonObject.get("ai_type");

        String entityTypeLocation = null;

        List<String> modifierStorageAddresses = new ArrayList<>();
        List<String> guaranteedModifierStorageAddresses = new ArrayList<>();

        IntegerStatementOptional cost = OptionalFromElement.INT(jsonObject.get("cost"));
        IntegerStatementOptional weight = OptionalFromElement.INT(jsonObject.get("weight"));

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

        EntityType<?> tempEntityType = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(entityTypeLocation));


        Entity testEntity = tempEntityType.create(Globals.server.overworld());


        if(!(testEntity instanceof LivingEntity))
        {
            throw new IllegalArgumentException("Attempted to create a WaveMob of non LivingEntity entityType, (OR it has a null or MISC MobCategory, in which case I need to make a more robust system)");
        }

        // istg if this somehow blows something up later then I'll blow myself up
        testEntity.remove(Entity.RemovalReason.DISCARDED);

        @SuppressWarnings("unchecked") EntityType<? extends LivingEntity> entityType = (EntityType<? extends LivingEntity>) tempEntityType;


        ModifierStorageSet storageSet = new ModifierStorageSet(RegisterModifierStorageRecord.getList(modifierStorageAddresses));
        ModifierStorageSet guaranteedStorageSet = new ModifierStorageSet(RegisterModifierStorageRecord.getList(guaranteedModifierStorageAddresses));

        WaveMob<?> waveMob = new WaveMob<>(entityType, storageSet, cost, weight);

        if(firstWaveElement != null)
        {
            IntegerStatementOptional firstWave = OptionalFromElement.INT(firstWaveElement);
            waveMob.setFirstWave(firstWave);
        }

        if(aiTypeElement != null)
        {
            waveMob.setAiType(RegisterAi.fromElement(aiTypeElement));
        }

        waveMob.addModifiers(storageSet);
        waveMob.addGuaranteedModifiers(guaranteedStorageSet);

        return waveMob;
    }

    private static void logFail(Map.Entry<ResourceLocation, Resource> entry)
    {
        Logging.Warn("JsonObject \"" + entry.getKey() + "\" failed to process. Please verify all expected values are present and in an acceptable form even if empty", Survivor.LOGGER);
    }
}
