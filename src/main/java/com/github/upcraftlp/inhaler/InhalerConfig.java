package com.github.upcraftlp.inhaler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.github.upcraftlp.inhaler.GaspunkInhaler.MODID;

/**
 * @author UpcraftLP
 */
@Config.LangKey("config.gp_inhaler.title")
@Config(modid = MODID, name = "craftdevmods/GaspunkInhaler") //--> /config/craftdevmods/gp-inhaler.cfg
public class InhalerConfig {

    //config values here
    @Config.Name("Half Life Period")
    @Config.Comment("the half life period of gases inside the body, default: -212.620584")
    public static double halfLife = -212.620584D;

    @Mod.EventBusSubscriber(modid = MODID)
    public static class Handler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(MODID)) {
                ConfigManager.load(MODID, Config.Type.INSTANCE);
            }
        }
    }
}
