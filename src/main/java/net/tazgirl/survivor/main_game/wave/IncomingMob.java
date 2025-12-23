package net.tazgirl.survivor.main_game.wave;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class IncomingMob<T extends Entity>
{

    EntityType<T> myType;

    public IncomingMob(EntityType<T> myType)
    {
        this.myType = myType;
    }

}
