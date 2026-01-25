package net.tazgirl.survivor.saved_data.data_attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.tazgirl.survivor.Survivor;

import java.util.function.Supplier;

public class DataAttachments
{
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Survivor.MODID);



    private static final Codec<waveMobRecord> WAVE_MOB_CODEC = RecordCodecBuilder.create(instance -> instance.group
            (Codec.BOOL.fieldOf("isWaveMob").forGetter(waveMobRecord::isWaveMob)
            ).apply(instance, waveMobRecord::new));


    public static final Supplier<AttachmentType<waveMobRecord>> WAVE_MOB = ATTACHMENT_TYPES.register("wave_mob",() -> AttachmentType.builder(() -> new waveMobRecord(false)).serialize(WAVE_MOB_CODEC).build());


    public record waveMobRecord(Boolean isWaveMob){}



    public static void register(IEventBus eventBus)
    {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
