package com.mattdahepic.autooredictconv;

import com.mattdahepic.autooredictconv.command.CommandODC;
import com.mattdahepic.autooredictconv.config.OptionsConfig;
import com.mattdahepic.autooredictconv.convert.Conversions;
import com.mattdahepic.autooredictconv.keypress.PacketHandler;
import com.mattdahepic.autooredictconv.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = AutoOreDictConv.MODID,name = AutoOreDictConv.NAME,version = AutoOreDictConv.VERSION,dependencies = AutoOreDictConv.DEPENDENCIES,updateJSON = AutoOreDictConv.UPDATE_JSON)
public class AutoOreDictConv {
    public static final String MODID = "autooredictconv";
    public static final String NAME = "Auto Ore Dictionary Converter";
    static final String VERSION = "@VERSION@";
    static final String DEPENDENCIES = "required-after:mdecore@[1.9-0.5,);";
    static final String UPDATE_JSON = "https://raw.githubusercontent.com/MattDahEpic/Version/master/"+MODID+".json";

    public static final Logger logger = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "com.mattdahepic.autooredictconv.proxy.ClientProxy",serverSide = "com.mattdahepic.autooredictconv.proxy.CommonProxy")
    public static CommonProxy proxy;

    private static File conversionsConfig;
    public static Block converter;

    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
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
    }
    @Mod.EventHandler
    public void postInit (FMLPostInitializationEvent e) {
        Conversions.Config.load(conversionsConfig);
        logger.info("Ready to convert with "+ Conversions.conversionMap.keySet().size()+" entries in the config.");
    }
    @Mod.EventHandler
    public void serverStarting (FMLServerStartingEvent e) {
        CommandODC.instance.init(e);
        OreDictionary.rebakeMap(); //metallurgy fix
    }
    @SubscribeEvent
    public void onTick (TickEvent.ServerTickEvent e) {
        if (!OptionsConfig.enableKeypress) {
            for (EntityPlayer p : FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerList()) {
                Conversions.convert(p);
            }
        }
    }
}
