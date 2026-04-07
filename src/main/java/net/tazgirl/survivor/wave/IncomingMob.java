package net.tazgirl.survivor.wave;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.mobs.ActiveMob;
import net.tazgirl.survivor.mobs.modifiers.storage.ModifierStorageRecord;
import net.tazgirl.survivor.mobs.modifiers.storage.ModifierStorageSet;
import net.tazgirl.survivor.mobs.wave_mobs.WaveMob;
import net.tazgirl.survivor.saved_data.data_attachments.DataAttachments;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class IncomingMob<T extends LivingEntity>
{
    public EntityType<T> myType;
    public WaveMob<T> waveMob;

    public ModifierStorageSet modifierStorageSet = new ModifierStorageSet(List.of());

    public IncomingMob(WaveMob<T> waveMob)
    {
        this.waveMob = waveMob;
        this.myType = (EntityType<T>) waveMob.getEntityType();
    }

    public ActiveMob<?> constructActive()
    {
        ActiveMob<?> activeMob = new ActiveMob<>(myType.create(Globals.overworld), waveMob);

        activeMob.entity.setData(DataAttachments.WAVE_MOB, new DataAttachments.waveMobRecord(true));

        for(ModifierStorageRecord<?> storageRecord : modifierStorageSet.getModifiers())
        {
            // Automatically subscribes and stores itself
            storageRecord.constructActive(activeMob);
        }

        return activeMob;
    }

    public void addModifier(ModifierStorageRecord<?> record)
    {
        if(modifierStorageSet == null)
        {
            modifierStorageSet = new ModifierStorageSet(List.of(record));
        }
        else
        {
            modifierStorageSet.addMobModifier(record);
        }
    }
}
