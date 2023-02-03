package dev.demon.serpent.base.process.processors;

import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import dev.demon.serpent.base.process.Processor;
import dev.demon.serpent.base.process.ProcessorInfo;
import dev.demon.serpent.base.user.User;
import dev.demon.serpent.utils.PacketUtil;
import dev.demon.serpent.utils.location.FlyingLocation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ProcessorInfo(name = "Movement")
public class MovementProcessor extends Processor {

    private final FlyingLocation to = new FlyingLocation();
    private final FlyingLocation from = new FlyingLocation();
    private final FlyingLocation fromFrom = new FlyingLocation();

    private double deltaX, deltaY, deltaZ, deltaXAbs, deltaZAbs, deltaYAbs, lastDeltaX, lastDeltaY, lastDeltaZ,
            lastDeltaXZ, lastDeltaYaw, lastDeltaPitch, lastDeltaYawAbs, lastDeltaPitchAbs,
            deltaXZ, deltaYaw, deltaPitch, deltaYawAbs, deltaPitchAbs;

    private int tick;

    public MovementProcessor(User user) {
        super(user);
    }

    @Override
    public void onPacket(PacketReceiveEvent event) {
        switch (PacketUtil.toPacketReceive(event)) {
            case CLIENT_FLYING:
            case CLIENT_POSITION:
            case CLIENT_LOOK:
            case CLIENT_POSITION_LOOK: {
                WrapperPlayClientPlayerFlying flyingPacket = new WrapperPlayClientPlayerFlying(event);

                double x = flyingPacket.getLocation().getX();
                double y = flyingPacket.getLocation().getY();
                double z = flyingPacket.getLocation().getZ();

                float pitch = flyingPacket.getLocation().getPitch();
                float yaw = flyingPacket.getLocation().getYaw();

                boolean ground = flyingPacket.isOnGround();

                this.fromFrom.setWorld(this.from.getWorld());
                this.from.setWorld(to.getWorld());
                this.to.setWorld(getData().getPlayer().getWorld());

                this.fromFrom.setOnGround(this.from.isOnGround());
                this.from.setOnGround(this.to.isOnGround());
                this.to.setOnGround(ground);

                this.fromFrom.setTick(this.from.getTick());
                this.from.setTick(this.to.getTick());
                this.to.setTick(this.tick);

                if (flyingPacket.hasPositionChanged()) {

                    this.fromFrom.setPosX(this.from.getPosX());
                    this.fromFrom.setPosY(this.from.getPosY());
                    this.fromFrom.setPosZ(this.from.getPosZ());

                    this.from.setPosX(this.to.getPosX());
                    this.from.setPosY(this.to.getPosY());
                    this.from.setPosZ(this.to.getPosZ());

                    this.to.setPosX(x);
                    this.to.setPosY(y);
                    this.to.setPosZ(z);

                    this.lastDeltaX = this.deltaX;
                    this.lastDeltaY = this.deltaY;
                    this.lastDeltaZ = this.deltaZ;

                    this.deltaY = this.to.getPosY() - this.from.getPosY();
                    this.deltaX = this.to.getPosX() - this.from.getPosX();
                    this.deltaZ = this.to.getPosZ() - this.from.getPosZ();

                    this.deltaXAbs = Math.abs(this.deltaX);
                    this.deltaZAbs = Math.abs(this.deltaZ);
                    this.deltaYAbs = Math.abs(this.deltaY);

                    this.lastDeltaXZ = this.deltaXZ;

                    this.deltaXZ = Math.hypot(this.deltaXAbs, this.deltaZAbs);
                }

                if (flyingPacket.hasRotationChanged()) {

                    this.fromFrom.setYaw(this.from.getYaw());
                    this.fromFrom.setPitch(this.from.getPitch());

                    this.from.setYaw(this.to.getYaw());
                    this.from.setPitch(this.to.getPitch());

                    this.to.setPitch(pitch);
                    this.to.setYaw(yaw);

                    this.lastDeltaYaw = this.deltaYaw;
                    this.lastDeltaPitch = this.deltaPitch;

                    this.deltaYaw = this.to.getYaw() - this.from.getYaw();
                    this.deltaPitch = this.to.getPitch() - this.from.getPitch();

                    this.lastDeltaYawAbs = this.deltaYawAbs;
                    this.lastDeltaPitchAbs = this.deltaPitchAbs;

                    this.deltaYawAbs = Math.abs(this.to.getYaw() - this.from.getYaw());
                    this.deltaPitchAbs = Math.abs(this.to.getPitch() - this.from.getPitch());

                }

                ++this.tick;
                break;
            }
        }
    }
}
