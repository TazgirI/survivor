package net.tazgirl.survivor.main_game.relics;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.tazgirl.survivor.main_game.players.PlayerData;
import net.tazgirl.survivor.main_game.players.PlayerEvent;
import net.tazgirl.survivor.main_game.players.RegisterPlayer;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class Relic<T extends PlayerEvent<T>> extends Item
{
    public RelicProperties relicProperties;

    public Relic(Properties properties, RelicProperties relicProperties)
    {
        super(properties);
        this.relicProperties = relicProperties;
    }

    public abstract T getEvent(PlayerData data);

    public abstract Consumer<T> eventConsumer();

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.SPYGLASS;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity)
    {
        return 16;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity)
    {
        UUID uuid;
        if(livingEntity instanceof ServerPlayer player)
        {
            uuid = player.getUUID();
        }
        else
        {
            return stack;
        }

        PlayerData data = RegisterPlayer.get(uuid);

        if(data == null)
        {
            return stack;
        }

        data.relics.add(this);


        T event = getEvent(data);
        event.subscribe(eventConsumer(), relicProperties.priority);


        stack.shrink(1);
        return stack;
    }


}
