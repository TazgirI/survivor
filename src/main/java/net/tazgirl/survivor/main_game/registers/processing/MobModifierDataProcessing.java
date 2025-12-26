package net.tazgirl.survivor.main_game.registers.processing;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.tazgirl.magicjson.data.Constants;
import net.tazgirl.survivor.main_game.mobs.modifiers.ModifierStorageRecord;
import net.tazgirl.survivor.main_game.mobs.modifiers.Enums.ModifierTypes;

import java.util.Map;

public class MobModifierDataProcessing
{
    public static ModifierStorageRecord ProcessModifierJson(JsonObject jsonObject)
    {
        ModifierTypes modifier;
        try
        {
            modifier = stringToModifierType(jsonObject.get("modifier").getAsString());
        }
        catch (Exception e)
        {
            return null;
        }

        if(modifier == null)
        {
            return null;
        }

        for()

    }

    public static Map<ResourceLocation, Resource> GetAllModifierData()
    {
        ResourceManager resourceManager = Constants.server.getResourceManager();

        return resourceManager.listResources("survivor/modifier/", path -> path.getPath().endsWith(".json"));
    }

    public static ModifierTypes stringToModifierType(String string)
    {
        for(ModifierTypes type: ModifierTypes.values())
        {
            if(type.name().equals(string))
            {
                return type;
            }
        }

        return null;
    }
}
