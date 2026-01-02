package net.tazgirl.survivor.saved_data.registers;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.tazgirl.magicjson.SendMessage;
import net.tazgirl.survivor.saved_data.registers.mob_sets.RegisterMobSets;
import net.tazgirl.survivor.saved_data.registers.modifier.RegisterModifierStorageRecord;
import net.tazgirl.survivor.saved_data.registers.wave_mob.RegisterWaveMob;

@EventBusSubscriber
public class RegisterTestCommands
{
    @SubscribeEvent
    public static void OnRegisterCommands(RegisterCommandsEvent event)
    {
        event.getDispatcher().register(Commands.literal("modifierGet")
                .then(Commands.argument("modifier", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String modifier = StringArgumentType.getString(ctx, "modifier");
                            if(RegisterModifierStorageRecord.hasAddress(modifier))
                            {
                                SendMessage.All(RegisterModifierStorageRecord.get(modifier).toString());
                            }

                            return 1;
                        })
                ));
        event.getDispatcher().register(Commands.literal("mobSetGet")
                .then(Commands.argument("mobSet", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String mobSet = StringArgumentType.getString(ctx, "mobSet");
                            if(RegisterMobSets.hasAddress(mobSet))
                            {
                                SendMessage.All(RegisterMobSets.get(mobSet).toString());
                            }

                            return 1;
                        })
                ));
        event.getDispatcher().register(Commands.literal("waveMobGet")
                .then(Commands.argument("waveMob", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String waveMob = StringArgumentType.getString(ctx, "waveMob");
                            if(RegisterWaveMob.hasAddress(waveMob))
                            {
                                SendMessage.All(RegisterWaveMob.get(waveMob).toString());
                            }

                            return 1;
                        })
                ));
        event.getDispatcher().register(Commands.literal("listRegister")
                .then(Commands.argument("register", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String register = StringArgumentType.getString(ctx, "register");

                            switch (register.toLowerCase())
                            {
                                case "modifier":
                                {
                                    SendMessage.All(RegisterModifierStorageRecord.registerString());
                                    break;
                                }
                                case "wavemob":
                                {
                                    SendMessage.All(RegisterWaveMob.registerString());
                                    break;
                                }
                                case "mobset":
                                {
                                    SendMessage.All(RegisterMobSets.registerString());
                                    break;
                                }
                            }

                            return 1;
                        })
                ));

    }
}
