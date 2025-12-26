package net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base;

import net.tazgirl.magicjson.StatementOptional;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class ModifierStorageArgs<T extends ModifierStorageArgs<T>>
{
    public ModifierStorageArgs()
    {

    }

    public abstract ModifierStorageArgs<T> putArgument(String string, Object object);

    public abstract ModifierActiveBase<T> constructActive();
}
