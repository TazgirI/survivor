package net.tazgirl.survivor.main_game.mobs.modifiers;

import net.tazgirl.survivor.main_game.mobs.modifiers.modifier_arguments.Base;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public record MobModifierStorageRecord(int cost, ModifierTypes type, int weight, @Nullable Base modifierArgs)
{

}
