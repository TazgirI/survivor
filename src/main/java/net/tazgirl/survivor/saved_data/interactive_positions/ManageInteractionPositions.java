package net.tazgirl.survivor.saved_data.interactive_positions;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.misc_game_stuff.CoreGameData;
import net.tazgirl.survivor.players.PlayerData;
import net.tazgirl.survivor.players.RegisterPlayer;
import net.tazgirl.survivor.vanilla_registration.Items;
import net.tazgirl.tutilz.ScheduleTask;

import java.util.UUID;

@EventBusSubscriber
public class ManageInteractionPositions
{
    // TODO: At a much later data, fix this shitshow

    @SubscribeEvent
    public static void OnPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        if(!(event.getEntity() instanceof ServerPlayer))
        {
            return;
        }

        if(!event.getItemStack().is(Items.INTERACTION_POINT_TOOL))
        {
            final PlayerData playerData = RegisterPlayer.get(event.getEntity().getUUID());


            if(playerData.tickWhenRightClick == -1)
            {
                final UUID finalUuid = playerData.uuid;

                ScheduleTask.scheduleTask(makeTask(finalUuid, event, playerData), 1, ScheduleTask.Priority.POST);

            }

            playerData.tickWhenRightClick = CoreGameData.zeroedTicks;
        }
    }

    // WARN: Should be safe, hopefully
    // #memory
    private static Runnable makeTask(UUID finalUuid, PlayerInteractEvent.RightClickBlock finalEvent, PlayerData playerData)
    {
        return () ->
        {
            if(CoreGameData.zeroedTicks > RegisterPlayer.get(finalUuid).tickWhenRightClick + 5)
            {
                RegisterInteractionPositions.executeForBlock(finalEvent.getPos(), finalEvent);
                playerData.tickWhenRightClick = -1;
            }
            else
            {
                ScheduleTask.scheduleTask(makeTask(finalUuid, finalEvent, playerData), 1, ScheduleTask.Priority.POST);
            }
        };
    }

    public static void addInteractionPositionVisual(BlockPos blockPos, String name)
    {
        Vec3 pos = Vec3.atCenterOf(blockPos);

        ArmorStand armorStand = new ArmorStand(net.tazgirl.survivor.Globals.server.overworld(), pos.x, pos.y - 0.1, pos.z);
        armorStand.setGlowingTag(true);
        armorStand.setNoGravity(true);
        armorStand.setCustomName(Component.literal("Interaction Point: " + name));
        armorStand.getAttributes().getInstance(Attributes.SCALE.getDelegate()).setBaseValue(0.5);
        armorStand.setCustomNameVisible(true);

        Globals.server.overworld().addFreshEntity(armorStand);
    }

    static boolean hasName(Entity entity)
    {
        return entity.getName().getString().contains("Interaction Point: ");
    }
}
