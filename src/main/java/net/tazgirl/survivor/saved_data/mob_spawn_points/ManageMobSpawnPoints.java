package net.tazgirl.survivor.saved_data.mob_spawn_points;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.saved_data.SavedDataNames;
import net.tazgirl.survivor.translation_keys.messages.mob_spawn_points;
import net.tazgirl.tutilz.admin.Logging;
import net.tazgirl.tutilz.commands.Translatable;
import oshi.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ManageMobSpawnPoints
{

    static final String mobSpawnPointName = "MobSpawnPoint";

    public static boolean Save(CommandSourceStack source)
    {
        MobSpawnsMapSavedData mobSpawnsMapSavedData = MobSpawnsMapSavedData.getForOverworld();

        List<ArmorStand> armorStands = new ArrayList<>(source.getLevel().getEntities(EntityTypeTest.forClass(ArmorStand.class), ManageMobSpawnPoints::HasName));

        if(armorStands.isEmpty())
        {
            source.sendSuccess(() -> Component.translatable(mob_spawn_points.SAVE_FAILURE.getLocation()),true);
            return false;
        }


        int saved = 0;

        for(ArmorStand armorStand: armorStands)
        {
            if(mobSpawnsMapSavedData.getMobSpawnsMap().get(armorStand.position()) != armorStand.getRotationVector())
            {
                saved++;
                mobSpawnsMapSavedData.addSpawnPoint(armorStand.position(), armorStand.getRotationVector());
            }
            armorStand.discard();
        }


        int finalSaved = saved;

        Translatable.Success(source,mob_spawn_points.SAVE_SUCCESS.getLocation(), finalSaved);


        return true;
    }

    public static boolean Clear(CommandSourceStack source)
    {
        Translatable.Success(source,mob_spawn_points.CLEAR_SUCCESS.getLocation());

        MobSpawnsMapSavedData.getForOverworld().clearSpawnPoints();
        return true;
    }

    public static boolean Show(CommandSourceStack source)
    {
        MobSpawnsMapSavedData mobSpawnsMapSavedData = MobSpawnsMapSavedData.getForOverworld();

        if(mobSpawnsMapSavedData.getMobSpawnsMap().isEmpty()){return false;}

        ServerLevel overworld = source.getServer().overworld();

        Vec3 vec3;
        Vec2 vec2;
        ArmorStand armorStand;

        for(Map.Entry<Vec3, Vec2> entry: mobSpawnsMapSavedData.getMobSpawnsMap().entrySet())
        {
            vec3 = entry.getKey();
            vec2 = entry.getValue();

            armorStand = new ArmorStand(overworld, vec3.x, vec3.y, vec3.z);
            armorStand.absRotateTo(vec2.y, vec2.x);
            armorStand.setCustomName(Component.literal(mobSpawnPointName));

            overworld.addFreshEntity(armorStand);
        }

        Translatable.Success(source, mob_spawn_points.SHOW_SUCCESS.getLocation(), mobSpawnsMapSavedData.getMobSpawnsMap().size());



        return true;
    }

    public static boolean ShowAndClear(CommandSourceStack source)
    {
        return Show(source) && Clear(source);
    }

    static boolean HasName(Entity entity)
    {
        if(entity.getName().getString().equals(mobSpawnPointName))
        {
            return true;
        }

        return false;
    }


    static Component success(String message)
    {
        return Component.literal(message);
    }

}
