package de.stynxyxy.emeraldtradingsystem.goals.customGoals;

import de.stynxyxy.emeraldtradingsystem.util.TradeUtil;
import net.minecraft.client.Minecraft;
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
    private int chance;

    public int getCooldownTicks() {
        return cooldownTicks;
    }

    public double getTradeRadius() {
        return tradeRadius;
    }

    public Villager getVillager() {
        return villager;
    }



    public VillagerTradewVGoal(Villager villager) {
        this.villager = villager;
        this.chance = 100;

        this.random = new Random();
        this.cooldownTicks = 0;
    }

    @Override
    public boolean canUse() {

        if (cooldownTicks > 0) {
            cooldownTicks--;
            return false;
        }

        if (random.nextInt(chance) == 1) {
            return false;
        }



        return  (!villager.getOffers().isEmpty()) && (!getNearestVillagers(TargetingConditions.DEFAULT,villager.level()).isEmpty());
    }

    List<Villager> getNearestVillagers(TargetingConditions conditions, Level world) {
        //Set AABB for nearest Villager
        double x = villager.getX() - tradeRadius;
        double y = villager.getY() - tradeRadius;
        double z = villager.getZ() - tradeRadius;
        Vec3 pos1 = new Vec3(x,y,z);

        double x2 = villager.getX() + tradeRadius;
        double y2 = villager.getY() + tradeRadius;
        double z2 = villager.getZ() + tradeRadius;
        Vec3 pos2 = new Vec3(x,y,z);
        AABB aabb = new AABB(pos1,pos2);

        return world.getNearbyEntities(Villager.class,conditions,villager,aabb);
    }

    @Override
    public void tick() {
        super.tick();

        List<Villager> villagers = getNearestVillagers(TargetingConditions.DEFAULT,villager.level());

        for (Villager tradingOpponent: villagers) {
            if (tradingOpponent != villager) {
                TradeUtil.performTrade(villager,tradingOpponent);
            }
        }
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Villager traded!"));
        this.cooldownTicks = 1000;

    }
}
