package net.tazgirl.survivor.mobs.modifiers.modifier_enums;

import com.google.gson.JsonElement;
import net.tazgirl.magicjson.optionals.OptionalFromElement;
import net.tazgirl.survivor.mobs.modifiers.modifier_objects.ModifierCombination;
import net.tazgirl.survivor.mobs.modifiers.modifier_objects.PotionEffectModifier;
import net.tazgirl.survivor.mobs.modifiers.modifier_objects.base.ModifierStorageArgs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public enum ModifierArgTypes
{
    POTION_EFFECT("potion_effect", PotionEffectModifier.Storage.class, Map.of("effect", OptionalFromElement::MOB_EFFECT_HOLDER,"duration", OptionalFromElement::INT,"level", OptionalFromElement::INT)),
    COMBINATION("combination", ModifierCombination.Storage.class, Map.of("modifiers", ModifierArgTypes::elementToArray));

    public final String name;
    public final Class<? extends ModifierStorageArgs<?>> myClass;
    public final Map<String, Function<JsonElement, Object>> parameters;

    ModifierArgTypes(String name, Class<? extends ModifierStorageArgs<?>> myClass, Map<String, Function<JsonElement, Object>> parameters)
    {
        this.name = name;
        this.myClass = myClass;
        this.parameters = parameters;
    }

    public static String[] elementToArray(JsonElement element)
    {
        if(element.isJsonArray())
        {
            List<String> returnStrings = new ArrayList<>();
            for(JsonElement arrayElement : element.getAsJsonArray())
            {
                if(arrayElement.isJsonPrimitive())
                {
                    returnStrings.add(arrayElement.getAsString());
                }
            }

            return returnStrings.toArray(new String[0]);
        }

        return null;
    }
}
