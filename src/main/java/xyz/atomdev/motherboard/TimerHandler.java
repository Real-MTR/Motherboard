package xyz.atomdev.motherboard;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.atomdev.motherboard.adapter.TimerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @project Motherboard is a property of MTR
 * @date 6/28/2024
 */

public class TimerHandler {

    @Getter private static JavaPlugin plugin;
    @Getter private final List<Timer> timers = new ArrayList<>();

    public TimerHandler(JavaPlugin plugin) {
        TimerHandler.plugin = plugin;
    }

    public void createTimer(TimerAdapter timerAdapter) {
        timers.add(timerAdapter.buildTimer());
        plugin.getServer().getPluginManager().registerEvents(timerAdapter, plugin);
    }
}