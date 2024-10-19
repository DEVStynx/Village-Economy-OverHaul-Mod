package de.stynxyxy.emeraldtradingsystem.mixin;

import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import de.stynxyxy.emeraldtradingsystem.capability.CapabilityRegistry;
import de.stynxyxy.emeraldtradingsystem.capability.EmeraldCapability;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractVillager.class)
public abstract class VillagerMixin {


    private Villager self() {
        return (Villager)  (Object) this;
    }

    /**
     *
     * @author Stynxyxy
     * @reason disable Rendering Particles, when not supposed to!
     */
    @Inject(at = @At(value = "HEAD"), method = "addParticlesAroundSelf")
    protected void addParticlesAroundSelf(ParticleOptions p_35391_, CallbackInfo ci) {

        EmeraldTradingSystem.getDebugUtil().infoPlayer(VillagerMixin.class.getName()+" Mixin got Called!");
        if (p_35391_.equals(ParticleTypes.HAPPY_VILLAGER)) {
            LazyOptional<EmeraldCapability> capability = self().getCapability(CapabilityRegistry.EMERALD_CAPABILITY);
            capability.ifPresent(cap ->
            {
                int emeraldCount = cap.getEmeraldcount();

                if (emeraldCount <= 0) {
                    ci.cancel();
                    EmeraldTradingSystem.getDebugUtil().infoPlayer(VillagerMixin.class.getName()+" Mixin got Executed!");
                }

            });

        }

    }
}
