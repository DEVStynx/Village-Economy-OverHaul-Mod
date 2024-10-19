package de.stynxyxy.emeraldtradingsystem.network.packets.particle;

import com.mojang.logging.LogUtils;
import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import de.stynxyxy.emeraldtradingsystem.particle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CustomParticleSpawnPacket {
    private final double x,y,z;
    public CustomParticleSpawnPacket(double x, double y, double z) {
        this.x  = x;
        this.y = y;
        this.z = z;
    }
    public static void encode(CustomParticleSpawnPacket packet, FriendlyByteBuf buf) {
        buf.writeDouble(packet.x);
        buf.writeDouble(packet.y);
        buf.writeDouble(packet.z);
    }
    public static CustomParticleSpawnPacket decode(FriendlyByteBuf buf) {
        return new CustomParticleSpawnPacket(buf.readDouble(),buf.readDouble(),buf.readDouble());
    }

    public static void handle(CustomParticleSpawnPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() ->{
            Level world = Minecraft.getInstance().level;
            if (world == null) return;
            world.addParticle(ModParticles.VILLAGEROUTOFEMERALDSPARTICLE.get(),packet.x,packet.y +1,packet.z,0.0D,1,0.0D);
            EmeraldTradingSystem.getDebugUtil().infoPlayer("Fired NetWorking Packet!",true);
        });
        context.get().setPacketHandled(true);
    }
}
