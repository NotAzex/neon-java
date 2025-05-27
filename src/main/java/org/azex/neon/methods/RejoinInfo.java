package org.azex.neon.methods;

import org.bukkit.Location;

public class RejoinInfo {
    private final Location location;
    private final long leaveTime;

    public RejoinInfo(Location location, long leaveTime) {
        this.location = location.clone();
        this.leaveTime = leaveTime;
    }

    public Location getLocation() {
        return location;
    }

    public long getLeaveTime() {
        return leaveTime;
    }
}
