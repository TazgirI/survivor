package net.tazgirl.survivor.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber
public class RunDatagen
{

    @SubscribeEvent
    public static void OnGatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if(event.includeClient())
        {
            generator.addProvider(true, new ItemModels(output, existingFileHelper));
            generator.addProvider(true, new Lang(output, "en_us"));
        }

        if(event.includeServer())
        {
            // recipe
            // tag
            // loot
        }
    }

}
