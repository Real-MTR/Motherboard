package xyz.atomdev.motherboard.defaults;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.atomdev.motherboard.Timer;
import xyz.atomdev.motherboard.adapter.TimerAdapter;

/**
 * @project Motherboard is a property of MTR
 * @date 6/28/2024
 */

@Getter
public class EnderPearlTimer implements TimerAdapter, Listener {

    private Timer timer;

    @Override
    public String getName() {
        return "Ender Pearl";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.DARK_PURPLE;
    }

    @Override
    public Integer getSeconds() {
        return 10;
    }

    @Override
    public String getPostName() {
        return "Pearl Cooldowned";
    }

    @Override
    public Material getLunarDisplayItem() {
        return Material.ENDER_PEARL;
    }

    @Override
    public Long getLongSeconds() {
        return Long.valueOf(getSeconds());
    }

    @Override
    public Timer buildTimer() {
        return timer = new Timer(getName(), getColor(), getSeconds(), getPostName(), getLongSeconds(), getLunarDisplayItem(), getPostMessage(), getExpiredMessage());
    }

    @Override
    public String getPostMessage() {
        return "&3[Timer] &eYou are now under &5Ender Pearl &ecooldown for " + getSeconds() + " seconds!";
    }

    @Override
    public String getExpiredMessage() {
        return "&aYour ender pearl cooldown has been expired!";
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPearl(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getItemInHand() == null ||
                player.getItemInHand().getType() == null ||
                player.getItemInHand().getType() != Material.ENDER_PEARL) return;

        if(timer.isActive(player)) {
            event.setCancelled(true);

            int timeLeft = timer.getActivePlayers().get(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are still under ender pearl cooldown for " + timeLeft + " second" + (timeLeft == 1 ? "!" : "s!")));
            return;
        }

        timer.apply(player);
    }
}