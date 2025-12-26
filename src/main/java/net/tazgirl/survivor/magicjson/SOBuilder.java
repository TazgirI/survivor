package net.tazgirl.survivor.magicjson;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.tazgirl.magicjson.StatementOptional;

import java.util.Map;
import java.util.Objects;

public class SOBuilder
{
    static Map<Class<?>, Object> defaultValues = Map.of(
            Integer.class, 1,
            MobEffect.class, Objects.requireNonNull(BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.parse("minecraft:strength")))
    );

    public static <T> StatementOptional<T> create(Object value, Class<T> classCheck)
    {
        return new StatementOptional<T>(value, (T) defaultValues.get(classCheck));
    }
}
