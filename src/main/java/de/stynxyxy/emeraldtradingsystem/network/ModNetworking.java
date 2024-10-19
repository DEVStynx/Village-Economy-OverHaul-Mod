package de.stynxyxy.emeraldtradingsystem.network;

import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import de.stynxyxy.emeraldtradingsystem.network.packets.particle.CustomParticleSpawnPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(EmeraldTradingSystem.MODID, "network"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        int id = 0;
        INSTANCE.registerMessage(id++, CustomParticleSpawnPacket.class, CustomParticleSpawnPacket::encode, CustomParticleSpawnPacket::decode, CustomParticleSpawnPacket::handle);
    }
}