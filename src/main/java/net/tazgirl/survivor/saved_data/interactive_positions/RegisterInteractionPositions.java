package net.tazgirl.survivor.saved_data.interactive_positions;

import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.tazgirl.survivor.misc_game_stuff.CoreGameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RegisterInteractionPositions
{
    private static final Map<String, Consumer<PlayerInteractEvent.RightClickBlock>> register = new HashMap<>();

    public static void executeForBlock(BlockPos blockPos, PlayerInteractEvent.RightClickBlock event)
    {
        String address = CoreGameData.getInteractionPositions().get(blockPos);
        Consumer<PlayerInteractEvent.RightClickBlock> consumer = register.get(address);

        if(consumer != null)
        {
            consumer.accept(event);
        }
    }

    public static Collection<String> keySet()
    {
        return register.keySet();
    }

    public static class FetchEvent extends net.neoforged.bus.api.Event
    {
        public Consumer<PlayerInteractEvent.RightClickBlock> add(String string, Consumer<PlayerInteractEvent.RightClickBlock> consumer)
        {
            return register.put(string, consumer);
        }
    }
}
