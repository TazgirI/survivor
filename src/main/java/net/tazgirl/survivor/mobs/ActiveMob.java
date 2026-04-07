package net.tazgirl.survivor.mobs;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.tazgirl.survivor.Priority;
import net.tazgirl.survivor.TickDistributor;
import net.tazgirl.survivor.buffs.BuffHolder;
import net.tazgirl.survivor.saved_data.registers.mobs.ai.RegisterAi;
import net.tazgirl.survivor.mobs.events.ActiveMobEvent;
import net.tazgirl.survivor.mobs.events.ActiveMobInheritanceEvent;
import net.tazgirl.survivor.mobs.modifiers.modifier_objects.base.ModifierActiveBase;
import net.tazgirl.survivor.mobs.wave_mobs.WaveMob;

import java.util.ArrayList;
import java.util.List;

public class ActiveMob<T extends LivingEntity> extends BuffHolder
{
    // TODO: Move the whole ecosystem to LivingEntity

    public T entity;
    public WaveMob<T> waveMob;

    public List<ModifierActiveBase<?>> modifiers = new ArrayList<>();

    public TickDistributor.DistributedTick aiTick = TickDistributor.newDistributedTick(1);

    public SpawnTrigger spawnTrigger;
    public DamageTrigger.Pre damageTriggerPre;
    public DamageTrigger.Post damageTriggerPost;
    public TickTrigger.Pre tickTriggerPre;
    public TickTrigger.Post tickTriggerPost;
    public AttackTrigger.Pre attackTriggerPre;
    public AttackTrigger.Post attackTriggerPost;


    public ActiveMob(T entity, WaveMob<T> waveMob)
    {
        this.entity = entity;
        this.waveMob = waveMob;
        spawnTrigger = new SpawnTrigger(this);
        damageTriggerPre = new DamageTrigger.Pre(this, null);
        damageTriggerPost = new DamageTrigger.Post(this, null);

        tickTriggerPre = new TickTrigger.Pre(this, null);
        tickTriggerPost = new TickTrigger.Post(this, null);

        attackTriggerPre = new AttackTrigger.Pre(this, null);
        attackTriggerPost = new AttackTrigger.Post(this, null);

        tickTriggerPre.subscribe(this::doAi, Priority.HIGH);
    }


    public static class SpawnTrigger extends ActiveMobEvent<SpawnTrigger>
    {
        public SpawnTrigger(ActiveMob<?> myMob)
        {
            super(myMob);
        }

        @Override
        public SpawnTrigger newEvent()
        {
            return new SpawnTrigger(myMob);
        }

        @Override
        public Entity getSecondaryEntity()
        {
            return null;
        }

        // TRIGGERED IN "Wave.placeMobs()"
        // TODO: Move when rework placement to per tick
    }

    public static class DamageTrigger
    {
        public static class Pre extends ActiveMobInheritanceEvent<LivingDamageEvent.Pre,Pre>
        {

            public Pre(ActiveMob<?> myMob, LivingDamageEvent.Pre sourceEvent)
            {
                super(myMob, sourceEvent);
            }

            @Override
            public Pre newEvent(LivingDamageEvent.Pre sourceEvent)
            {
                return new Pre(myMob, sourceEvent);
            }

            @Override
            public Entity getSecondaryEntity()
            {
                return sourceEvent.getSource().getEntity();
            }
        }

        public static class Post extends ActiveMobInheritanceEvent<LivingDamageEvent.Post, Post>
        {
            public Post(ActiveMob<?> myMob, LivingDamageEvent.Post sourceEvent)
            {
                super(myMob, sourceEvent);
            }

            @Override
            public Post newEvent(LivingDamageEvent.Post sourceEvent)
            {
                return new Post(myMob, sourceEvent);
            }

            @Override
            public Entity getSecondaryEntity()
            {
                return sourceEvent.getSource().getEntity();
            }
        }
    }

    public static class TickTrigger
    {
        public static class Pre extends ActiveMobInheritanceEvent<EntityTickEvent.Pre, Pre>
        {
            public Pre(ActiveMob<?> myMob, EntityTickEvent.Pre sourceEvent)
            {
                super(myMob, sourceEvent);
            }

            @Override
            public Pre newEvent(EntityTickEvent.Pre sourceEvent)
            {
                return new Pre(myMob, sourceEvent);
            }

            @Override
            public Entity getSecondaryEntity()
            {
                return null;
            }
        }

        public static class Post extends ActiveMobInheritanceEvent<EntityTickEvent.Post, Post>
        {
            public Post(ActiveMob<?> myMob, EntityTickEvent.Post sourceEvent)
            {
                super(myMob, sourceEvent);
            }

            @Override
            public Post newEvent(EntityTickEvent.Post sourceEvent)
            {
                return new Post(myMob, sourceEvent);
            }

            @Override
            public Entity getSecondaryEntity()
            {
                return null;
            }
        }
    }

    public static class AttackTrigger
    {
        public static class Pre extends ActiveMobInheritanceEvent<LivingDamageEvent.Pre, Pre>
        {
            public Pre(ActiveMob<?> myMob, LivingDamageEvent.Pre sourceEvent)
            {
                super(myMob, sourceEvent);
            }

            @Override
            public Pre newEvent(LivingDamageEvent.Pre sourceEvent)
            {
                return new Pre(myMob, sourceEvent);
            }

            @Override
            public Entity getSecondaryEntity()
            {
                return sourceEvent.getEntity();
            }
        }

        public static class Post extends ActiveMobInheritanceEvent<LivingDamageEvent.Post, Post>
        {
            public Post(ActiveMob<?> myMob, LivingDamageEvent.Post sourceEvent)
            {
                super(myMob, sourceEvent);
            }

            @Override
            public Post newEvent(LivingDamageEvent.Post sourceEvent)
            {
                return new Post(myMob, sourceEvent);
            }

            @Override
            public Entity getSecondaryEntity()
            {
                return sourceEvent.getEntity();
            }
        }
    }

    public void doAi(TickTrigger.Pre ignored)
    {
        if(aiTick.isActivationTick())
        {
            RegisterAi.aiTick(this);
        }
    }
}
