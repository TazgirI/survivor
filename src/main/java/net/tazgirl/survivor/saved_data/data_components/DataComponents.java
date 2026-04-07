package net.tazgirl.survivor.saved_data.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tazgirl.survivor.Survivor;

import java.util.function.Supplier;

public class DataComponents
{
    private static final DeferredRegister.DataComponents COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Survivor.MODID);


    private static final Codec<waveLimitItemRecord> WAVE_LIMIT_ITEM_CODEC = RecordCodecBuilder.create(instance -> instance.group
            (Codec.INT.fieldOf("wave").forGetter(waveLimitItemRecord::wave)
            ).apply(instance, waveLimitItemRecord::new));

    public static final StreamCodec<ByteBuf, waveLimitItemRecord> WAVE_LIMIT_ITEM_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, waveLimitItemRecord::wave,
            waveLimitItemRecord::new
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<waveLimitItemRecord>> WAVE_LIMIT_ITEM = COMPONENT_TYPES.registerComponentType("wave_limit_item",builder -> builder.persistent(WAVE_LIMIT_ITEM_CODEC).networkSynchronized(WAVE_LIMIT_ITEM_STREAM_CODEC));


    public record waveLimitItemRecord(Integer wave){}


}
