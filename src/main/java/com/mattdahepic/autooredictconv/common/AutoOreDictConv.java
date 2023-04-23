package com.mattdahepic.autooredictconv.common;

import com.mattdahepic.autooredictconv.common.block.ConverterBlock;
import com.mattdahepic.autooredictconv.common.block.ConverterTile;
import com.mattdahepic.autooredictconv.common.command.CommandODC;
import com.mattdahepic.autooredictconv.common.config.ConversionsConfig;
import com.mattdahepic.autooredictconv.common.config.OptionsConfig;
import com.mattdahepic.autooredictconv.common.convert.Conversions;
import com.mattdahepic.autooredictconv.common.keypress.KeyHandler;
import com.mattdahepic.autooredictconv.common.keypress.PacketHandler;
import com.mattdahepic.mdecore.common.registries.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;
import java.util.ArrayList;

@Mod("autooredictconv")
public class AutoOreDictConv {
    public static final String MODID = "autooredictconv";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<Block> CONVERTER_BLOCK = BLOCKS.register("converter", ConverterBlock::new);
    public static final RegistryObject<Item> CONVERTER_ITEM = ITEMS.register("converter", () -> new BlockItem(CONVERTER_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<BlockEntityType<?>> CONVERTER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("converter", () -> ConverterTile.TYPE);

    public static ArrayList<String> pausedPlayers = new ArrayList<String>();

    public AutoOreDictConv () {
        //config
        ConfigRegistry.registerConfig(null, OptionsConfig.COMMON_SPEC);
        ConversionsConfig.file = Paths.get(FMLPaths.CONFIGDIR.get().toString(),"autooredictconv-conversions.cfg").toFile();

        //mod bus events
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::commonSetup);
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);

        //forge bus events
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(this::onTick);
    }

    public void clientSetup (FMLClientSetupEvent event) {
        if (OptionsConfig.COMMON.enableKeypress.get()) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(KeyHandler::register);
            MinecraftForge.EVENT_BUS.addListener(KeyHandler::onKeyInput);
        }
        MinecraftForge.EVENT_BUS.addListener(this::onTooltip);
    }

    public void commonSetup (FMLCommonSetupEvent event) {
        PacketHandler.initPackets();
        CommandRegistry.registerCommand(CommandODC::register);
        ConversionsConfig.load();
        LOGGER.info("Ready to convert with "+ Conversions.tagConversionMap.keySet().size()+" entries in the config.");
    }

    public void onTick (TickEvent.ServerTickEvent e) {
        if (!OptionsConfig.COMMON.enableKeypress.get()) {
            for (Player p : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                if (pausedPlayers.contains(p.getScoreboardName())) continue;
                Conversions.convert(p);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void onTooltip(ItemTooltipEvent e) {
        if (e.getItemStack().getItem() == ForgeRegistries.ITEMS.getValue(new ResourceLocation(MODID,ConverterBlock.NAME))) {
            for (int i = 0; i < 3; i++) {
                e.getToolTip().add(Component.translatable("tooltip.autooredictconv.converter."+i));
            }
        }
    }
}
