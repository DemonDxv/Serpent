package dev.demon.serpent.base.process;

import dev.demon.serpent.base.process.processors.MovementProcessor;
import dev.demon.serpent.base.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProcessorManager {
    private final List<Processor> processors = new ArrayList<>();

    private final MovementProcessor movementProcessor;

    public ProcessorManager(User user) {
        this.processors.add(this.movementProcessor = new MovementProcessor(user));
    }
}