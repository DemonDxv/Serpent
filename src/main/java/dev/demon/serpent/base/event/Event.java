package dev.demon.serpent.base.event;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;

public class Event implements IEvent {

    @Override
    public void onPacket(PacketSendEvent event) {
        //
    }

    @Override
    public void onPacket(PacketReceiveEvent event) {
        //
    }
}