package dev.demon.serpent.base.process;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessorInfo {
    String name();
}
