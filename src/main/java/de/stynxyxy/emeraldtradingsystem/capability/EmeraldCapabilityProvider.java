package de.stynxyxy.emeraldtradingsystem.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EmeraldCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    private final EmeraldCapability emeraldCapability = new EmeraldCapability();
    private final LazyOptional<EmeraldCapability> optional = LazyOptional.of(() -> emeraldCapability);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        return capability == CapabilityRegistry.EMERALD_CAPABILITY ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return emeraldCapability.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        emeraldCapability.deserializeNBT(compoundTag);
    }
}
