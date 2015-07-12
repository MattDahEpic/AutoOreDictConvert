package com.mattdahepic.autooredictconv;

import com.mattdahepic.autooredictconv.command.CommandConfig;
import com.mattdahepic.autooredictconv.config.Config;
import com.mattdahepic.autooredictconv.convert.Convert;
import com.mattdahepic.mdecore.helpers.LogHelper;
import com.mattdahepic.mdecore.update.UpdateChecker;
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

import java.io.File;

@Mod(modid = OreDictConv.MODID,name = OreDictConv.NAME,version = OreDictConv.VERSION,dependencies = OreDictConv.DEPENDENCIES)
public class OreDictConv {
    @Mod.Instance("autooredictconv")
    public static OreDictConv instance;

    public static final String MODID = "autooredictconv";
    public static final String NAME = "Hotkey Ore Dictionary Converter";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:mdecore@[1.8-1.2.2,);";
    public static final String UPDATE_URL = "https://raw.githubusercontent.com/MattDahEpic/AutoOreDictConvert1.8/master/version.txt";

    @SidedProxy(clientSide = "com.mattdahepic.autooredictconv.client.ClientProxy",serverSide = "com.mattdahepic.autooredictconv.CommonProxy")
    public static CommonProxy proxy;

    public static MinecraftServer mcServer;

    @Mod.EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(instance);
        Config.load(new File(event.getModConfigurationDirectory(),"autooredictconvert.cfg"));
    }
    @Mod.EventHandler
    public void init (FMLInitializationEvent event) {}
    @Mod.EventHandler
    public void postInit (FMLPostInitializationEvent event) {
        LogHelper.info(MODID,"Ready to convert with "+Config.conversions.keySet().size()+" entries in the config.");
        UpdateChecker.updateCheck(MODID,NAME,UPDATE_URL,VERSION,false,null);
    }
    @Mod.EventHandler
    public void serverStarting (FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandConfig());
        mcServer = event.getServer();
    }
    @SubscribeEvent
    public void onJoined (PlayerEvent.PlayerLoggedInEvent event) {
        UpdateChecker.updateCheck(MODID,NAME,UPDATE_URL,VERSION,true,event.player);
    }
    @SubscribeEvent
    public void onTick (TickEvent.ServerTickEvent event) {
        Convert.convert();
    }
}
