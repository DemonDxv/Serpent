package dev.demon.serpent.utils.location;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

@Getter
@Setter
public class FlyingLocation {
    private int tick;
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private World world;

    public FlyingLocation() {

    }

    public FlyingLocation(int tick, double posX, double posY, double posZ) {
        this.tick = tick;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public FlyingLocation(int tick, double posX, double posY, double posZ, float yaw, float pitch) {
        this.tick = tick;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public FlyingLocation(World world, int tick, double posX, double posY, double posZ) {
        this.world = world;
        this.tick = tick;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public FlyingLocation(World world, int tick, double posX, double posY, double posZ, float yaw, float pitch,
                          boolean onGround) {
        this.world = world;
        this.tick = tick;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    public Vector toVector() {
        return new Vector(this.posX, this.posY, this.posZ);
    }

    public Location toLocation(World world) {
        return new Location(world, this.posX, this.posY, this.posZ, this.yaw, this.pitch);
    }

    public FlyingLocation add(double x, double y, double z) {
        return new FlyingLocation(this.tick, this.posX + x, this.posY + y,
                this.posZ + z, this.yaw, this.pitch);
    }

    public FlyingLocation clone() {
        return new FlyingLocation(
                this.world,
                this.tick,
                this.posX,
                this.posY,
                this.posZ,
                this.yaw,
                this.pitch,
                this.onGround
        );
    }

    public double distanceSquaredXZ(FlyingLocation o) {
        if (o.getWorld() != null && getWorld() != null && o.getWorld() == getWorld()) {
            return NumberConversions.square(this.getPosX() - o.getPosX())
                    + NumberConversions.square(this.getPosZ() - o.getPosZ());
        }
        return 0.0;
    }
}
