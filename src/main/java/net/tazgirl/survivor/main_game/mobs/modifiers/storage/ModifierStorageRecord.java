package net.tazgirl.survivor.main_game.mobs.modifiers.storage;

import net.tazgirl.magicjson.optionals.numbers.IntegerStatementOptional;
import net.tazgirl.survivor.main_game.mobs.ActiveMob;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_enums.ModifierTriggers;
import net.tazgirl.survivor.main_game.mobs.modifiers.NameModifierRecord;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base.ModifierActiveBase;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base.ModifierStorageArgs;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record ModifierStorageRecord<T extends ModifierStorageArgs<T>>(IntegerStatementOptional cost, IntegerStatementOptional weight, T modifierArgs, ModifierTriggers trigger, IntegerStatementOptional priority, NameModifierRecord nameModifier)
{
    public ModifierActiveBase<T> constructActive(ActiveMob<?> activeMob)
    {
        ModifierActiveBase<T> activeBase = modifierArgs.activeConstructor(activeMob, this);

        activeMob.modifiers.add(activeBase);

        return activeBase;
    }

    public CachedModifierStorageRecord constructCachedValues()
    {
        return new CachedModifierStorageRecord(this, cost.get(), weight.get());
    }

    public int getWeight()
    {
        return weight.get();
    }

    public int getCost()
    {
        return cost.get();
    }
}
