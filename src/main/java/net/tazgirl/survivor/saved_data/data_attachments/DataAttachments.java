package net.tazgirl.survivor.saved_data.data_attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.Survivor;

import java.util.UUID;
import java.util.function.Supplier;

public class DataAttachments
{
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Survivor.MODID);



    private static final Codec<waveMobRecord> WAVE_MOB_CODEC = RecordCodecBuilder.create(instance -> instance.group
            (Codec.BOOL.fieldOf("isWaveMob").forGetter(waveMobRecord::isWaveMob)
            ).apply(instance, waveMobRecord::new));

    // TODO: Review implementation and ensure it is updated when necessary
    private static final Codec<lastHurtByPlayerRecord> LAST_HURT_BY_PLAYER_CODEC = RecordCodecBuilder.create(instance -> instance.group
            (Codec.STRING.fieldOf("uuid").forGetter(lastHurtByPlayerRecord::uuid)
            ).apply(instance, lastHurtByPlayerRecord::new));


    public static final Supplier<AttachmentType<waveMobRecord>> WAVE_MOB = ATTACHMENT_TYPES.register("wave_mob",() -> AttachmentType.builder(() -> new waveMobRecord(false)).serialize(WAVE_MOB_CODEC).build());
    public static final Supplier<AttachmentType<lastHurtByPlayerRecord>> LAST_HURT_BY_PLAYER = ATTACHMENT_TYPES.register("last_hurt_by_player",() -> AttachmentType.builder(() -> new lastHurtByPlayerRecord("")).serialize(LAST_HURT_BY_PLAYER_CODEC).build());


    public record waveMobRecord(Boolean isWaveMob){}
    public record lastHurtByPlayerRecord(String uuid)
    {
        public ServerPlayer getPlayer()
        {
            if(Globals.server != null && Globals.server.overworld().getEntity(UUID.fromString(uuid)) instanceof ServerPlayer player)
            {
                return player;
            }

            return null;
        }
    }






    public static void register(IEventBus eventBus)
    {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
