package net.tazgirl.survivor.saved_data.custom_json;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.tazgirl.survivor.Globals;
import net.tazgirl.survivor.Survivor;
import net.tazgirl.survivor.SurvivorLogging;
import net.tazgirl.tutilz.admin.Logging;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CustomJson
{
    public static String positionX = "position-x";
    public static String positionY = "position-y";
    public static String positionZ = "position-z";

    public static String rotationX = "rotation-x";
    public static String rotationY = "rotation-y";

    public static String valueKey = "value";

    public static Vec3 getPosition(JsonObject jsonObject)
    {
        return new Vec3(jsonObject.get(positionX).getAsDouble(), jsonObject.get(positionY).getAsDouble(), jsonObject.get(positionZ).getAsDouble());
    }

    public static void addPosition(JsonObject jsonObject, Vec3 position)
    {
        jsonObject.addProperty(positionX, position.x);
        jsonObject.addProperty(positionY, position.y);
        jsonObject.addProperty(positionZ, position.z);
    }

    public static JsonObject newPositionObject(Vec3 position)
    {
        JsonObject jsonObject = new JsonObject();
        addPosition(jsonObject, position);
        return jsonObject;
    }

    public static Vec2 getRotation(JsonObject jsonObject)
    {
        return new Vec2(jsonObject.get(rotationX).getAsFloat(), jsonObject.get(rotationY).getAsFloat());
    }

    public static void addValue(JsonObject pointObject, String value)
    {
        pointObject.addProperty(CustomJson.valueKey, value);
    }

    public static Path mapPath(String mapName)
    {
        return Minecraft.getInstance().gameDirectory.toPath().resolve("survivor").resolve("maps").resolve(mapName + ".json");
    }

    public static String readFile(Path filePath)
    {
        try
        {
            return Files.readString(filePath);
        }
        catch(IOException e)
        {
            SurvivorLogging.Debug("Failed to read file: " + filePath);
            return null;
        }
    }

    public static void verifyDirs(Path filePath) throws IOException
    {
        Path dirPath = filePath.getParent();

        if(!Files.exists(dirPath))
        {
            Files.createDirectories(dirPath);
        }
    }

    public static boolean overwriteFile(Path filePath, String content)
    {
        try
        {
            verifyDirs(filePath);

            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
            Files.writeString(filePath, content);
        }
        catch(IOException e)
        {
            SurvivorLogging.Warn("Failed to write to file: " + filePath);
            return false;
        }

        return true;
    }
}
