package de.stynxyxy.emeraldtradingsystem.events;


import com.mojang.logging.LogUtils;
import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import de.stynxyxy.emeraldtradingsystem.capability.CapabilityRegistry;
import de.stynxyxy.emeraldtradingsystem.capability.EmeraldCapability;
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
        Player player = event.getEntity();
        Villager villager = (Villager) event.getAbstractVillager();
        MerchantOffer offer = event.getMerchantOffer();

        // Get Capability
        LazyOptional<EmeraldCapability> emeraldCap = villager.getCapability(CapabilityRegistry.EMERALD_CAPABILITY);

        emeraldCap.ifPresent(cap -> {
            int currentEmeralds = cap.getEmeraldcount();

            // Trade costs and result
            ItemStack item1 = offer.getCostA();
            ItemStack item2 = offer.getCostB();
            ItemStack result = offer.getResult();

            ItemStack[] costs = {item1,item2};

            // Add emeralds to villager if the trade costs include emeralds
            for (ItemStack stack: costs) {
                if (stack.is(Items.EMERALD)) {
                    cap.addEmeralds(stack.getCount());
                    for (MerchantOffer i_offer: cap.getBlockedOffers()) {
                        if (i_offer.getResult().is(Items.EMERALD) && i_offer.getResult().getCount() <= cap.getEmeraldcount()) {
                            MerchantOffers newOffers = villager.getOffers();
                            newOffers.add(i_offer);
                            player.closeContainer();
                            villager.setOffers(newOffers);
                        }
                    }
                }
            }

            // Check if the result is emeralds and if the villager has enough
            if (result.is(Items.EMERALD)) {
                if (result.getCount() <= cap.getEmeraldcount()) {
                    cap.useEmeralds(result.getCount());
                    if (cap.getEmeraldcount() == 0) {
                        offer.setToOutOfStock();
                    }

                } else {
                    // Villager doesn't have enough emeralds
                    player.sendSystemMessage(Component.literal("The Villager does not have enough emeralds to trade!"));

                    // Set the offer to out of stock
                    offer.setToOutOfStock();

                    // Refund the player
                    player.getInventory().add(new ItemStack(item1.getItem(), item1.getCount()));
                    if (!item2.isEmpty()) {
                        player.getInventory().add(new ItemStack(item2.getItem(), item2.getCount()));
                    }



                    player.closeContainer();
                    // Villager reaction (visual feedback)
                    villager.level().playSound(null, villager.blockPosition(), SoundEvents.VILLAGER_NO, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    villager.level().addParticle(ParticleTypes.ANGRY_VILLAGER, villager.getX(), villager.getY(), villager.getZ(), 0.0D, 0.0D, 0.0D);
                    villager.level().playSound(null, villager.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0F, 1.0F);
                }
            }

            // Update player's chat with villager emerald count after trade
            int currentEmeraldsAfter = cap.getEmeraldcount();
            player.sendSystemMessage(Component.literal("Villager's emerald count: " + currentEmeraldsAfter));
        });


    }
}
