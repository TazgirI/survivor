package net.tazgirl.survivor.saved_data.mob_spawn_points;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.translation_keys.messages.mob_spawn_points;
import net.tazgirl.tutilz.commands.Translatable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManageMobSpawnPoints
{

    static final String mobSpawnPointName = "MobSpawnPoint";

    public static boolean Save(CommandContext<?> source)
    {
        if(!(source.getSource() instanceof CommandSourceStack))
        {
            return false;
        }

        CommandContext<CommandSourceStack> ctx = (CommandContext<CommandSourceStack>) source;

        MobSpawnsMapSavedData mobSpawnsMapSavedData = MobSpawnsMapSavedData.getForOverworld();

        List<ArmorStand> armorStands = new ArrayList<>(ctx.getSource().getLevel().getEntities(EntityTypeTest.forClass(ArmorStand.class), ManageMobSpawnPoints::HasName));

        if(armorStands.isEmpty())
        {
            ctx.getSource().sendSuccess(() -> Component.translatable(mob_spawn_points.SAVE_FAILURE.getLocation()),true);
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

        Translatable.Success(ctx.getSource(),mob_spawn_points.SAVE_SUCCESS.getLocation(), finalSaved);


        return true;
    }

    public static boolean Clear(CommandContext<?> source)
    {
        if(!(source.getSource() instanceof CommandSourceStack))
        {
            return false;
        }

        CommandContext<CommandSourceStack> ctx = (CommandContext<CommandSourceStack>) source;

        Translatable.Success(ctx.getSource(),mob_spawn_points.CLEAR_SUCCESS.getLocation());

        MobSpawnsMapSavedData.getForOverworld().clearSpawnPoints();
        return true;
    }

    public static boolean Show(CommandContext<?> source)
    {
        if(!(source.getSource() instanceof CommandSourceStack))
        {
            return false;
        }

        CommandContext<CommandSourceStack> ctx = (CommandContext<CommandSourceStack>) source;

        MobSpawnsMapSavedData mobSpawnsMapSavedData = MobSpawnsMapSavedData.getForOverworld();

        if(mobSpawnsMapSavedData.getMobSpawnsMap().isEmpty()){return false;}

        ServerLevel overworld = ctx.getSource().getServer().overworld();

        Vec3 vec3;
        Vec2 vec2;
        ArmorStand armorStand;

        for(Map.Entry<Vec3, Vec2> entry: mobSpawnsMapSavedData.getMobSpawnsMap().entrySet())
        {
            spawnSpawnpointArmorStand(entry.getKey(), entry.getValue());
        }

        Translatable.Success(ctx.getSource(), mob_spawn_points.SHOW_SUCCESS.getLocation(), mobSpawnsMapSavedData.getMobSpawnsMap().size());



        return true;
    }

    public static void spawnSpawnpointArmorStand(Vec3 position, Vec2 rotation)
    {
        ArmorStand armorStand = new ArmorStand(Globals.overworld, position.x - 0.5, position.y, position.z + 0.5);
        armorStand.absRotateTo(rotation.y, rotation.x);
        armorStand.setCustomName(Component.literal(mobSpawnPointName));
        armorStand.setNoGravity(true);

        Globals.overworld.addFreshEntity(armorStand);
    }
    public static boolean ShowAndClear(CommandContext<?> source)
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
