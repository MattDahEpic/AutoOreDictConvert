package com.mattdahepic.autooredictconv.common;

import com.mattdahepic.autooredictconv.common.block.AutoOreDictConvBlocks;
import com.mattdahepic.autooredictconv.common.block.AutoOreDictConvTiles;
import com.mattdahepic.autooredictconv.common.block.ConverterBlock;
import com.mattdahepic.autooredictconv.common.command.CommandODC;
import com.mattdahepic.autooredictconv.common.config.ConversionsConfig;
import com.mattdahepic.autooredictconv.common.config.OptionsConfig;
import com.mattdahepic.autooredictconv.common.convert.Conversions;
import com.mattdahepic.autooredictconv.common.keypress.KeyHandler;
import com.mattdahepic.autooredictconv.common.keypress.PacketHandler;
import com.mattdahepic.mdecore.common.registries.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.ArrayList;

@Mod("autooredictconv")
public class AutoOreDictConv {
    public static final String MODID = "autooredictconv";
    public static final Logger logger = LogManager.getLogger();

    public static ArrayList<String> pausedPlayers = new ArrayList<String>();

    public AutoOreDictConv () {
        //config
        ConfigRegistry.registerConfig(null, OptionsConfig.COMMON_SPEC);
        ConversionsConfig.file = Paths.get(FMLPaths.CONFIGDIR.get().toString(),"autooredictconv-conversions.cfg").toFile();

        //mod bus events
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::commonSetup);
        modBus.register(new AutoOreDictConvBlocks());
        modBus.register(new AutoOreDictConvTiles());

        //forge bus events
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(this::onTick);
        forgeBus.addListener(this::onTooltip);
    }

    public void clientSetup (final FMLClientSetupEvent event) {
        if (OptionsConfig.COMMON.enableKeypress.get()) {
            KeyHandler.register();
            MinecraftForge.EVENT_BUS.addListener(KeyHandler::onKeyInput);
        }
    }

    public void commonSetup (final FMLCommonSetupEvent event) {
        PacketHandler.initPackets();
        CommandRegistry.registerCommand(CommandODC::register);
        ConversionsConfig.load();
        logger.info("Ready to convert with "+ Conversions.tagConversionMap.keySet().size()+" entries in the config.");
    }

    public void onTick (TickEvent.ServerTickEvent e) {
        if (!OptionsConfig.COMMON.enableKeypress.get()) {
            for (PlayerEntity p : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                if (pausedPlayers.contains(p.getScoreboardName())) continue;
                Conversions.convert(p);
            }
        }
    }

    public void onTooltip(ItemTooltipEvent e) {
        if (e.getItemStack().getItem() == ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID,ConverterBlock.NAME))) {
            for (int i = 0; i < 3; i++) {
                e.getToolTip().add(new TranslationTextComponent("tooltip.autooredictconv.converter."+i));
            }
        }
    }
}
