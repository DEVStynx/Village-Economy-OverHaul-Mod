package de.stynxyxy.emeraldtradingsystem.events;


import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import de.stynxyxy.emeraldtradingsystem.particle.ModParticles;
import de.stynxyxy.emeraldtradingsystem.particle.custom.VillagerOutOfEmeraldParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmeraldTradingSystem.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRegisterEvent {
    @SubscribeEvent
    public static void registerParticleEvent(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.VILLAGEROUTOFEMERALDSPARTICLE.get(),VillagerOutOfEmeraldParticle.Provider::new);
    }
}
