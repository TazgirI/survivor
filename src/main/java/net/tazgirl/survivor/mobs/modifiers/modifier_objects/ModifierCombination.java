package net.tazgirl.survivor.mobs.modifiers.modifier_objects;

import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.mobs.ActiveMob;
import net.tazgirl.survivor.mobs.events.ActiveMobEvent;
import net.tazgirl.survivor.mobs.modifiers.modifier_objects.base.ModifierActiveBase;
import net.tazgirl.survivor.mobs.modifiers.modifier_objects.base.ModifierStorageArgs;
import net.tazgirl.survivor.mobs.modifiers.storage.ModifierStorageRecord;
import net.tazgirl.tutilz.admin.Logging;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class ModifierCombination
{
    private ModifierCombination()
    {

    }

    public static class Storage extends ModifierStorageArgs<Storage>
    {
        public List<String> modifiers = new ArrayList<>();

        @Override
        public @NonNull ModifierStorageArgs<Storage> putArgument(@NotNull String string, @NotNull Object object)
        {
            if(object instanceof String[] strings)
            {
                modifiers.addAll(List.of(strings));
            }

            return this;
        }

        @Override
        public @NonNull ModifierActiveBase<Storage> activeConstructor(@NotNull ActiveMob<?> mob, @NotNull ModifierStorageRecord<Storage> record)
        {
            Logging.Error("Attempted to construct an Active of a ModifierCombination, processing should instead fetch each member of \"modifiers\" and construct that", Survivor.LOGGER);
            throw new RuntimeException("Tried to construct ModifierCombination");
        }
    }

    public static class Active extends ModifierActiveBase<Storage>
    {

        public Active(ActiveMob<?> activeMob, ModifierStorageRecord<Storage> storageRecord, ActiveMobEvent<?> trigger)
        {
            super(activeMob, storageRecord, trigger);
            throw new RuntimeException("Tried to construct a ModifierConstruction.Active, construct it's modifiers instead");
        }

        @Override
        public void trigger(ActiveMobEvent<?> event)
        {

        }

        @Override
        public void PutArgument(String string, Object object)
        {

        }
    }
}
