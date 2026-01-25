package net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.tazgirl.magicjson.optionals.StatementOptional;
import net.tazgirl.magicjson.optionals.minecraft_types.MobEffectHolderStatementOptional;
import net.tazgirl.magicjson.optionals.numbers.IntegerStatementOptional;
import net.tazgirl.survivor.magicjson.SOBuilder;
import net.tazgirl.survivor.main_game.mobs.ActiveMob;
import net.tazgirl.survivor.main_game.mobs.ActiveMobEvent;
import net.tazgirl.survivor.main_game.mobs.modifiers.storage.ModifierStorageRecord;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base.ModifierActiveBase;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base.ModifierStorageArgs;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public final class PotionEffectModifier
{
    private PotionEffectModifier()
    {

    }

    public static class Storage extends ModifierStorageArgs<Storage>
    {
        public MobEffectHolderStatementOptional effect;
        public IntegerStatementOptional duration;
        public IntegerStatementOptional level;

        @Override
        public @NotNull ModifierStorageArgs<Storage> putArgument(@NotNull String string, @NotNull Object object)
        {
            switch (string)
            {
                case "effect" -> effect = SOBuilder.MOB_EFFECT(object);
                case "duration" -> duration = SOBuilder.INTEGER(object);
                case "level" -> level = SOBuilder.INTEGER(object);
            }

            return this;
        }

        @Override
        public @NonNull ModifierActiveBase<Storage> activeConstructor(@NotNull ActiveMob<?> activeMob, @NotNull ModifierStorageRecord<Storage> record)
        {
            return new Active(activeMob, record);
        }
    }

    public static class Active extends ModifierActiveBase<Storage>
    {
        public Active(ActiveMob<?> activeMob, ModifierStorageRecord<Storage> storageRecord)
        {
            super(activeMob, storageRecord);
        }

        @Override
        public void PutArgument(@NotNull String string, @NotNull Object object)
        {

        }

        public void trigger(@NotNull ActiveMobEvent<?> event)
        {
            Storage storage = storageRecord.modifierArgs();

            if(activeMob.entity instanceof LivingEntity livingEntity)
            {
                livingEntity.addEffect(new MobEffectInstance(storage.effect.getWithArg(livingEntity), storage.duration.get(), storage.level.get()));
            }


        }
    }

}
