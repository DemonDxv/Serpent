package dev.demon.serpent.base.user;

import dev.demon.serpent.Serpent;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class UserManager {
    private final Map<UUID, User> userMap = new ConcurrentHashMap<>();

    public void addUser(Player player) {
        this.userMap.put(player.getUniqueId(), new User(player));
        Serpent.getInstance().getCheckManager().loadToPlayer(new User(player));
    }

    public void removeUser(Player player) {
        this.userMap.remove(player.getUniqueId());
    }

    public User getUser(Player player) {
        return this.userMap.get(player.getUniqueId());
    }

    public User getUser(UUID uuid) {
        return this.userMap.get(uuid);
    }
}
