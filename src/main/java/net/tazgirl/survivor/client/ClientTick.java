package net.tazgirl.survivor.client;

import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.tazgirl.survivor.gui.relic_inventory.RelicInventoryScreen;

@EventBusSubscriber
public class ClientTick
{
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event)
    {
        if(Keybinds.openRelics.consumeClick())
        {
            Minecraft.getInstance().setScreen(new RelicInventoryScreen());
        }
    }
}
