package net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base;

import net.tazgirl.survivor.main_game.mobs.ActiveMob;
import net.tazgirl.survivor.main_game.mobs.modifiers.storage.ModifierStorageRecord;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class ModifierStorageArgs<T extends ModifierStorageArgs<T>>
{
    public ModifierStorageArgs()
    {

    }

    @NonNull
    public abstract ModifierStorageArgs<T> putArgument(String string, Object object);

    @NonNull
    public abstract ModifierActiveBase<T> activeConstructor(ActiveMob<?> mob, ModifierStorageRecord<T> record);
}
