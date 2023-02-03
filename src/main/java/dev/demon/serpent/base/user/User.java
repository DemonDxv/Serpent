package dev.demon.serpent.base.user;

import dev.demon.serpent.base.check.Check;
import dev.demon.serpent.base.process.Processor;
import dev.demon.serpent.base.process.ProcessorManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class User {

    private final Player player;
    private final UUID uuid;

    private final String userName;

    private final List<Check> checks;

    private ProcessorManager processorManager;

    public User(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.userName = player.getName();
        this.checks = new ArrayList<>();
        this.processorManager = new ProcessorManager(this);
    }
}
