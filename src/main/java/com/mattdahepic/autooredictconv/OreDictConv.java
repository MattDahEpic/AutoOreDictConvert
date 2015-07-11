package com.mattdahepic.autooredictconv;

//mport com.mattdahepic.autooredictconv.command.CommandConfig;
import com.mattdahepic.autooredictconv.config.Config;
import com.mattdahepic.autooredictconv.network.PacketHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.io.File;

@Mod(modid = OreDictConv.MODID,name = OreDictConv.NAME,version = OreDictConv.VERSION,dependencies = OreDictConv.DEPENDENCIES)
public class OreDictConv {
    @Mod.Instance("autooredictconv")
    public static OreDictConv instance;

    public static final String MODID = "autooredictconv";
    public static final String NAME = "Hotkey Ore Dictionary Converter";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:mdecore@[1.8-1.2.2,);";
    public static final String UPDATE_URL = "";

    @SidedProxy(clientSide = "com.mattdahepic.autooredictconv.client.ClientProxy",serverSide = "com.mattdahepic.autooredictconv.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(instance);
        Config.load(new File(event.getModConfigurationDirectory(),"autooredictconvert.cfg"));
    }
    @Mod.EventHandler
    public void init (FMLInitializationEvent event) {
        PacketHandler.initPackets();
    }
    @Mod.EventHandler
    public void postInit (FMLPostInitializationEvent event) {
        //Log.info("Ready to convert with " + Config.oreValues.length + " entries in the config!");
    }
    @Mod.EventHandler
    public void serverStarting (FMLServerStartingEvent event) {
        //event.registerServerCommand(new CommandConfig());
    }
}
