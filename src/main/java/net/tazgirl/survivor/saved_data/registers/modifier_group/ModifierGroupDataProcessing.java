package net.tazgirl.survivor.saved_data.registers.modifier_group;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.tazgirl.magicjson.data.Constants;
import net.tazgirl.magicjson.helpers.InputStreamToJson;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.tutilz.admin.Logging;
import net.tazgirl.tutilz.registers.MakeRegistryAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModifierGroupDataProcessing
{
    static final String dataPath = "survivor/modifier_group";

    public static void LoopJsons()
    {
        for(Map.Entry<ResourceLocation, Resource> entry : GetAllMobSetsData().entrySet())
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

            List<String> list = processJsonRoot(jsonObject);

            if(list == null || list.isEmpty())
            {
                Logging.Warn("The ModifierGroup JSON for \"" + entry.getKey().toString() + "\" failed to process correctly into a List<String>", Survivor.LOGGER);
                continue;
            }

            RegisterModifierGroup.put(address, list);
        }
    }

    public static List<String> processJsonRoot(JsonObject jsonObject)
    {
        List<String> addresses = new ArrayList<>();

        try
        {
            JsonArray array = jsonObject.entrySet().iterator().next().getValue().getAsJsonArray();
            array.forEach(element -> addresses.add(element.getAsString()));
        }
        catch(Exception e)
        {
            // Relies on the following null catch after this method is called to log the address and such
            Logging.Warn("Failed to process the interior JsonArray for: ", Survivor.LOGGER);
            return null;
        }

        return addresses;
    }

    public static Map<ResourceLocation, Resource> GetAllMobSetsData()
    {
        ResourceManager resourceManager = Constants.server.getResourceManager();

        return resourceManager.listResources(dataPath, path -> path.getPath().endsWith(".json"));
    }
}
