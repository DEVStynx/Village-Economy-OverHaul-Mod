package de.stynxyxy.emeraldtradingsystem.util;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

public class DebugUtil {

    private boolean debugMode = false;
    final Logger logger = LogUtils.getLogger();


    public DebugUtil(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean getDebugMode() {
        return this.debugMode;
    }
    public boolean setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        return getDebugMode();
    }
    public void info(String i) {
        if (this.debugMode) {
            System.out.println();
            this.logger.info(i);
        }

    }

    public void infoPlayer(String i) {
        if (this.getDebugMode()) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal(i));
        }
    }
    public void infoPlayer(String i,boolean off) {
        if (off) {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal(i));
            }
        } else {
            this.infoPlayer(i);
        }

    }

    public void infoPlayer(Component i) {
        if (this.getDebugMode()) {
            try {
                Minecraft.getInstance().player.sendSystemMessage(i);
            }
            catch (NullPointerException nullPointerException) {
                Log("Player is null: ");
                Log(i.getString());
            }

        }
    }

    public void Log(String i) {
        if (this.getDebugMode()) {
            this.logger.info(i);
        }
    }

    public Logger getLogger() {
        return this.logger;
    }
}
