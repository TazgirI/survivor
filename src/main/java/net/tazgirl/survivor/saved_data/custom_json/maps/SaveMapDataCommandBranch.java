package net.tazgirl.survivor.saved_data.custom_json.maps;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.Logging;
import net.neoforged.fml.common.EventBusSubscriber;
import net.tazgirl.survivor.SurvivorLogging;
import net.tazgirl.survivor.events.CollectCommandBranchesEvent;
import net.tazgirl.tutilz.chat.SendMessage;
import net.tazgirl.tutilz.commands.ArgBranch;
import net.tazgirl.tutilz.commands.ArgPair;

import java.util.List;

@EventBusSubscriber
public class SaveMapDataCommandBranch
{
    final static String branchName = "savedData";

    static final ArgBranch[] argPairs = List.of(
            ArgBranch.New("export", SaveMapDataCommandBranch::export, List.of(new ArgPair("sourcePosition", new BlockPosArgument()), new ArgPair("targetPosition", new BlockPosArgument()), new ArgPair("mapName", StringArgumentType.word()))),
            ArgBranch.New("view",SaveMapDataCommandBranch::view, List.of(new ArgPair("mapName", StringArgumentType.word()), new ArgPair("position", new BlockPosArgument())))
    ).toArray(new ArgBranch[0]);

    @SubscribeEvent
    public static void OnRegisterCommands(CollectCommandBranchesEvent event)
    {
        event.AddBranch(branchName, argPairs);
    }

    private static boolean export(CommandContext<?> commandContext)
    {
        if(!(commandContext.getSource() instanceof CommandSourceStack))
        {
            return false;
        }

        CommandContext<CommandSourceStack> ctx = (CommandContext<CommandSourceStack>) commandContext;

        Vec3 sourcePos = Vec3.atLowerCornerOf(BlockPosArgument.getBlockPos(ctx, "sourcePosition"));
        Vec3 targetPos = Vec3.atLowerCornerOf(BlockPosArgument.getBlockPos(ctx, "targetPosition"));
        String mapName = StringArgumentType.getString(ctx, "mapName");

        boolean result = false;

        try
        {
            result = SaveMap.savePoints(sourcePos, targetPos, mapName);
        }
        catch (CommandSyntaxException ignored)
        {
            SurvivorLogging.Error("Ran into an error while creating map blocks");

        }

        return result;
    }


    private static boolean view(CommandContext<?> commandContext)
    {
        if(!(commandContext.getSource() instanceof CommandSourceStack))
        {
            return false;
        }

        CommandContext<CommandSourceStack> ctx = (CommandContext<CommandSourceStack>) commandContext;

        BlockPos position = BlockPosArgument.getBlockPos(ctx, "position");
        String mapName = StringArgumentType.getString(ctx, "mapName");

        boolean result = false;

        try
        {
            result = SaveMap.view(position, mapName);
        }
        catch (CommandSyntaxException ignored)
        {
            SurvivorLogging.Error("Ran into an error while creating map blocks");
        }

        if(!result && ((CommandSourceStack) commandContext.getSource()).isPlayer())
        {
            SendMessage.Specific("Failed to read the specified map", ((CommandSourceStack) commandContext.getSource()).getPlayer());
        }

        return result;
    }

}
