package net.tazgirl.survivor.saved_data.registers.modifiers.modifier_group;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.tazgirl.magicjson.data.Constants;
import net.tazgirl.magicjson.helpers.InputStreamToJson;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.SurvivorLogging;
import net.tazgirl.survivor.saved_data.registers.RegisterDataProcessing;
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
        RegisterModifierGroup.putAll(RegisterDataProcessing.loopRegisterJson(dataPath, ModifierGroupDataProcessing::processJsonRoot));
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
            SurvivorLogging.Debug("Failed to process the interior JsonArray for: ");
            return null;
        }

        return addresses;
    }
}
