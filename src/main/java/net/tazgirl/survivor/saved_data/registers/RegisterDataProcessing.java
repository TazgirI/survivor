package net.tazgirl.survivor.saved_data.registers;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.tazgirl.magicjson.data.Constants;
import net.tazgirl.magicjson.helpers.InputStreamToJson;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.SurvivorLogging;
import net.tazgirl.survivor.mobs.wave_mobs.WaveMobStorageSet;
import net.tazgirl.survivor.saved_data.registers.mobs.mob_sets.RegisterMobSets;
import net.tazgirl.tutilz.admin.Logging;
import net.tazgirl.tutilz.registers.MakeRegistryAddress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class RegisterDataProcessing
{
    public static <T> Map<String, T> loopRegisterJson(String dataPath, Function<JsonObject, T> function)
    {
        return loopRegisterJson(getJsons(dataPath), dataPath, function);
    }

    public static <T> Map<String, T> loopRegisterJson(Map<ResourceLocation, Resource> map, String dataPath, Function<JsonObject, T> function)
    {
        Map<String, T> returnMap = new HashMap<>();

        for(Map.Entry<ResourceLocation, Resource> entry : map.entrySet())
        {
            JsonObject jsonObject;
            try
            {
                jsonObject = Objects.requireNonNull(InputStreamToJson.getJson(entry.getValue().open()));
            }
            catch (Exception e)
            {
                Logging.Warn("Failed to open \"" + entry.getKey() + "\", skipping file", Survivor.LOGGER);
                continue;
            }

            String address = MakeRegistryAddress.withPath(entry.getKey(), dataPath);

            T value = function.apply(jsonObject);

            if(value == null)
            {
                SurvivorLogging.Debug("The registry JSON for \"" + entry.getKey().toString() + "\" failed to process correctly into the designated type", List.of(jsonObject));
                continue;
            }

            returnMap.put(address, value);
        }

        return returnMap;
    }

    public static Map<ResourceLocation, Resource> getJsons(String dataPath)
    {
        ResourceManager resourceManager = Constants.server.getResourceManager();

        return resourceManager.listResources(dataPath, path -> path.getPath().endsWith(".json"));
    }

}
