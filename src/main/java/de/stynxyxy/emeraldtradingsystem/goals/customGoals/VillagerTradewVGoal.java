package de.stynxyxy.emeraldtradingsystem.goals.customGoals;

import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import de.stynxyxy.emeraldtradingsystem.util.DebugUtil;

import de.stynxyxy.emeraldtradingsystem.util.TradeUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class VillagerTradewVGoal extends Goal {
    private final Villager villager;
    private final double tradeRadius = 4d;
    private final Random random;
    private int cooldownTicks;
    private final int chance;
    private DebugUtil debugUtil;

    public VillagerTradewVGoal(Villager villager) {
        this.villager = villager;
        this.chance = 100;
        this.random = new Random();
        this.cooldownTicks = 0;
        this.debugUtil = EmeraldTradingSystem.getDebugUtil();
    }

    @Override
    public boolean canUse() {
        // Check if the goal is on cooldown
        if (cooldownTicks > 0) {
            cooldownTicks--;
            return false;
        }

        // Temporarily remove random chance to test logic

        if (random.nextInt(chance) == 1) {
            return false;
        }


        // Debugging: Checking if conditions are met
        this.debugUtil.infoPlayer(Component.literal("Villager offers empty: " + villager.getOffers().isEmpty() +
                " | Near villagers: " + !getNearestVillagers(TargetingConditions.forNonCombat().range(tradeRadius), villager.level()).isEmpty()));

        // Log all nearby villagers for debugging
        for (Villager v : getNearestVillagers(TargetingConditions.forNonCombat().range(tradeRadius), villager.level())) {
            this.debugUtil.infoPlayer(Component.literal("- " + v.getName() + " uuid: " + v.getUUID().toString()));
        }

        this.debugUtil.info("Cooldown for villager: "+cooldownTicks);
        // Only proceed if the villager has offers and nearby villagers are found
        return !villager.getOffers().isEmpty() &&
                !getNearestVillagers(TargetingConditions.forNonCombat().range(tradeRadius), villager.level()).isEmpty();
    }

    List<Villager> getNearestVillagers(TargetingConditions conditions, Level world) {
        // Set AABB (bounding box) for finding nearby villagers
        double x = villager.getX() - tradeRadius;
        double y = villager.getY() - tradeRadius;
        double z = villager.getZ() - tradeRadius;
        Vec3 pos1 = new Vec3(x, y, z);

        double x2 = villager.getX() + tradeRadius;
        double y2 = villager.getY() + tradeRadius;
        double z2 = villager.getZ() + tradeRadius;
        Vec3 pos2 = new Vec3(x2, y2, z2);

        AABB aabb = new AABB(pos1, pos2);

        // Debugging: log the AABB for confirmation
        this.debugUtil.info("AABB pos1: " + pos1 + " | pos2: " + pos2);

        // Find and log nearby villagers for debugging
        List<Villager> nearbyVillagers = world.getNearbyEntities(Villager.class, conditions, villager, aabb);
        this.debugUtil.info("Nearby villagers found: " + nearbyVillagers.size());

        return nearbyVillagers;
    }

    @Override
    public void tick() {
        super.tick();

        // Get nearby villagers and attempt to trade
        List<Villager> villagers = getNearestVillagers(TargetingConditions.forNonCombat().range(tradeRadius), villager.level());

        for (Villager tradingOpponent : villagers) {
            if (tradingOpponent != villager) {
                // Perform trade logic between two villagers
                TradeUtil.tradewVillager(villager, tradingOpponent);
            }
        }



        // Set cooldown to prevent constant trading
        this.cooldownTicks = 1000;
    }
}
