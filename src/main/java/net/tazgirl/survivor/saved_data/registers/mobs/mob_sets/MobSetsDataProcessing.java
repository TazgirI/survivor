package net.tazgirl.survivor.saved_data.registers.mobs.mob_sets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.saved_data.registers.RegisterDataProcessing;
import net.tazgirl.tutilz.admin.Logging;
import net.tazgirl.magicjson.data.Constants;
import net.tazgirl.magicjson.helpers.InputStreamToJson;
import net.tazgirl.survivor.mobs.wave_mobs.WaveMobStorageSet;
import net.tazgirl.tutilz.registers.MakeRegistryAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MobSetsDataProcessing
{
    static final String dataPath = "survivor/wave_mob_set";

    public static void LoopJsons()
    {
        RegisterMobSets.putAll(RegisterDataProcessing.loopRegisterJson(dataPath, MobSetsDataProcessing::processJsonRoot));
    }

    public static WaveMobStorageSet processJsonRoot(JsonObject jsonObject)
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

        return new WaveMobStorageSet(addresses);
    }
}
