package net.tazgirl.survivor.main_game.mobs;

import net.minecraft.world.entity.LivingEntity;
import net.tazgirl.survivor.main_game.mobs.wave_mobs.WaveMob;

public class ActiveMob<T extends LivingEntity>
{
    public T entity;
    public WaveMob<T> waveMob;

    public SpawnTrigger spawnTrigger;
    public DamageTrigger.Pre damageTriggerPre;
    public DamageTrigger.Post damageTriggerPost;


    public ActiveMob(T entity, WaveMob<T> waveMob)
    {
        this.entity = entity;
        this.waveMob = waveMob;
        spawnTrigger = new SpawnTrigger(this);
        damageTriggerPre = new DamageTrigger.Pre(this);
        damageTriggerPost = new DamageTrigger.Post(this);
    }


    public static class SpawnTrigger extends ActiveMobEvent<SpawnTrigger>
    {
        public SpawnTrigger(ActiveMob<?> myMob)
        {
            super(myMob);
        }

        @Override
        SpawnTrigger newEvent()
        {
            return new SpawnTrigger(myMob);
        }
    }

    public static class DamageTrigger
    {
        public static class Pre extends ActiveMobEvent<Pre>
        {
            public Pre(ActiveMob<?> myMob)
            {
                super(myMob);
            }

            @Override
            Pre newEvent()
            {
                return new Pre(myMob);
            }
        }

        public static class Post extends ActiveMobEvent<Post>
        {
            public Post(ActiveMob<?> myMob)
            {
                super(myMob);
            }

            @Override
            Post newEvent()
            {
                return new Post(myMob);
            }
        }
    }

}
