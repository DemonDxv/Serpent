package dev.demon.serpent.base.check.impl.other.badpackets;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import dev.demon.serpent.base.check.Check;
import dev.demon.serpent.base.check.CheckData;
import dev.demon.serpent.utils.PacketUtil;

@CheckData(
        name = "BadPackets",
        description = "Basic impossible pitch check",
        punishmentVL = 3)
public class BadPacketsA extends Check {

    @Override
    public void onPacket(PacketReceiveEvent event) {

        switch (PacketUtil.toPacketReceive(event)) {
            case CLIENT_LOOK:
            case CLIENT_POSITION:
            case CLIENT_POSITION_LOOK:
            case CLIENT_FLYING: {

                double pitch = Math.abs(getUser().getProcessorManager().getMovementProcessor().getTo().getPitch());

                if (pitch > 90.0) {
                    this.fail("Impossible pitch rotation",
                            "pitch=" + pitch);
                }

                break;
            }
        }
    }
}