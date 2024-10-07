package de.stynxyxy.emeraldtradingsystem.util;

import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import de.stynxyxy.emeraldtradingsystem.capability.EmeraldCapability;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraftforge.common.util.LazyOptional;

public class TradeUtil {


    public static void performTrade(Villager villager1, Villager villager2) {
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
}
