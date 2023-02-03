package dev.demon.serpent.base.event;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;

public interface IEvent {

    void onPacket(PacketReceiveEvent event);

    void onPacket(PacketSendEvent event);
}