package com.mattdahepic.autooredictconv;

import com.mattdahepic.autooredictconv.command.CommandODC;
import com.mattdahepic.autooredictconv.config.ConversionsConfig;
import com.mattdahepic.autooredictconv.config.OptionsConfig;
import com.mattdahepic.autooredictconv.convert.Convert;
import com.mattdahepic.autooredictconv.keypress.PacketHandler;
import com.mattdahepic.autooredictconv.proxy.CommonProxy;
import com.mattdahepic.mdecore.update.UpdateChecker;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = AutoOreDictConv.MODID,name = AutoOreDictConv.NAME,version = AutoOreDictConv.VERSION,dependencies = AutoOreDictConv.DEPENDENCIES)
public class AutoOreDictConv {
    @Mod.Instance("autooredictconv")
    public static AutoOreDictConv instance;

    public static final String MODID = "autooredictconv";
    public static final String NAME = "Auto Ore Dictionary Converter";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:mdecore@[1.8.8-1.6,);";
    public static final String UPDATE_URL = "https://raw.githubusercontent.com/MattDahEpic/Version/master/"+ MinecraftForge.MC_VERSION+"/"+MODID+".txt";
    public static final String CLIENT_PROXY = "com.mattdahepic.autooredictconv.proxy.ClientProxy";
    public static final String COMMON_PROXY = "com.mattdahepic.autooredictconv.proxy.CommonProxy";

    public static final Logger logger = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = AutoOreDictConv.CLIENT_PROXY,serverSide = AutoOreDictConv.COMMON_PROXY)
    public static CommonProxy proxy;

    static File conversionsConfig;
    public static Block converter;

    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent e) {
        FMLCommonHandler.instance().bus().register(instance);
        OptionsConfig.instance(MODID).initialize(e);
        conversionsConfig = new File(e.getModConfigurationDirectory(),"mattdahepic"+File.separator+"conversions.cfg");
        if (OptionsConfig.enableBlock) {
            proxy.registerBlocks();
            proxy.registerRecipes();
            proxy.registerTiles();
            proxy.registerRenderers();
        }
    }
    @Mod.EventHandler
    public void init (FMLInitializationEvent e) {
        if (OptionsConfig.enableKeypress) {
            proxy.registerKeys();
            PacketHandler.initPackets();
        }
        UpdateChecker.checkRemote(MODID,UPDATE_URL);
    }
    @Mod.EventHandler
    public void postInit (FMLPostInitializationEvent e) {
        ConversionsConfig.load(conversionsConfig);
        logger.info("Ready to convert with "+ ConversionsConfig.conversions.keySet().size()+" entries in the config.");
    }
    @Mod.EventHandler
    public void serverStarting (FMLServerStartingEvent e) {
        CommandODC.init(e);
        OreDictionary.rebakeMap(); //metallurgy fix
    }
    @SubscribeEvent
    public void onJoined (PlayerEvent.PlayerLoggedInEvent e) {
        UpdateChecker.printMessageToPlayer(MODID,e.player);
    }
    @SubscribeEvent
    public void onTick (TickEvent.ServerTickEvent e) {
        if (!OptionsConfig.enableKeypress) {
            Convert.convertAllPlayers();
        }
    }
}
