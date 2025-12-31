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
import net.tazgirl.survivor.magicjson.SOBuilder;

public class Helpers
{

    public static StatementOptional<Integer> integer(JsonElement element)
    {
        Object value = element.getAsString();
        if(!isAddress((String) value))
        {
            value = element.getAsInt();
        }

        return SOBuilder.create(value, Integer.class);
    }

    public static StatementOptional<String> string(JsonElement element)
    {
        return SOBuilder.create(element.getAsString(), String.class);
    }

    public static StatementOptional<MobEffect> mobEffectHolder(JsonElement element)
    {
        Object returnValue = element.getAsString();

        if(!isAddress((String) returnValue))
        {
            returnValue = BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.parse((String) returnValue));
        }

        return SOBuilder.create(returnValue, MobEffect.class);
    }

    static boolean isAddress(String string)
    {
        return PrivateCore.hasStatement(string);
    }
}
