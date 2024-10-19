package de.stynxyxy.emeraldtradingsystem.capability;

import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = EmeraldTradingSystem.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityRegistry {

    public static final Capability<EmeraldCapability> EMERALD_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(EmeraldCapability.class);
    }

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Villager villager) {
            event.addCapability(new ResourceLocation(EmeraldTradingSystem.MODID, "emeralds"), new EmeraldCapabilityProvider());
        }
    }
}
