package com.github.upcraftlp.inhaler;

import com.github.upcraftlp.inhaler.api.IEntityGasEffect;
import com.github.upcraftlp.inhaler.capability.CapabilityGasEffect;
import core.upcraftlp.craftdev.api.util.ModHelper;
import core.upcraftlp.craftdev.api.util.UpdateChecker;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.github.upcraftlp.inhaler.GaspunkInhaler.*;

@SuppressWarnings("WeakerAccess")
@Mod(
        certificateFingerprint = FINGERPRINT_KEY,
        name = MODNAME,
        version = VERSION,
        acceptedMinecraftVersions = MCVERSIONS,
        modid = MODID,
        dependencies = DEPENDENCIES,
        updateJSON = UPDATE_JSON
)
public class GaspunkInhaler {

    //Version
    public static final String MCVERSIONS = "[1.12.2, 1.13)";
    public static final String VERSION = "@VERSION@";

    //Meta Information
    public static final String MODNAME = "Gaspunk Inhaler";
    public static final String MODID = "gp_inhaler";
    public static final String DEPENDENCIES = "required-after:gaspunk@[1.4]";
    public static final String UPDATE_JSON = "@UPDATE_JSON@";

    public static final String FINGERPRINT_KEY = "@FINGERPRINTKEY@";

    @Mod.Instance
    public static GaspunkInhaler INSTANCE;
    
    @Mod.Metadata(MODID)
    public static ModMetadata metaData;
    
    private static final Logger log = LogManager.getLogger(MODID);
    private static boolean debugMode = false;

    public static Logger getLogger() {
        return log;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityManager.INSTANCE.register(IEntityGasEffect.class, new CapabilityGasEffect.Storage(), CapabilityGasEffect.Provider::new);
        if(Loader.isModLoaded("craftdev-core")) {
            UpdateChecker.registerMod(MODID);
            debugMode = ModHelper.isDebugMode();
        }
        log.info("gaspunk inhaler initialized!");
    }

}
