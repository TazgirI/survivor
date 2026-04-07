package net.tazgirl.survivor.saved_data.interactive_positions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.phys.AABB;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.saved_data.SavedDataNames;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InteractivePositionsSavedData extends SavedData
{
    private Map<BlockPos, String> interactablesMap = new HashMap<>();

    public static InteractivePositionsSavedData create()
    {
        return new InteractivePositionsSavedData();
    }

    public static boolean show = false;

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider provider)
    {
        ListTag listTag = new ListTag();


        CompoundTag pairTag;
        BlockPos blockPos;
        String address;

        for(Map.Entry<BlockPos, String> entry: interactablesMap.entrySet())
        {
            pairTag = new CompoundTag();
            blockPos = entry.getKey();
            address = entry.getValue();

            pairTag.putInt("3x", blockPos.getX());
            pairTag.putInt("3y", blockPos.getY());
            pairTag.putInt("3z", blockPos.getZ());

            pairTag.putString("address", address);

            listTag.add(pairTag);
        }

        tag.put(SavedDataNames.INTERACTION_POINTS_ADDRESS.string, listTag);
        return tag;
    }

    public static InteractivePositionsSavedData load(CompoundTag compoundTag, HolderLookup.Provider lookupProvider)
    {
        InteractivePositionsSavedData interactivePositionsSavedData = new InteractivePositionsSavedData();

        ListTag listTag = compoundTag.getList(SavedDataNames.INTERACTION_POINTS_ADDRESS.string, 10);

        CompoundTag pairTag;

        BlockPos position;
        String address;

        for (int i = 0; i < listTag.size(); i++)
        {
            pairTag = listTag.getCompound(i);

            position = new BlockPos(pairTag.getInt("3x"),pairTag.getInt("3y"),pairTag.getInt("3z"));
            address = pairTag.getString("address");

            interactivePositionsSavedData.interactablesMap.put(position, address);
        }

        return interactivePositionsSavedData;
    }

    public Map<BlockPos, String> getInteractionPositionsMap()
    {
        return interactablesMap;
    }

    public void addInteractionPoint(BlockPos position, String address)
    {
        interactablesMap.put(position, address);
        this.setDirty();

        if(show)
        {
            ManageInteractionPositions.addInteractionPositionVisual(position, address);
        }
    }

    public void removeInteractionPoint(BlockPos position)
    {
        String string = interactablesMap.remove(position);
        if(string != null)
        {
            this.setDirty();
            if(show)
            {
                List<ArmorStand> armourStands = Globals.overworld.getEntitiesOfClass(ArmorStand.class, new AABB(position));
                if(!armourStands.isEmpty())
                {
                    armourStands.forEach(ArmorStand::discard);
                }
            }
        }
    }

    void dangerClearAll()
    {
        interactablesMap = new HashMap<>();
        this.setDirty();
    }

    public static InteractivePositionsSavedData getForOverworld()
    {
        return Globals.overworldData.computeIfAbsent(new Factory<>(InteractivePositionsSavedData::create, InteractivePositionsSavedData::load), SavedDataNames.INTERACTION_POINTS_ADDRESS.string);
    }


}