package de.stynxyxy.emeraldtradingsystem.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.stynxyxy.emeraldtradingsystem.capability.CapabilityRegistry;
import de.stynxyxy.emeraldtradingsystem.capability.EmeraldCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class VillagerTradeMenuOutOfEmeraldsRenderer {
    public static final ResourceLocation OutOfEmeraldsImageLocation = new ResourceLocation("emeraldtradingsystem", "textures/gui/outofemeraldsicon.png");
    @SubscribeEvent
    public static void onVillagerTradeScreen(ScreenEvent.Render.Post event) {
        if (event.getScreen() instanceof MerchantScreen screen) {
            Level world = Minecraft.getInstance().level;
            if (false) {
                if (false) {
                    renderOutOfStockIcon(screen, event.getGuiGraphics());

                }
            }
        }
    }

    private static int getEmeraldCount(Villager villager) {
        LazyOptional<EmeraldCapability> capability = villager.getCapability(CapabilityRegistry.EMERALD_CAPABILITY);
        return capability.map(EmeraldCapability::getEmeraldcount).orElse(0);  // Return the emerald count
    }

    public static void renderOutOfStockIcon(MerchantScreen screen, GuiGraphics guiGraphics) {
        Minecraft mc = Minecraft.getInstance();
        int xPos = screen.getGuiLeft() + 15;  // Position of the icon
        int yPos = screen.getGuiTop() + 50;   // Position of the icon

        RenderSystem.setShaderTexture(0, OutOfEmeraldsImageLocation);
        guiGraphics.blit(OutOfEmeraldsImageLocation, xPos, yPos, 0, 0, 64, 64, 64, 64);  // Image size 64x64
    }
}
