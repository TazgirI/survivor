package net.tazgirl.survivor.main_game.mobs.modifiers.Enums;

import com.google.gson.JsonElement;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.PotionEffectModifier;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base.ModifierStorageArgs;
import org.checkerframework.checker.units.qual.C;

import java.util.Map;
import java.util.function.Function;

public enum ModifierArgTypes
{
    POTION_EFFECT("potion_effect", PotionEffectModifier.Storage.class, Map.of("effect", Helpers::mobEffectHolder,"duration", Helpers::integer,"level", Helpers::integer));

    public final String name;
    public final Class<? extends ModifierStorageArgs<?>> myClass;
    public final Map<String, Function<JsonElement, Object>> parameters;

    ModifierArgTypes(String name, Class<? extends ModifierStorageArgs<?>> myClass, Map<String, Function<JsonElement, Object>> parameters)
    {
        this.name = name;
        this.myClass = myClass;
        this.parameters = parameters;
    }
}
