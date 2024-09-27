package de.stynxyxy.emeraldtradingsystem.events;

import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import de.stynxyxy.emeraldtradingsystem.capability.CapabilityRegistry;
import de.stynxyxy.emeraldtradingsystem.capability.EmeraldCapability;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmeraldTradingSystem.MODID)
public class VillagerTick{
    @SubscribeEvent
    public static void PlayerTick(TickEvent.PlayerTickEvent event) {


    }
}
