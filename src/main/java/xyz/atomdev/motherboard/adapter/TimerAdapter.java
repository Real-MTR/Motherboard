package xyz.atomdev.motherboard.adapter;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import xyz.atomdev.motherboard.Timer;

/**
 * @project Motherboard is a property of MTR
 * @date 6/28/2024
 */

public interface TimerAdapter extends Listener {
    String getName();
    ChatColor getColor();
    Integer getSeconds();
    String getPostName();
    Material getLunarDisplayItem();
    Long getLongSeconds();
    Timer buildTimer();
    default String getPostMessage() {
        return null;
    }
    default String getExpiredMessage() {
        return null;
    }
}