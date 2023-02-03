package dev.demon.serpent.base.check;

import dev.demon.serpent.base.check.impl.movement.speed.SpeedA;
import dev.demon.serpent.base.check.impl.other.badpackets.BadPacketsA;
import dev.demon.serpent.base.user.User;

import java.util.ArrayList;
import java.util.List;

public class CheckManager {

    public final List<Check> checks = new ArrayList<>();

    public void loadChecks() {
        this.checks.add(new SpeedA());
        this.checks.add(new BadPacketsA());
    }

    public void loadToPlayer(User user) {
        wrapUser(user);
        user.getChecks().addAll(this.checks);
    }

    public void wrapUser(User user) {
        this.checks.forEach(check -> check.setUser(user));
    }
}
