package net.tazgirl.survivor.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.registries.ClientRegistryManager;
import net.tazgirl.survivor.translation_keys.keybinds;

@EventBusSubscriber
public class Keybinds
{
    public static final KeyMapping openRelics = new KeyMapping(keybinds.OPEN_RELIC_MENU.getLocation(), InputConstants.KEY_I, keybinds.SURVIVOR_CATEGORY.getLocation());

    @SubscribeEvent
    public static void OnClientSetup(RegisterKeyMappingsEvent event)
    {
        event.register(openRelics);
    }
}
