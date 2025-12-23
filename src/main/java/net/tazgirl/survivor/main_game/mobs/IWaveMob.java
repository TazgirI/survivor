package net.tazgirl.survivor.main_game.mobs;

import net.minecraft.world.entity.EntityType;

public interface IWaveMob
{
    int getCost();

    int getWeight();

    EntityType<?> getEntityType();

    void setWeight(int newWeight);

    void setCost(int newCost);
}
