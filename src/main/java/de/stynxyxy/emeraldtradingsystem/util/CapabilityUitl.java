package de.stynxyxy.emeraldtradingsystem.util;

import de.stynxyxy.emeraldtradingsystem.capability.CapabilityRegistry;
import de.stynxyxy.emeraldtradingsystem.capability.EmeraldCapability;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityUitl {


    /*
     *@info Get The EmeraldCapability
     *@param villager The Villager you want to get the capability from
     */
    public static LazyOptional<EmeraldCapability> getEmeraldCapability(Villager villager) {
        LazyOptional<EmeraldCapability> emeraldCap = villager.getCapability(CapabilityRegistry.EMERALD_CAPABILITY);
        return emeraldCap;
    }

}
