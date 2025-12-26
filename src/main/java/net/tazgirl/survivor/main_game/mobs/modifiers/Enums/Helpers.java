package net.tazgirl.survivor.main_game.mobs.modifiers.Enums;

import com.google.gson.JsonElement;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.effects.AllOf;
import net.tazgirl.magicjson.PrivateCore;
import net.tazgirl.magicjson.StatementOptional;

public class Helpers
{

    public static StatementOptional<Integer> integer(JsonElement element)
    {
        Object value = element.getAsString();
        if(!isAddress((String) value))
        {
            value = element.getAsInt();
        }

        return new StatementOptional<>(value);
    }

    public static StatementOptional<String> string(JsonElement element)
    {
        return new StatementOptional<>(element.getAsString());
    }

    public static StatementOptional<MobEffect> mobEffectHolder(JsonElement element)
    {
        StatementOptional<MobEffect> returnValue;
        String elementString = element.getAsString();

        if(PrivateCore.hasStatement(elementString))
        {
            returnValue = new StatementOptional<>(elementString);
        }
        else
        {
            returnValue = new StatementOptional<>(BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.parse(elementString)));
        }

        return returnValue;
    }

    static boolean isAddress(String string)
    {
        return PrivateCore.hasStatement(string);
    }
}
