package de.stynxyxy.emeraldtradingsystem.goals;

import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import de.stynxyxy.emeraldtradingsystem.capability.EmeraldCapability;
import de.stynxyxy.emeraldtradingsystem.goals.customGoals.VillagerTradewVGoal;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = EmeraldTradingSystem.MODID)
public class ModGoals {
    @SubscribeEvent
    public static void onEntityAddGoalEvent(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Villager villager) {
            villager.goalSelector.addGoal(1, new VillagerTradewVGoal(villager));
            EmeraldTradingSystem.getDebugUtil().info("Added Goal to Entity "+villager.getName());  //| for Debugging
        }

    }

}
