package de.stynxyxy.emeraldtradingsystem;

import com.mojang.logging.LogUtils;
import de.stynxyxy.emeraldtradingsystem.network.ModNetworking;
import de.stynxyxy.emeraldtradingsystem.particle.ModParticles;
import de.stynxyxy.emeraldtradingsystem.util.DebugUtil;
import net.minecraft.client.Minecraft;

import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

//declare class as Mod
@Mod(EmeraldTradingSystem.MODID)
public class EmeraldTradingSystem {

    //MODID
    public static final String MODID = "emeraldtradingsystem";
    //LOGGEr
    private static final Logger LOGGER = LogUtils.getLogger();

    public static DebugUtil getDebugUtil() {
        return debugUtil;
    }

    private static DebugUtil debugUtil = new DebugUtil(false);

    //constructor
    public EmeraldTradingSystem() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        ModParticles.register(modEventBus);
        ModNetworking.registerPackets();

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        debugUtil.info("DIRT BLOCK >> "+ Blocks.DIRT.getName().toString());
        debugUtil.info("HELLO FROM COMMON SETUP");

    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            debugUtil.info("HELLO FROM CLIENT SETUP");
            debugUtil.info("MINECRAFT NAME >> {}"+ Minecraft.getInstance().getUser().getName());
        }
    }
}
