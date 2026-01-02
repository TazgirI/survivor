package net.tazgirl.survivor.main_game.mobs.modifiers.storage;

import net.tazgirl.magicjson.optionals.numbers.IntegerStatementOptional;
import net.tazgirl.survivor.main_game.mobs.ActiveMob;
import net.tazgirl.survivor.main_game.mobs.modifiers.Enums.ModifierTriggers;
import net.tazgirl.survivor.main_game.mobs.modifiers.NameModifierRecord;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base.ModifierActiveBase;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base.ModifierStorageArgs;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record ModifierStorageRecord<T extends ModifierStorageArgs<T>>(IntegerStatementOptional cost, IntegerStatementOptional weight, T modifierArgs, ModifierTriggers trigger, IntegerStatementOptional priority, NameModifierRecord nameModifier)
{
    public ModifierActiveBase<T> constructActive(ActiveMob<?> activeMob)
    {
        return modifierArgs.activeConstructor(activeMob, this);
    }
}
