package net.tazgirl.survivor.main_game.mobs.modifiers;

import net.tazgirl.survivor.main_game.mobs.modifiers.Enums.ModifierTriggers;
import net.tazgirl.survivor.main_game.mobs.modifiers.Enums.ModifierTypes;
import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_objects.base.ModifierStorageArgs;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record ModifierStorageRecord<T extends ModifierStorageArgs>(int cost, ModifierTypes type, int weight, T modifierArgs, ModifierTriggers trigger, int priority)
{

}
