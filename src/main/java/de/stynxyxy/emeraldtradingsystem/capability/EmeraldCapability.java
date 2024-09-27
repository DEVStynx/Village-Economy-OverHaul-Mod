package de.stynxyxy.emeraldtradingsystem.capability;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.Random;

public class EmeraldCapability implements INBTSerializable<CompoundTag> {
    private Random random = new Random();

    private int emeraldcount = random.nextInt(5,23);

    private ArrayList<MerchantOffer> BlockedOffers = new ArrayList<>();

    public int getEmeraldcount() {
        return emeraldcount;
    }

    public void addEmeralds(int amount) {
        this.emeraldcount += amount;
    }


    public boolean useEmeralds(int amount) {
        if (this.hasenoughemeralds(amount)) {
            emeraldcount -= amount;
            return true;
        }
        return false;
    }

    public boolean hasenoughemeralds( int amount) {
        if (emeraldcount >= amount) {
            return true;
        }
        return false;
    }

    public ArrayList<MerchantOffer> getBlockedOffers() {
        return BlockedOffers;
    }

    public void addBlockedOffer(MerchantOffer offer) {
        BlockedOffers.add(offer);
    }


    /*
     *
     * Be Careful, in this methon every other Offer is getting deleted
     */

    public void setBlockedOffers(MerchantOffer[] blockedOffers) {
        BlockedOffers.clear();
        for (MerchantOffer offer: blockedOffers) {
            BlockedOffers.add(offer);
        }
    }

    public void setBlockedOffer(int i, MerchantOffer offer) {
        BlockedOffers.set(i,offer);

    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Emeralds",emeraldcount);

        ListTag offersList = new ListTag();
        for (MerchantOffer offer: BlockedOffers) {
            CompoundTag tag = offer.createTag();
            offersList.add(tag);
        }
        nbt.put("BlockedOffers",offersList);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        emeraldcount = compoundTag.getInt("Emeralds");

        ListTag offersList = compoundTag.getList("BlockedOffers",10); // 10 für CompoundTag

        BlockedOffers.clear();

        for (int i = 0; i< offersList.size(); i++) {
            CompoundTag offerTag = offersList.getCompound(i);

            MerchantOffer offer = new MerchantOffer(offerTag);
            BlockedOffers.add(offer);
        }
    }
}
