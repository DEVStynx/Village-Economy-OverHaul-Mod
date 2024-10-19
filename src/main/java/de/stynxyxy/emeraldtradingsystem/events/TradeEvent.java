package de.stynxyxy.emeraldtradingsystem.events;


import com.mojang.logging.LogUtils;
import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import de.stynxyxy.emeraldtradingsystem.capability.CapabilityRegistry;
import de.stynxyxy.emeraldtradingsystem.capability.EmeraldCapability;
import de.stynxyxy.emeraldtradingsystem.particle.ModParticles;
import de.stynxyxy.emeraldtradingsystem.util.TradeUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SmokeParticle;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.TradeWithVillagerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = EmeraldTradingSystem.MODID)
public class TradeEvent {

    @SubscribeEvent
    public static void onVillagerTrade(TradeWithVillagerEvent event) {
        TradeUtil.handlePlayerTrade(event);
    }
}
