package dev.demon.serpent.base.bukkit;

import dev.demon.serpent.Serpent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent event) {
        Serpent.getInstance().getUserManager().addUser(event.getPlayer());
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(final PlayerQuitEvent event) {
        Serpent.getInstance().getUserManager().removeUser(event.getPlayer());
    }

}
