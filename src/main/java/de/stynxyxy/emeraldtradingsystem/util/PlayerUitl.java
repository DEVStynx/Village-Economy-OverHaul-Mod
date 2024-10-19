package de.stynxyxy.emeraldtradingsystem.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class PlayerUitl {
    public static void sendMessage(Component message) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.sendSystemMessage(message);
            return;
        }
        System.out.println("Player is null!");
    }
}
