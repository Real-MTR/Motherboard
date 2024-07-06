package xyz.atomdev.motherboard;

import com.lunarclient.bukkitapi.cooldown.LCCooldown;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

/**
 * @project Motherboard is a property of MTR
 * @date 6/28/2024
 */

@Getter
public class Timer {

    private final String name;
    private final ChatColor color;
    private final Integer time;
    private final String postName;
    private final Material item;
    private final Long longtime;
    private final String postMessage;
    private final String expireMessage;

    private final HashMap<Player, Integer> activePlayers = new HashMap<>();
    private final HashMap<Player, BukkitRunnable> activePlayerTaskIds = new HashMap<>();

    public Timer(String name, ChatColor color, Integer seconds, String postName, long longSeconds, Material item, String postMessage, String expireMessage) {
        this.name = name;
        this.color = color;
        this.time = seconds;
        this.longtime = longSeconds;
        this.postName = postName;
        this.item = item;
        this.postMessage = postMessage;
        this.expireMessage = expireMessage;

        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "[TIMER] " + ChatColor.GREEN + "Registered Timer " + ChatColor.YELLOW + "\"" +  name + "\"");
    }

    @SuppressWarnings("unused")
    public void apply(Player toBeApplied){
        if(!activePlayers.containsKey(toBeApplied)) {
            if (postMessage == null) {
                toBeApplied.sendMessage(ChatColor.YELLOW + "You have been " + color + postName + ChatColor.YELLOW + " for " + ChatColor.RED + time + " " + ChatColor.YELLOW + "seconds!");
            } else {
                toBeApplied.sendMessage(ChatColor.translateAlternateColorCodes('&', postMessage));
            }
        }
        if(activePlayers.containsKey(toBeApplied)) {
            activePlayerTaskIds.get(toBeApplied).cancel();
        }

        activePlayers.put(toBeApplied, time);
        new BukkitRunnable() {
            @Override
            public void run() {
                activePlayerTaskIds.put(toBeApplied, this);

                if (activePlayers.get(toBeApplied) <= time && activePlayers.get(toBeApplied) != 1) {
                    activePlayers.put(toBeApplied, activePlayers.get(toBeApplied)-1);
                } else {
                    activePlayers.remove(toBeApplied);

                    if(expireMessage == null) {
                        toBeApplied.sendMessage(ChatColor.YELLOW + "Your " + color + name + ChatColor.YELLOW + " cooldown has expired!");
                    } else {
                        toBeApplied.sendMessage(ChatColor.translateAlternateColorCodes('&', expireMessage));
                    }

                    activePlayerTaskIds.remove(toBeApplied);
                    cancel();
                }
            }

        }.runTaskTimer(TimerHandler.getPlugin(), 20L, 20L);
    }

    public void unapply(Player player) {
        activePlayers.remove(player);

        BukkitRunnable task = activePlayerTaskIds.get(player);

        if(task != null) {
            task.cancel();
            activePlayerTaskIds.remove(player);
        }
    }

    @SuppressWarnings("unused")
    public boolean isActive(Player player) {
        return activePlayers.get(player) != null;
    }
}