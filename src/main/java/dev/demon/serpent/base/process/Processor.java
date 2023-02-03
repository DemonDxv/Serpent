package dev.demon.serpent.base.process;

import dev.demon.serpent.base.event.Event;
import dev.demon.serpent.base.user.User;
import lombok.Getter;
import org.bukkit.Material;

@Getter
public class Processor extends Event {

    private final String name;


    private final User data;

    public Processor(final User data) {
        this.name = getClass().getAnnotation(ProcessorInfo.class).name();
        this.data = data;
    }
}
