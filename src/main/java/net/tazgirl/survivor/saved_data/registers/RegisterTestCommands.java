package net.tazgirl.survivor.saved_data.registers;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.tazgirl.magicjson.PrivateCore;
import net.tazgirl.magicjson.SendMessage;
import net.tazgirl.magicjson.processing.Tokenisation;
import net.tazgirl.magicjson.processing.TokensToHolder;
import net.tazgirl.survivor.saved_data.registers.mob_sets.MobSetsRegister;
import net.tazgirl.survivor.saved_data.registers.modifier.ModifierStorageRecordRegister;
import net.tazgirl.survivor.saved_data.registers.wave_mob.WaveMobRegister;

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
                            if(ModifierStorageRecordRegister.hasAddress(modifier))
                            {
                                SendMessage.All(ModifierStorageRecordRegister.get(modifier).toString());
                            }

                            return 1;
                        })
                ));
        event.getDispatcher().register(Commands.literal("mobSetGet")
                .then(Commands.argument("mobSet", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String mobSet = StringArgumentType.getString(ctx, "mobSet");
                            if(MobSetsRegister.hasAddress(mobSet))
                            {
                                SendMessage.All(MobSetsRegister.get(mobSet).toString());
                            }

                            return 1;
                        })
                ));
        event.getDispatcher().register(Commands.literal("waveMobGet")
                .then(Commands.argument("waveMob", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String waveMob = StringArgumentType.getString(ctx, "waveMob");
                            if(WaveMobRegister.hasAddress(waveMob))
                            {
                                SendMessage.All(WaveMobRegister.get(waveMob).toString());
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
                                    SendMessage.All(ModifierStorageRecordRegister.registerString());
                                    break;
                                }
                                case "wavemob":
                                {
                                    SendMessage.All(WaveMobRegister.registerString());
                                    break;
                                }
                                case "mobset":
                                {
                                    SendMessage.All(MobSetsRegister.registerString());
                                    break;
                                }
                            }

                            return 1;
                        })
                ));

    }
}
