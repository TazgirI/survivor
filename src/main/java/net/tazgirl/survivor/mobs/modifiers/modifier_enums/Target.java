package net.tazgirl.survivor.mobs.modifiers.modifier_enums;

import net.minecraft.world.entity.LivingEntity;
import net.tazgirl.survivor.SurvivorLogging;
import net.tazgirl.survivor.misc_game_stuff.CoreGameData;
import net.tazgirl.survivor.mobs.events.ActiveMobEvent;
import net.tazgirl.survivor.mobs.modifiers.storage.ModifierStorageRecord;
import net.tazgirl.survivor.players.PlayerData;
import net.tazgirl.survivor.players.RegisterPlayer;

import java.util.List;

public enum Target
{
    SELF,
    OTHER,
    RANDOM_PLAYER,
    RANDOM_WAVE_MOB;
    // TODO: Allow for a MagicJson predicate

    public static LivingEntity getTarget(ActiveMobEvent<?> event, ModifierStorageRecord<?> storageRecord)
    {
        return switch (storageRecord.target())
        {
            case OTHER ->
            {
                if(event.getSecondaryEntity() instanceof LivingEntity livingEntity)
                {
                    yield livingEntity;
                }
                else
                {
                    SurvivorLogging.Debug("Modifier is set to target \"OTHER\" but subscribes to an event with no OTHER", List.of("modifierStorageRecord", storageRecord, "event", event));
                    yield null;
                }
            }
            case RANDOM_PLAYER ->
            {
                List<PlayerData> playerDatas = RegisterPlayer.values().stream().toList();
                yield playerDatas.get(CoreGameData.seedHolder.modifierSeed.nextRandom().nextInt(0, playerDatas.size())).player;
            }
            case RANDOM_WAVE_MOB ->
            {
                List<LivingEntity> activeMobs = CoreGameData.currentWave.mobsInWave.keySet().stream().filter(LivingEntity::isAlive).toList();
                yield activeMobs.get(CoreGameData.seedHolder.modifierSeed.nextRandom().nextInt(0, activeMobs.size()));
            }
            case SELF -> event.myMob.entity;
            default -> null;
        };
    }
}
