package de.stynxyxy.emeraldtradingsystem.particle;

import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EmeraldTradingSystem.MODID);

    public static final RegistryObject<SimpleParticleType> VILLAGEROUTOFEMERALDSPARTICLE =
            PARTICLE_TYPES.register("villageroutofemeraldsparticle", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
