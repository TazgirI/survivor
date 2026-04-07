package net.tazgirl.survivor.saved_data.registers.modifiers.modifier;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.tazgirl.magicjson.optionals.OptionalFromElement;
import net.tazgirl.magicjson.optionals.numbers.IntegerStatementOptional;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.SurvivorLogging;
import net.tazgirl.survivor.mobs.modifiers.modifier_enums.Target;
import net.tazgirl.survivor.saved_data.registers.RegisterDataProcessing;
import net.tazgirl.tutilz.admin.Logging;
import net.tazgirl.magicjson.data.Constants;
import net.tazgirl.magicjson.helpers.InputStreamToJson;
import net.tazgirl.survivor.mobs.modifiers.modifier_enums.ModifierTriggers;
import net.tazgirl.survivor.mobs.modifiers.NameModifierRecord;
import net.tazgirl.survivor.mobs.modifiers.storage.ModifierStorageRecord;
import net.tazgirl.survivor.mobs.modifiers.modifier_enums.ModifierArgTypes;
import net.tazgirl.survivor.mobs.modifiers.modifier_objects.base.ModifierStorageArgs;
import net.tazgirl.tutilz.registers.MakeRegistryAddress;

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
        RegisterModifierStorageRecord.putAll(RegisterDataProcessing.loopRegisterJson(dataPath, ModifierDataProcessing::processJsonRoot));
    }

    public static ModifierStorageRecord<?> processJsonRoot(JsonObject jsonObject)
    {
        ModifierArgTypes modifier;

        IntegerStatementOptional cost;
        IntegerStatementOptional weight;

        JsonObject argsContainer;
        ModifierStorageArgs<?> modifierStorageArgs;

        ModifierTriggers trigger;
        IntegerStatementOptional priority;

        NameModifierRecord nameRecord = null;
        JsonObject nameModifierObject;
        String nameModifier;
        int nameModifierPriority;

        JsonElement targetElement;
        Target target;

        try
        {
            modifier = Objects.requireNonNull(stringToModifierType(jsonObject.get("modifier").getAsString()));
            cost = Objects.requireNonNull(OptionalFromElement.INT(jsonObject.get("cost")));
            weight = Objects.requireNonNull(OptionalFromElement.INT(jsonObject.get("weight")));
            argsContainer = Objects.requireNonNull(jsonObject.getAsJsonObject("args"));
            trigger = Objects.requireNonNull(stringToModifierTrigger(jsonObject.get("trigger").getAsString()));
            priority = OptionalFromElement.INT(jsonObject.get("priority"));
            target = stringToTarget(jsonObject.get("target").getAsString());
        }
        catch (Exception e)
        {
            SurvivorLogging.Debug("A Modifier .json has failed to process an expected value");
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
            SurvivorLogging.Log("Could not process or find NameModifier in \"" + jsonObject + "\" this may be intentional");
        }


        try
        {
            modifierStorageArgs = modifier.myClass.getDeclaredConstructor().newInstance();
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            SurvivorLogging.Debug("A Modifier .json has failed to construct it's args class");
            return null;
        }

        for(Map.Entry<String, Function<JsonElement, Object>> entry : modifier.parameters.entrySet())
        {
            String key = entry.getKey();
            JsonElement element = argsContainer.get(key);

            modifierStorageArgs.putArgument(key, entry.getValue().apply(element));
        }

        // Should be completely fine just don't somehow write a ModifierStorageArgs that ignores the superclasses T
        return constructTypedModifierStorageArgs(cost, weight, modifierStorageArgs, trigger, priority, nameRecord, target, modifierStorageArgs.getClass());
    }

    private static <T extends ModifierStorageArgs<T>> ModifierStorageRecord<T> constructTypedModifierStorageArgs(IntegerStatementOptional cost, IntegerStatementOptional weight, ModifierStorageArgs<?> modifierStorageArgs, ModifierTriggers trigger, IntegerStatementOptional priority, NameModifierRecord nameRecord, Target target, Class<T> buildClass)
    {
        return new ModifierStorageRecord<>(cost, weight, (T) modifierStorageArgs, trigger, priority, nameRecord, target);
    }

    public static ModifierArgTypes stringToModifierType(String string)
    {
        for(ModifierArgTypes type: ModifierArgTypes.values())
        {
            if(type.name().equalsIgnoreCase(string))
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

    public static Target stringToTarget(String string)
    {
        for(Target target : Target.values())
        {
            if(target.toString().equals(string.toUpperCase()))
            {
                return target;
            }
        }
        return Target.SELF;
    }
}
