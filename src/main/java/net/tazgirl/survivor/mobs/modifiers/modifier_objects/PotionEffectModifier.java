package net.tazgirl.survivor.mobs.modifiers.modifier_objects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.tazgirl.magicjson.optionals.minecraft_types.MobEffectHolderStatementOptional;
import net.tazgirl.magicjson.optionals.numbers.IntegerStatementOptional;
import net.tazgirl.survivor.mobs.ActiveMob;
import net.tazgirl.survivor.mobs.events.ActiveMobEvent;
import net.tazgirl.survivor.mobs.modifiers.modifier_enums.Target;
import net.tazgirl.survivor.mobs.modifiers.storage.ModifierStorageRecord;
import net.tazgirl.survivor.mobs.modifiers.modifier_objects.base.ModifierActiveBase;
import net.tazgirl.survivor.mobs.modifiers.modifier_objects.base.ModifierStorageArgs;
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
                case "effect" -> effect = (MobEffectHolderStatementOptional) object;
                case "duration" -> duration = (IntegerStatementOptional) object;
                case "level" -> level = (IntegerStatementOptional) object;
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

            LivingEntity targetEntity = Target.getTarget(event, storageRecord);

            // Re-route through BuffHolder if potion effect trigger needed
            if(targetEntity != null)
            {
                targetEntity.addEffect(new MobEffectInstance(storage.effect.getWithArg(targetEntity), storage.duration.get(), storage.level.get()));
            }
        }
    }

}
