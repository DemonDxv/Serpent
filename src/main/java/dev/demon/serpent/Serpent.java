package dev.demon.serpent;

import com.github.retrooper.packetevents.PacketEvents;
import dev.demon.serpent.base.bukkit.BukkitListener;
import dev.demon.serpent.base.check.CheckManager;
import dev.demon.serpent.base.packet.PacketManager;
import dev.demon.serpent.base.user.UserManager;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter
public class Serpent extends JavaPlugin {

    //By default, the anticheat base is using 1.8.8 spigot,
    //If you would like to change this version, edit the pom.xml file.

    @Getter
    private static Serpent instance;
    private CheckManager checkManager;

    private final UserManager userManager = new UserManager();
    private final ScheduledExecutorService checkService = Executors.newSingleThreadScheduledExecutor();


    @Override
    public void onEnable() {

        try {
            instance = this;

            //Init packet events
            PacketEvents.getAPI().init();

            //Listen for incoming and outgoing packets
            PacketEvents.getAPI().getEventManager().registerListener(new PacketManager());

            //Register listener for join and quit events.
            new BukkitListener();

            this.checkManager = new CheckManager();

            //Load the checks separate from the player to make it more accessible
            this.checkManager.loadChecks();

            //Reset all check violation over time
            this.checkService.scheduleAtFixedRate(() -> getUserManager().getUserMap().forEach((uuid, user) ->
                            user.getChecks().forEach(check -> check.setViolations(0))),
                    1L, 3L, TimeUnit.MINUTES);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoad() {
        //Load PacketEvents
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
        PacketEvents.getAPI().load();
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().terminate();
        this.checkService.shutdown();
    }
}
