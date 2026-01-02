package net.tazgirl.survivor.magicjson;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.tazgirl.magicjson.optionals.StatementOptional;
import net.tazgirl.magicjson.optionals.numbers.DoubleStatementOptional;
import net.tazgirl.magicjson.optionals.numbers.FloatStatementOptional;
import net.tazgirl.magicjson.optionals.numbers.IntegerStatementOptional;
import net.tazgirl.magicjson.optionals.numbers.LongStatementOptional;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.tutilz.admin.Logging;

import java.util.Map;
import java.util.Objects;

public class SOBuilder
{
    static Map<Class<?>, Object> defaultValues = Map.of(
            Integer.class, 1,
            MobEffect.class, Objects.requireNonNull(BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.parse("minecraft:strength"))),
            String.class, ""
    );

    public static <T> StatementOptional<T> createAbstract(Object value, Class<T> classCheck)
    {
        Object defaultValue = defaultValues.get(classCheck);

        if(defaultValue == null)
        {
            Logging.Debug("Attempted to pass unacceptable class to SOBuilder", Survivor.LOGGER);
            throw new NullPointerException();
        }

        return new StatementOptional<T>(value, (T) defaultValue);
    }

    public static IntegerStatementOptional INTEGER(Object value)
    {
        return new IntegerStatementOptional(value, 0);
    }

    public static FloatStatementOptional FLOAT(Object value)
    {
        return new FloatStatementOptional(value, 0f);
    }

    public static DoubleStatementOptional DOUBLE(Object value)
    {
        return new DoubleStatementOptional(value, 0.0);
    }

    public static LongStatementOptional LONG(Object value)
    {
        return new LongStatementOptional(value, 0L);
    }
}
