package net.tazgirl.survivor.main_game.wave;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.main_game.CoreGameData;
import net.tazgirl.survivor.main_game.data.seeds.structure.SeedHolder;
import net.tazgirl.survivor.saved_data.data_attachments.DataAttachments;
import net.tazgirl.survivor.main_game.mobs.ActiveMob;
import net.tazgirl.tutilz.admin.Logging;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Wave
{
    public Map<Entity, ActiveMob<?>> mobsInWave;

    int remainingMobs;

    public Wave(Map<Entity, ActiveMob<?>> mobsInWave)
    {
        this.mobsInWave = mobsInWave;
        this.remainingMobs = mobsInWave.size();
    }


    public void placeMobs()
    {
        if(Globals.server == null) {return;}

        List<Map.Entry<Vec3, Vec2>> spawns = CoreGameData.getMobSpawnPoints().entrySet().stream().toList();
        Map.Entry<Vec3, Vec2> spawn;

        Random random = new Random(CoreGameData.seedHolder.waveSeed.newSeed());


        for(Map.Entry<Entity, ActiveMob<?>> entry : mobsInWave.entrySet())
        {
            Entity entity = entry.getKey();
            spawn = spawns.get(random.nextInt(0, spawns.size()));

            entity.setPos(spawn.getKey());
            entity.setYRot(spawn.getValue().y);
            entity.setXRot(spawn.getValue().x);

            Globals.overworld.addFreshEntity(entity);

            entry.getValue().spawnTrigger.fire();
        }
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

        int total = 0;
        for(Entity entity : Globals.server.overworld().getAllEntities())
        {
            if(entity.getData(DataAttachments.WAVE_MOB).isWaveMob())
            {
                total++;
            }
        }

        if(total < 0)
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
}
