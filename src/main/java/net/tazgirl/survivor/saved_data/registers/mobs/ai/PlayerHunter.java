package net.tazgirl.survivor.saved_data.registers.mobs.ai;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.tazgirl.survivor.buffs.Buff;
import net.tazgirl.survivor.mobs.ActiveMob;
import net.tazgirl.survivor.players.PlayerData;
import net.tazgirl.survivor.players.RegisterPlayer;
import net.tazgirl.survivor.saved_data.data_attachments.DataAttachments;
import net.tazgirl.survivor.vanilla_registration.Attributes;

public class PlayerHunter extends RegisterAi
{
    static final double huntRange = 10;

    @Override
    void setObjective(ActiveMob<?> activeMob)
    {
        if(activeMob.entity instanceof Mob mob && !mob.level().isClientSide())
        {
            LivingEntity target = mob.getTarget();

            if(target instanceof Player player && mob.distanceTo(player) <= huntRange)
            {
                return;
            }

            ServerPlayer lastHurtBy = mob.getData(DataAttachments.LAST_HURT_BY_PLAYER.get()).getPlayer();
            PlayerData closestPlayer = null;
            double closestDistance = Double.MAX_VALUE;
            double isRawFlag = 0;
            for(PlayerData playerData : RegisterPlayer.livingValues())
            {
                ServerPlayer player = playerData.player;
                double threat = playerData.getBuffedValue(Buff.Type.THREAT, player.getAttributeValue(Attributes.THREAT.getDelegate()));
                double rawDistance = mob.distanceTo(player);
                double threatDistance = rawDistance - threat;
                threatDistance = threatDistance + (Math.abs(threatDistance) * isRawFlag);

                if(lastHurtBy == player)
                {
                    threatDistance += (Math.abs(threatDistance) * 0.25);
                }

                if(rawDistance <= huntRange / 2 && rawDistance < closestDistance)
                {
                    closestPlayer = playerData;
                    closestDistance = rawDistance;
                    isRawFlag = 0.5;
                }
                else if(threatDistance < closestDistance)
                {
                    closestPlayer = playerData;
                    closestDistance = threatDistance;
                    isRawFlag = 0;
                }
            }

            if(closestPlayer != null)
            {
                mob.setTarget(closestPlayer.player);
            }
        }
    }
}
