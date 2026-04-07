package net.tazgirl.survivor.boons;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.tazgirl.survivor.misc_game_stuff.CoreGameData;
import net.tazgirl.survivor.players.PlayerData;
import net.tazgirl.survivor.players.PlayerEvent;
import net.tazgirl.survivor.players.RegisterPlayer;
import net.tazgirl.survivor.saved_data.data_components.DataComponents;

import java.util.UUID;
import java.util.function.Consumer;

public abstract class Boon<T extends PlayerEvent<T>> extends Item
{
    public BoonProperties boonProperties;

    public Boon(Properties properties, BoonProperties boonProperties)
    {
        super(properties);
        this.boonProperties = boonProperties;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand)
    {
        player.startUsingItem(usedHand);

        return super.use(level, player, usedHand);
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

        data.boons.add(this);


        T event = getEvent(data);
        event.subscribe(eventConsumer(), boonProperties.priority);


        stack.shrink(1);
        return stack;
    }

    @Override
    public ItemStack getDefaultInstance()
    {
        ItemStack stack = super.getDefaultInstance();
        stack.set(DataComponents.WAVE_LIMIT_ITEM.get(), new DataComponents.waveLimitItemRecord(CoreGameData.waveNum));
        return stack;
    }
}
