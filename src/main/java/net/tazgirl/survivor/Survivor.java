package net.tazgirl.survivor;

import net.tazgirl.survivor.misc_game_stuff.GameState;
import net.tazgirl.survivor.saved_data.data_attachments.DataAttachments;
import net.tazgirl.survivor.vanilla_registration.Attributes;
import net.tazgirl.survivor.vanilla_registration.Items;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Survivor.MODID)
public class Survivor
{

    public static final String MODID = "survivor";

    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Survivor(IEventBus modEventBus, ModContainer modContainer) {

        modEventBus.addListener(this::commonSetup);


        DataAttachments.register(modEventBus);

        Items.register(modEventBus);
        Attributes.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {

    }

    public static GameState.State gamestate()
    {
        return GameState.currentState;
    }
}
