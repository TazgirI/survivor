package net.tazgirl.survivor.main_game.mobs.wave_mobs;

import net.minecraft.world.entity.EntityType;
import net.tazgirl.magicjson.optionals.numbers.IntegerStatementOptional;

public interface IWaveMob
{
    int getCost();

    int getWeight();

    EntityType<?> getEntityType();

    void setWeight(IntegerStatementOptional newWeight);

    void setCost(IntegerStatementOptional newCost);
}
