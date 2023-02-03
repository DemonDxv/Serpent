package dev.demon.serpent.base.check.impl.movement.speed;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import dev.demon.serpent.base.check.Check;
import dev.demon.serpent.base.check.CheckData;
import dev.demon.serpent.utils.PacketUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

@CheckData(
        name = "Speed",
        description = "Basic air prediction modification check",
        experimental = true,
        punishmentVL = 12)
public class SpeedA extends Check {

    private double threshold;
    private int exemptTeleportTicks;

    @Override
    public void onPacket(PacketReceiveEvent event) {

        switch (PacketUtil.toPacketReceive(event)) {
            case CLIENT_LOOK:
            case CLIENT_POSITION:
            case CLIENT_POSITION_LOOK:
            case CLIENT_FLYING: {

                this.exemptTeleportTicks -= Math.min(this.exemptTeleportTicks, 1);

                if (this.exemptTeleportTicks > 0
                        || getUser().getPlayer().getAllowFlight()
                        || getUser().getPlayer().getGameMode() != GameMode.SURVIVAL) {
                    this.threshold = 0;
                    return;
                }

                boolean ground = getUser().getProcessorManager().getMovementProcessor().getTo().isOnGround();
                boolean lastGround = getUser().getProcessorManager().getMovementProcessor().getFrom().isOnGround();

                double deltaXZ = getUser().getProcessorManager().getMovementProcessor().getDeltaXZ();
                double lastDeltaXZ = getUser().getProcessorManager().getMovementProcessor().getLastDeltaXZ();

                double previousPredicted = (lastDeltaXZ * 0.91F);

                if (Math.abs(previousPredicted) < 0.005) {
                    previousPredicted = 0.0;
                }

                double totalDifference = deltaXZ - previousPredicted;

                if (!ground && !lastGround) {

                    Bukkit.broadcastMessage("" + totalDifference);

                    if (totalDifference > 0.005) {
                        if (++this.threshold > 2.5) {
                            this.fail("Not following proper friction in air",
                                    "d=" + totalDifference,
                                    "t=" + this.threshold);
                        }
                    } else {
                        this.threshold -= Math.min(this.threshold, .0001);
                    }
                } else {
                    this.threshold -= Math.min(this.threshold, .0001);
                }

                break;
            }
        }
    }

    @Override
    public void onPacket(PacketSendEvent event) {

        switch (PacketUtil.toPacketSend(event)) {

            case SERVER_POSITION: {

                //bad way to exempt, not spoon-feeding you code.
                this.exemptTeleportTicks = 20;

                break;
            }
        }
    }
}