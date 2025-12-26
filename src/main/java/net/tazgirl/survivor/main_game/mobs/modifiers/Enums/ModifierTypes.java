package net.tazgirl.survivor.main_game.mobs.modifiers.Enums;

import com.google.gson.JsonElement;

import java.util.Map;
import java.util.function.Function;

public enum ModifierTypes
{
    POTION_EFFECT("potion_effect", Map.of("effect", Helpers::mobEffectHolder,"duration", Helpers::integer,"level", Helpers::integer));

    final String name;
    final Map<String, Function<JsonElement, Object>> parameters;

    ModifierTypes(String name, Map<String, Function<JsonElement, Object>> parameters)
    {
        this.name = name;
        this.parameters = parameters;
    }
}
