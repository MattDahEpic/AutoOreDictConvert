package com.mattdahepic.autooredictconv;

import com.mattdahepic.autooredictconv.command.CommandConfig;
import com.mattdahepic.autooredictconv.config.Config;
import com.mattdahepic.autooredictconv.convert.Convert;
import com.mattdahepic.autooredictconv.proxy.CommonProxy;
import com.mattdahepic.mdecore.helpers.LogHelper;
import com.mattdahepic.mdecore.update.UpdateChecker;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
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

import java.io.File;

@Mod(modid = AutoOreDictConv.MODID,name = AutoOreDictConv.NAME,version = AutoOreDictConv.VERSION,dependencies = AutoOreDictConv.DEPENDENCIES)
public class AutoOreDictConv {
    @Mod.Instance("autooredictconv")
    public static AutoOreDictConv instance;

    public static final String MODID = "autooredictconv";
    public static final String NAME = "Auto Ore Dictionary Converter";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:mdecore@[1.8-1.2.5,);";
    public static final String UPDATE_URL = "https://raw.githubusercontent.com/MattDahEpic/AutoOreDictConvert1.8/master/version.txt";
    public static final String CLIENT_PROXY = "com.mattdahepic.autooredictconv.proxy.ClientProxy";
    public static final String COMMON_PROXY = "com.mattdahepic.autooredictconv.proxy.CommonProxy";

    @SidedProxy(clientSide = AutoOreDictConv.CLIENT_PROXY,serverSide = AutoOreDictConv.COMMON_PROXY)
    public static CommonProxy proxy;

    public static MinecraftServer mcServer;
    static File configFile;
    public static Block converter;

    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(instance);
        configFile = new File(event.getModConfigurationDirectory(),"autooredictconvert.cfg");
        proxy.registerBlocks();
        proxy.registerRecipes();
        proxy.registerTiles();
    }
    @Mod.EventHandler
    public void init (FMLInitializationEvent event) {
        proxy.registerRenderers();
    }
    @Mod.EventHandler
    public void postInit (FMLPostInitializationEvent event) {
        LogHelper.info(MODID,"Ready to convert with "+Config.conversions.keySet().size()+" entries in the config.");
        UpdateChecker.updateCheck(MODID,NAME,UPDATE_URL,VERSION,false,null);
    }
    @Mod.EventHandler
    public void serverStarting (FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandConfig());
        mcServer = event.getServer();
        Config.load(this.configFile);
        OreDictionary.rebakeMap(); //metallurgy fix
    }
    @SubscribeEvent
    public void onJoined (PlayerEvent.PlayerLoggedInEvent event) {
        UpdateChecker.updateCheck(MODID,NAME,UPDATE_URL,VERSION,true,event.player);
    }
    @SubscribeEvent
    public void onTick (TickEvent.ServerTickEvent event) {
        Convert.convertPlayers();
    }
}
