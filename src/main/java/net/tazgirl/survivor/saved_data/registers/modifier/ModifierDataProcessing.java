package net.tazgirl.survivor.saved_data.registers.modifier;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.tutilz.admin.Logging;
import net.tazgirl.magicjson.data.Constants;
import net.tazgirl.magicjson.helpers.InputStreamToJson;
import net.tazgirl.survivor.main_game.mobs.modifiers.Enums.ModifierTriggers;
import net.tazgirl.survivor.main_game.mobs.modifiers.NameModifierRecord;
import net.tazgirl.survivor.main_game.mobs.modifiers.storage.ModifierStorageRecord;
import net.tazgirl.survivor.main_game.mobs.modifiers.Enums.ModifierArgTypes;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base.ModifierStorageArgs;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class ModifierDataProcessing
{
    static String dataPath = "survivor/modifier";

    public static void LoopJsons()
    {
        for(Map.Entry<ResourceLocation, Resource> entry : GetAllModifierData().entrySet())
        {
            JsonObject jsonObject;
            try
            {
                jsonObject = InputStreamToJson.getJson(entry.getValue().open());
            }
            catch (IOException e)
            {
                Logging.Warn("Failed to open \"" + entry.getKey() + "\", skipping file", Survivor.LOGGER);
                continue;
            }

            String key = entry.getKey().toString();
            int colon = key.indexOf(':');
            String address = key.substring(0, colon + 1) + key.substring(colon + dataPath.length() + 1);

            ModifierStorageRecord<?> processedJson = processJsonRoot(jsonObject);

            if(processedJson == null)
            {
                Logging.Warn("The Modifier JSON for \"" + entry.getKey().toString() + "\" failed to process correctly into a ModifierStorageRecord", Survivor.LOGGER);
                continue;
            }

            ModifierStorageRecordRegister.put(address, processedJson);
        }
    }

    public static ModifierStorageRecord<?> processJsonRoot(JsonObject jsonObject)
    {
        ModifierArgTypes modifier;
        int cost;
        int weight;
        JsonObject argsContainer;
        ModifierStorageArgs<?> modifierStorageArgs;
        ModifierTriggers trigger;
        int priority;
        NameModifierRecord nameRecord = null;
        JsonObject nameModifierObject;
        String nameModifier;
        int nameModifierPriority;

        try
        {
            modifier = Objects.requireNonNull(stringToModifierType(jsonObject.get("modifier").getAsString()));
            cost = jsonObject.get("cost").getAsInt();
            weight = jsonObject.get("weight").getAsInt();
            argsContainer = Objects.requireNonNull(jsonObject.getAsJsonObject("args"));
            trigger = Objects.requireNonNull(stringToModifierTrigger(jsonObject.get("trigger").getAsString()));
            priority = jsonObject.get("priority").getAsInt();
        }
        catch (Exception e)
        {
            return null;
        }

        try
        {
            nameModifierObject = Objects.requireNonNull(jsonObject.getAsJsonObject("name_modifier"));
            nameModifier = nameModifierObject.get("name_modifier").getAsString();
            nameModifierPriority = Objects.requireNonNull(NameModifierOrderPriorities.get(nameModifierObject.get("priority").getAsString())).priority;
            nameRecord = new NameModifierRecord(nameModifier, nameModifierPriority);
        }
        catch (Exception e)
        {
            // entry.key() not accessible within this method, nonspecific error message better than passing over a String every time that will only be used here
            Logging.Log("Could not process or find NameModifier in \"" + jsonObject.toString() + "\" this may be intentional", Survivor.LOGGER);
        }


        try
        {
            modifierStorageArgs = modifier.myClass.getDeclaredConstructor().newInstance();
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            return null;
        }

        for(Map.Entry<String, Function<JsonElement, Object>> entry : modifier.parameters.entrySet())
        {
            String key = entry.getKey();
            JsonElement element = argsContainer.get(key);

            modifierStorageArgs.putArgument(key, entry.getValue().apply(element));
        }

        // Should be completely fine just don't somehow write a ModifierStorageArgs that ignores the superclasses T
        return constructTypedModifierStorageArgs(cost, weight, modifierStorageArgs, trigger, priority, nameRecord, modifierStorageArgs.getClass());
    }

    private static <T extends ModifierStorageArgs<T>> ModifierStorageRecord<T> constructTypedModifierStorageArgs(int cost, int weight, ModifierStorageArgs<?> modifierStorageArgs, ModifierTriggers trigger, int priority, NameModifierRecord nameRecord, Class<T> buildClass)
    {
        return new ModifierStorageRecord<>(cost, weight, (T) modifierStorageArgs, trigger, priority, nameRecord);
    }

    public static Map<ResourceLocation, Resource> GetAllModifierData()
    {
        ResourceManager resourceManager = Constants.server.getResourceManager();

        return resourceManager.listResources(dataPath, path -> path.getPath().endsWith(".json"));
    }

    public static ModifierArgTypes stringToModifierType(String string)
    {
        for(ModifierArgTypes type: ModifierArgTypes.values())
        {
            if(type.name().equals(string))
            {
                return type;
            }
        }

        return null;
    }

    public static ModifierTriggers stringToModifierTrigger(String string)
    {
        for(ModifierTriggers trigger: ModifierTriggers.values())
        {
            if(trigger.name.equals(string))
            {
                return trigger;
            }
        }

        return null;
    }
}
