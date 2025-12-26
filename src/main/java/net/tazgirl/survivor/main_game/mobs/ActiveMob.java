package net.tazgirl.survivor.main_game.mobs;

import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;

public class ActiveMob
{
    public LivingEntity entity;

    public SpawnTrigger spawnTrigger;
    public DamageTrigger.Pre damageTriggerPre;
    public DamageTrigger.Post damageTriggerPost;


    public ActiveMob(LivingEntity entity)
    {
        this.entity = entity;
        spawnTrigger = new SpawnTrigger();
        damageTriggerPre = new DamageTrigger.Pre();
        damageTriggerPost = new DamageTrigger.Post();
    }


    public static class SpawnTrigger extends ActiveMobEvent<SpawnTrigger>
    {
        @Override
        SpawnTrigger newEvent()
        {
            return new SpawnTrigger();
        }
    }

    public static class DamageTrigger
    {
        public static class Pre extends ActiveMobEvent<Pre>
        {
            @Override
            Pre newEvent()
            {
                return new Pre();
            }
        }

        public static class Post extends ActiveMobEvent<Post>
        {
            @Override
            Post newEvent()
            {
                return new Post();
            }
        }
    }

}
