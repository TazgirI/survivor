package net.tazgirl.survivor.wave_limited_items;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.misc_game_stuff.CoreGameData;
import net.tazgirl.survivor.saved_data.data_attachments.DataAttachments;
import net.tazgirl.survivor.saved_data.data_components.DataComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaveLimitedItems
{
    public static Map<ItemStack, Entity> activateOnProgress()
    {
        if(CoreGameData.currentWave == null)
        {
            return Map.of();
        }

        Map<ItemStack, Entity> returnMap = new HashMap<>();

        for(ServerPlayer player : Globals.server.getPlayerList().getPlayers())
        {
            for(ItemStack itemStack : player.getInventory().items)
            {
                if(willActivateOnProgress(itemStack))
                {
                    returnMap.put(itemStack, player);
                }
            }
        }

        for(ItemEntity itemEntity : Globals.overworld.getEntities(EntityTypeTest.forClass(ItemEntity.class), itemEntity -> willActivateOnProgress(itemEntity.getItem())))
        {
            returnMap.put(itemEntity.getItem(), itemEntity);
        }

        // TODO: Update if any storage becomes available and needs to be checked


        return returnMap;
    }

    public static boolean willActivateOnProgress(ItemStack itemStack)
    {
        DataComponents.waveLimitItemRecord record = itemStack.getComponents().get(DataComponents.WAVE_LIMIT_ITEM.get());
        if(record != null)
        {
            int wave = CoreGameData.waveNum;
            return record.wave() <= wave;
        }

        return false;
    }

}
