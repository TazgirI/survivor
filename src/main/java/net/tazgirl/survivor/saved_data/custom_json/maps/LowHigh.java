package net.tazgirl.survivor.saved_data.custom_json.maps;

import net.minecraft.world.phys.Vec3;

public record LowHigh(Vec3 lowPos, Vec3 highPos)
{
    public static LowHigh from(Vec3 sourcePos, Vec3 targetPos)
    {
        Vec3 low = new Vec3(0, sourcePos.y,0);
        Vec3 high = new Vec3(0, targetPos.y, 0);

        if(sourcePos.x < targetPos.x)
        {
            low = low.add(sourcePos.x, 0, 0);
            high = high.add(targetPos.x, 0, 0);
        }
        else
        {
            low = low.add(targetPos.x, 0, 0);
            high = high.add(sourcePos.x, 0, 0);
        }

        if(sourcePos.z < targetPos.z)
        {
            low = low.add(0, 0, sourcePos.z);
            high = high.add(0, 0, targetPos.z);
        }
        else
        {
            low = low.add(0, 0, targetPos.z);
            high = high.add(0, 0, sourcePos.z);
        }

        return new LowHigh(low, high);
    }
}
