package net.tazgirl.survivor.saved_data.custom_json.maps;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.SurvivorLogging;
import net.tazgirl.survivor.saved_data.custom_json.CustomJson;
import net.tazgirl.survivor.saved_data.interactive_positions.InteractivePositionsSavedData;
import net.tazgirl.survivor.saved_data.interactive_positions.ManageInteractionPositions;
import net.tazgirl.survivor.saved_data.mob_spawn_points.ManageMobSpawnPoints;
import net.tazgirl.survivor.saved_data.mob_spawn_points.MobSpawnsMapSavedData;
import net.tazgirl.tutilz.admin.Logging;
import net.tazgirl.tutilz.misc_helpers.interfaces.ThrowingConsumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SaveMap
{
    static String sourcePosKey = "sourcePos";
    static String targetPosKey = "targetPos";

    static String mobSpawnPointsKey = "mobSpawnPoints";

    static String interactionPointsKey = "interactionPoints";

    static String blocksKey = "blocks";
    static String blockData = "blockData";
    static String blockEntityData = "blockEntityData";

    static String entitiesKey = "entities";

    public static boolean savePoints(Vec3 sourcePos, Vec3 targetPos, String mapName) throws CommandSyntaxException
    {
        if(sourcePos.x == targetPos.x || sourcePos.z == targetPos.z)
        {
            Logging.Error("Source and target positions for saving maps data cannot be on the same x or z coordinates", Survivor.LOGGER);
            return false;
        }
        
        if(targetPos.y <= sourcePos.y)
        {
            Logging.Error("Source position for saving maps data cannot be higher than the target position along the y axis", Survivor.LOGGER);
            return false;
        }
        
        if(targetPos.y - 2 <= sourcePos.y)
        {
            Logging.Error("Source and target positions for saving maps data cannot be within 2 blocks along the y axis", Survivor.LOGGER);
            return false;
        }

        if(!Globals.overworld.getBlockState(BlockPos.containing(sourcePos)).is(Blocks.STRUCTURE_BLOCK) || !Globals.overworld.getBlockState(BlockPos.containing(targetPos)).is(Blocks.STRUCTURE_BLOCK))
        {
            Logging.Error("There is not a structure block at both the source position and target position", Survivor.LOGGER);
            return false;
        }
        
        Gson gson = new Gson();

        JsonObject finalObject = new JsonObject();

        JsonObject sourcePosObj = CustomJson.newPositionObject(sourcePos);
        JsonObject targetPosObj = CustomJson.newPositionObject(targetPos);

        finalObject.add(sourcePosKey, sourcePosObj);
        finalObject.add(targetPosKey, targetPosObj);

        JsonObject mobSpawnPoints = mobSpawnPoints(sourcePos, targetPos);
        finalObject.add(mobSpawnPointsKey, mobSpawnPoints);

        JsonObject interactionPoints = interactionPoints(sourcePos, targetPos);
        finalObject.add(interactionPointsKey, interactionPoints);

        JsonObject blocks = blocks(sourcePos, targetPos);
        finalObject.add(blocksKey, blocks);

        JsonObject entities = entities(sourcePos, targetPos);
        finalObject.add(entitiesKey, entities);

        String finalJson = gson.toJson(finalObject);

        if(!CustomJson.overwriteFile(CustomJson.mapPath(mapName), finalJson))
        {
            SurvivorLogging.Error("Failed to write map data to '" + CustomJson.mapPath(mapName) + "'");
            return false;
        }

        return true;
    }

    private static JsonObject mobSpawnPoints(Vec3 sourcePos, Vec3 targetPos)
    {
        Map<Vec3, Vec2> filteredSpawnPoints = filterMap(MobSpawnsMapSavedData.getForOverworld().getMobSpawnsMap(), sourcePos, targetPos);
        
        JsonObject returnObject = new JsonObject();
        JsonObject spawnObject = null;

        int i = 0;

        for(Map.Entry<Vec3, Vec2> entry : filteredSpawnPoints.entrySet())
        {
            Vec3 relativePos = entry.getKey().subtract(sourcePos);
            
            spawnObject = new JsonObject();

            spawnObject.addProperty(CustomJson.positionX, relativePos.x);
            spawnObject.addProperty(CustomJson.positionY, relativePos.y);
            spawnObject.addProperty(CustomJson.positionZ, relativePos.z);

            spawnObject.addProperty(CustomJson.rotationX, entry.getValue().x);
            spawnObject.addProperty(CustomJson.rotationY, entry.getValue().y);

            i++;

            returnObject.add(String.valueOf(i), spawnObject);
        }

        return returnObject;
    }

    private static JsonObject blocks(Vec3 sourcePos, Vec3 targetPos) throws CommandSyntaxException
    {
        AtomicInteger i = new AtomicInteger();

        JsonObject returnObject = new JsonObject();

        loop3D(pos ->
        {
            CompoundTag tag = new CompoundTag();
            tag.put(blockData, NbtUtils.writeBlockState(Globals.overworld.getBlockState(BlockPos.containing(pos))));

            BlockEntity blockEntity = Globals.overworld.getBlockEntity(BlockPos.containing(pos));

            if(blockEntity != null)
            {
                tag.put(blockEntityData, blockEntity.saveWithFullMetadata(Globals.overworld.registryAccess()));
            }

            String blockTagString = tag.toString();

            returnObject.addProperty(String.valueOf(i.getAndAdd(1)), blockTagString);
        }, sourcePos, targetPos);

        return returnObject;
    }

    private static JsonObject entities(Vec3 sourcePos, Vec3 targetPos)
    {
        LowHigh lowHigh = LowHigh.from(sourcePos, targetPos);

        List<Entity> entities = (List<Entity>) Globals.overworld.getEntities(EntityTypeTest.forClass(Entity.class), entity -> isBetween(entity.position(), lowHigh));

        JsonObject returnObject = new JsonObject();
        int i = 0;

        for(Entity entity : entities)
        {
            CompoundTag tag = new CompoundTag();

            entity.save(tag);

            tag.remove("UUID");


            returnObject.addProperty(String.valueOf(i), tag.toString());
            i++;
        }


        return returnObject;
    }

    private static boolean isBetween(Vec3 pos, LowHigh lowHigh)
    {
        return isBetween(pos, lowHigh.lowPos(), lowHigh.highPos());
    }

    private static boolean isBetween(Vec3 pos, Vec3 low, Vec3 high)
    {
        return pos.x > low.x && pos.x < high.x && pos.y > low.y && pos.y < high.y && pos.z > low.z && pos.z < high.z;
    }

    private static void loop3D(ThrowingConsumer<Vec3, CommandSyntaxException> consumer, Vec3 pos1, Vec3 pos2) throws CommandSyntaxException
    {
        pos1 = new Vec3((int) Math.round(pos1.x), (int) Math.round(pos1.y), (int) Math.round(pos1.z));
        pos2 = new Vec3((int) Math.round(pos2.x), (int) Math.round(pos2.y), (int) Math.round(pos2.z));

        LowHigh lowHigh = LowHigh.from(pos1, pos2);

        for(int x = (int) lowHigh.lowPos().x; x < lowHigh.highPos().x; x++)
        {
            for(int y = (int) lowHigh.lowPos().y; y < lowHigh.highPos().y; y++)
            {
                for(int z = (int) lowHigh.lowPos().z; z < lowHigh.highPos().z; z++)
                {
                    consumer.apply(new Vec3(x, y, z));
                }
            }
        }

    }

    private static JsonObject interactionPoints(Vec3 sourcePos, Vec3 targetPos)
    {
        Map<Vec3, String> filteredInteractionPoints = InteractivePositionsSavedData.getForOverworld().getInteractionPositionsMap().entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().getCenter(), Map.Entry::getValue));
        filteredInteractionPoints = filterMap(filteredInteractionPoints, sourcePos, targetPos);

        JsonObject returnObject = new JsonObject();
        JsonObject pointObject = null;

        int i = 0;

        for(Map.Entry<Vec3, String> entry : filteredInteractionPoints.entrySet())
        {
            Vec3 relativePos = entry.getKey().subtract(sourcePos);

            pointObject = new JsonObject();

            CustomJson.addPosition(pointObject, relativePos);
            CustomJson.addValue(pointObject, entry.getValue());

            i++;

            returnObject.add(String.valueOf(i), pointObject);
        }

        return returnObject;
    }

    private static <T> Map<Vec3, T> filterMap(Map<Vec3, T> map, Vec3 sourcePos, Vec3 targetPos)
    {
        Map<Vec3, T> filteredMap = new HashMap<>();
        
        LowHigh lowHigh = LowHigh.from(sourcePos, targetPos);

        for(Map.Entry<Vec3, T> entry : map.entrySet())
        {
            Vec3 pos = entry.getKey();
            
            if(isBetween(pos, sourcePos, targetPos))
            {
                filteredMap.put(pos, entry.getValue());
            }
        }
        
        return filteredMap;
    }

    public static boolean view(BlockPos position, String mapName) throws CommandSyntaxException
    {
        String contents = CustomJson.readFile(CustomJson.mapPath(mapName));

        if(contents == null)
        {
            return false;
        }

        JsonObject object = new Gson().fromJson(contents, JsonObject.class);

        JsonObject mobSpawnPoints = object.getAsJsonObject(mobSpawnPointsKey);
        Vec3 pos = Vec3.atBottomCenterOf(position);

        JsonObject entryObject;

        for(JsonElement element : mobSpawnPoints.asMap().values())
        {
            entryObject = (JsonObject) element;

            ManageMobSpawnPoints.spawnSpawnpointArmorStand(CustomJson.getPosition(entryObject).add(pos), CustomJson.getRotation(entryObject));
        }

        JsonObject interactionPoints = object.getAsJsonObject(interactionPointsKey);

        for(JsonElement element : interactionPoints.asMap().values())
        {
            entryObject = (JsonObject) element;

            ManageInteractionPositions.addInteractionPositionVisual(BlockPos.containing(CustomJson.getPosition(entryObject).add(pos)), entryObject.get(CustomJson.valueKey).getAsString());
        }

        JsonObject blocks = object.getAsJsonObject(blocksKey);
        List<JsonElement> blockElements = blocks.asMap().values().stream().toList();
        AtomicInteger i = new AtomicInteger();

        Vec3 sourcePos = CustomJson.getPosition(object.getAsJsonObject(sourcePosKey));
        Vec3 targetPos = CustomJson.getPosition(object.getAsJsonObject(targetPosKey));

        Vec3 dif = sourcePos.subtract(targetPos);

        Vec3 newTargetPos = pos.subtract(dif);

        loop3D(vec3 ->
        {
            CompoundTag tag = TagParser.parseTag(blockElements.get(i.getAndAdd(1)).getAsString());
            Globals.overworld.setBlock(BlockPos.containing(vec3), NbtUtils.readBlockState(Globals.overworld.holderLookup(Registries.BLOCK), tag.getCompound(blockData)), 3);
            if(tag.contains(blockEntityData))
            {
                BlockEntity blockEntity = Globals.overworld.getBlockEntity(BlockPos.containing(vec3));
                blockEntity.loadCustomOnly(tag.getCompound(blockEntityData), Globals.overworld.registryAccess());
            }
            }, pos, newTargetPos);


        JsonObject entities = object.getAsJsonObject(entitiesKey);
        List<JsonElement> entityElements = entities.asMap().values().stream().toList();

        for(JsonElement element : entityElements)
        {
            CompoundTag tag = TagParser.parseTag(element.getAsString());
            EntityType.loadEntityRecursive(tag, Globals.overworld, entity ->
            {
                entity.moveTo(pos.add(entity.position().subtract(sourcePos)));
                Globals.overworld.addFreshEntity(entity);

                return entity;
            });
        }

        return true;
    }


}
