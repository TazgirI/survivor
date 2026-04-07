package net.tazgirl.survivor;

import net.tazgirl.tutilz.admin.Logging;
import net.tazgirl.tutilz.chat.SendMessage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

public class SurvivorLogging extends Logging
{
    // Nobody make fun of me for this
    // I know it's dumb, but it's easy to remember

    static Logger overrideLogger = Survivor.LOGGER;

    public static void Log(@NotNull String message)
    {
        Log(message, overrideLogger);
    }

    public static void Log(@NotNull String message, List<Object> quickDump)
    {
        Log(message, overrideLogger, quickDump);
    }

    public static void Warn(@NotNull String message)
    {
        Warn(message, overrideLogger);
    }

    public static void Warn(@NotNull String message, List<Object> quickDump)
    {
        Warn(message, overrideLogger, quickDump);
    }

    public static void Error(@NotNull String message)
    {
        Error(message, overrideLogger);
    }

    public static void Error(@NotNull String message, List<Object> quickDump)
    {
        Error(message, overrideLogger, quickDump);
    }

    public static void Debug(@NotNull String message)
    {
        Debug(message, overrideLogger);
    }

    public static void Debug(@NotNull String message, List<Object> quickDump)
    {
        Debug(message, overrideLogger, quickDump);
    }

    public static void LogAndTell(@NotNull String message)
    {
        LogAndTell(message, overrideLogger);
    }

}
