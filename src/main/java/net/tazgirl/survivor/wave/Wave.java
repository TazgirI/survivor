package net.tazgirl.survivor.wave;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.tazgirl.survivor.EntityTickTest;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.events.WaveStartedEvent;
import net.tazgirl.survivor.misc_game_stuff.CoreGameData;
import net.tazgirl.survivor.saved_data.data_attachments.DataAttachments;
import net.tazgirl.survivor.mobs.ActiveMob;
import net.tazgirl.tutilz.admin.Logging;
import org.apache.logging.log4j.core.Core;

import java.util.*;
import java.util.stream.Collectors;

public class Wave
{
    public Map<LivingEntity, ActiveMob<?>> mobsInWave;

    private List<ActiveMob<?>> mobsToPlace = new ArrayList<>();
    static List<Map.Entry<Vec3, Vec2>> spawns = CoreGameData.getMobSpawnPoints().entrySet().stream().toList();
    Random spawnRandom;

    int remainingMobs;

    public Wave(LinkedHashMap<LivingEntity, ActiveMob<?>> mobsInWave)
    {
        this.mobsInWave = mobsInWave;
        this.remainingMobs = mobsInWave.size();

        spawnRandom = new Random(CoreGameData.seedHolder.waveSeed.newSeed());
    }


    public void placeMobs()
    {
        if(Globals.server == null) {return;}

        mobsToPlace = new ArrayList<>(mobsInWave.values());

        spawns = CoreGameData.getMobSpawnPoints().entrySet().stream().toList();

        WaveStartedEvent.postEvent();
    }

    public int updateCount()
    {
        remainingMobs = countMobs();

        return remainingMobs;
    }

    public boolean waveFinished()
    {
        return remainingMobs <= 0;
    }


    public int countMobs()
    {
        if(Globals.server == null){return -1;}

        int total = Globals.server.overworld().getEntities(EntityTypeTest.forClass(LivingEntity.class), e -> e.getData(DataAttachments.WAVE_MOB).isWaveMob()).size();


        if(total <= 0)
        {
            Logging.Warn("Negative number of remaining wave mobs counted " + total, Survivor.LOGGER);
        }
        else
        {
            Logging.Log("Counted " + total + " mobs remaining in wave", Survivor.LOGGER);
        }

        return total;
    }

    public int getTotalRemainingMobs()
    {
        return remainingMobs;
    }

    public boolean mobsPlaced()
    {
        return mobsToPlace.isEmpty();
    }

    @EventBusSubscriber
    static class eventChecker
    {
        @SubscribeEvent
        public static void onServerTick(ServerTickEvent.Pre event)
        {
            if(CoreGameData.currentWave != null && !CoreGameData.currentWave.mobsToPlace.isEmpty())
            {
                Wave currentWave = CoreGameData.currentWave;

                Map.Entry<Vec3, Vec2> spawn = spawns.get(currentWave.spawnRandom.nextInt(0, spawns.size()));;

                // Closer to front == higher chance of being big high cost mob
                ActiveMob<?> activeMob = currentWave.mobsToPlace.removeLast();;
                LivingEntity entity = activeMob.entity;

                entity.setPos(spawn.getKey());
                entity.setYRot(spawn.getValue().y);
                entity.setXRot(spawn.getValue().x);

                Globals.overworld.addFreshEntity(entity);

                activeMob.spawnTrigger.fire();
            }
        }

        @SubscribeEvent
        public static void onEntityTick(EntityTickEvent.Pre event)
        {
            if(event.getEntity() instanceof LivingEntity livingEntity && CoreGameData.currentWave != null)
            {
                ActiveMob<?> activeMob = CoreGameData.currentWave.mobsInWave.get(livingEntity);

                if(activeMob != null)
                {
                    activeMob.tickTriggerPre.fire(event);
                }
            }
        }

        @SubscribeEvent
        public static void onEntityTick(EntityTickEvent.Post event)
        {
            if(event.getEntity() instanceof LivingEntity livingEntity && CoreGameData.currentWave != null)
            {
                ActiveMob<?> activeMob = CoreGameData.currentWave.mobsInWave.get(livingEntity);

                if(activeMob != null)
                {
                    activeMob.tickTriggerPost.fire(event);
                }
            }
        }

        @SubscribeEvent()
        public static void onLivingDamagePre(LivingDamageEvent.Pre event)
        {
            if(CoreGameData.currentWave == null)
            {
                Logging.Warn("Living damage pre fired but no wave has been created yet", Survivor.LOGGER);
                return;
            }

            if(event.getSource().getEntity() instanceof LivingEntity livingEntity)
            {
                ActiveMob<?> attacker = CoreGameData.currentWave.mobsInWave.get(livingEntity);

                if(attacker != null)
                {
                    attacker.attackTriggerPre.fire(event);
                }
            }

            ActiveMob<?> target = CoreGameData.currentWave.mobsInWave.get(event.getEntity());
            if(target != null)
            {
                target.damageTriggerPre.fire(event);
            }
        }

//        @SubscribeEvent
//        public static void onEntityDeath(LivingDeathEvent event)
//        {
//            CoreGameData.currentWave.mobsInWave.remove(event.getEntity());
//        }
    }
}
