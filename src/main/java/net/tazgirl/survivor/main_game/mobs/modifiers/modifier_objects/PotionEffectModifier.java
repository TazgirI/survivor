package net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.tazgirl.magicjson.StatementOptional;
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
        public StatementOptional<MobEffect> effect;
        public StatementOptional<Integer> duration;
        public StatementOptional<Integer> level;

        @Override
        public @NotNull ModifierStorageArgs<Storage> putArgument(@NotNull String string, @NotNull Object object)
        {
            switch (string)
            {
                case "effect" -> effect = SOBuilder.create(object, MobEffect.class);
                case "duration" -> duration = SOBuilder.create(object, Integer.class);
                case "level" -> level = SOBuilder.create(object, Integer.class);
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
            super(activeMob, storageRecord, storageRecord.trigger().get.apply(activeMob));
        }

        @Override
        public void PutArgument(@NotNull String string, @NotNull Object object)
        {

        }

        public void trigger(@NotNull ActiveMobEvent<?> event)
        {
            Storage storage = storageRecord.modifierArgs();

            activeMob.entity.addEffect(new MobEffectInstance(Holder.direct(storage.effect.get()), storage.duration.get(), storage.level.get()));
        }
    }

}
