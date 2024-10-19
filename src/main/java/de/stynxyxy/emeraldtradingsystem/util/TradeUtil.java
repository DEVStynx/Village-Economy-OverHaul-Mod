package de.stynxyxy.emeraldtradingsystem.util;

import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import de.stynxyxy.emeraldtradingsystem.capability.CapabilityRegistry;
import de.stynxyxy.emeraldtradingsystem.capability.EmeraldCapability;
import de.stynxyxy.emeraldtradingsystem.network.ModNetworking;
import de.stynxyxy.emeraldtradingsystem.network.packets.particle.CustomParticleSpawnPacket;
import de.stynxyxy.emeraldtradingsystem.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.TradeWithVillagerEvent;
import net.minecraftforge.network.PacketDistributor;

public class TradeUtil {

    /**
     *
     * @param villager1
     * @param villager2
     * @implNote ONLY for trading for a villager to another
     */
    public static void tradewVillager(Villager villager1, Villager villager2) {
        DebugUtil debugUtil = EmeraldTradingSystem.getDebugUtil();
        LazyOptional<EmeraldCapability> villager1_cap = CapabilityUitl.getEmeraldCapability(villager1);
        LazyOptional<EmeraldCapability> villager2_cap = CapabilityUitl.getEmeraldCapability(villager2);

        MerchantOffers villager1_offers = villager1.getOffers();


        villager1_cap.ifPresent(cap_v1 -> {


            int v1_emeraldcount = cap_v1.getEmeraldcount();


            villager2_cap.ifPresent(cap_v2 -> {
                int v2_emeraldcount = cap_v2.getEmeraldcount();

                for (MerchantOffer offer: villager1_offers) {
                    if ((offer.isOutOfStock()) && !(offer.getResult().is(Items.EMERALD)))
                        continue;

                    int cost = offer.getResult().getCount();
                    if (v2_emeraldcount < cost)
                        continue;

                    cap_v1.addEmeralds(cost);
                    cap_v2.useEmeralds(cost);



                    villager1.playCelebrateSound();
                    villager2.playCelebrateSound();
                    villager1.lookAt(villager2,0,0);
                    villager2.lookAt(villager1,1,1);
                    villager1.level().addParticle(ParticleTypes.TOTEM_OF_UNDYING,villager1.getEyePosition().x,villager1.getEyePosition().y,villager1.getEyePosition().z,0d,0d,0d);
                    villager2.level().addParticle(ParticleTypes.TOTEM_OF_UNDYING,villager2.getEyePosition().x,villager2.getEyePosition().y,villager2.getEyePosition().z,0d,0d,0d);

                    villager1.setItemInHand(InteractionHand.MAIN_HAND, offer.getResult());

                    villager2.setItemInHand(InteractionHand.MAIN_HAND,offer.getCostA());
                    debugUtil.info("Villager: "+villager1.getUUID().toString()+" and Villager: "+villager2.getUUID().toString()+" performed a trade successfully!");
                }
            });

        });
    }

    /**
     *
     * @param event
     */

    public static void handlePlayerTrade(TradeWithVillagerEvent event) {
        //Variables
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
                    //add Emeralds to Villager!
                    cap.addEmeralds(stack.getCount());
                    //Handle BlockedOffers
                    /* Currently Disabled!
                    for (MerchantOffer i_offer: cap.getBlockedOffers()) {
                        if (i_offer.getResult().is(Items.EMERALD) && i_offer.getResult().getCount() <= cap.getEmeraldcount()) {
                            MerchantOffers newOffers = villager.getOffers();
                            newOffers.add(i_offer);
                            player.closeContainer();
                            villager.setOffers(newOffers);
                        }
                    }
                     */
                }
            }

            // Check if the result is emeralds and if the villager has enough
            if (result.is(Items.EMERALD)) {
                if (result.getCount() <= cap.getEmeraldcount()) {
                    cap.useEmeralds(result.getCount());


                }
                if (result.getCount() > cap.getEmeraldcount()) {
                    // Villager doesn't have enough emeralds
                    player.sendSystemMessage(Component.literal("The Villager does not have enough emeralds to trade!"));
                    villager.level().addParticle(ModParticles.VILLAGEROUTOFEMERALDSPARTICLE.get(),villager.getX(),villager.getY()+1,villager.getZ(), 0, 0.75, 0);
                    // Set the offer to out of stock
                    offer.setToOutOfStock();

                    //Visual Handling


                    player.closeContainer();

                    // Villager reaction (visual feedback)
                    villager.level().playSound(null, villager.blockPosition(), SoundEvents.VILLAGER_NO, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    villager.level().addParticle(ModParticles.VILLAGEROUTOFEMERALDSPARTICLE.get(), villager.getX(), villager.getY(), villager.getZ(), 0.0D, 0.0D, 0.0D);
                    villager.level().playSound(null, villager.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    player.level().addParticle(ModParticles.VILLAGEROUTOFEMERALDSPARTICLE.get(), villager.getX(), villager.getY(), villager.getZ(),0,0,0);
                    if (!player.level().isClientSide()) {
                        ModNetworking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
                                new CustomParticleSpawnPacket(villager.getX(), villager.getY() + 1, villager.getZ()));
                    }

                    // Refund the player
                    player.getInventory().add(new ItemStack(item1.getItem(), item1.getCount()));
                    if (!item2.isEmpty()) {
                        player.getInventory().add(new ItemStack(item2.getItem(), item2.getCount()));
                    }



                }
            }

            // Update player's chat with villager emerald count after trade
            int currentEmeraldsAfter = cap.getEmeraldcount();
            player.sendSystemMessage(Component.literal("Villager's emerald count: " + currentEmeraldsAfter));
        });
    }
}