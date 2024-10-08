package de.stynxyxy.emeraldtradingsystem.ui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.stynxyxy.emeraldtradingsystem.EmeraldTradingSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EmeraldTradingSystem.MODID)
public class VillagerTradeMenuOutOfEmeraldsRenderer {

    public static final ResourceLocation OutOfEmeraldsImageLocation = new ResourceLocation("emeraldtradingsystem","textures/gui/outofemeraldsicon.png");
    boolean shouldrender;

    public VillagerTradeMenuOutOfEmeraldsRenderer() {
        
    }

    @SubscribeEvent
    public static void onVillagerTradeScreen(ScreenEvent.Render.Post event) {
        if (event.getScreen() instanceof MerchantScreen) {
            if (true) {
                renderOutOfStockIcon((MerchantScreen) event.getScreen(),event.getGuiGraphics());
            }
        }
    }

    public static void renderOutOfStockIcon(MerchantScreen screen, GuiGraphics guiGraphics) {
        //render Image
        Minecraft mc = Minecraft.getInstance();
        int xPos = screen.getGuiLeft() + 100; // Position
        int yPos = screen.getGuiTop() + 50;   // Position

        RenderSystem.setShaderTexture(0, OutOfEmeraldsImageLocation);
        guiGraphics.blit(OutOfEmeraldsImageLocation, xPos, yPos, 0, 0, 64, 64, 16, 16);  // Image size 64x64
    }



}
