package appeng.api.util;

import net.minecraft.util.EnumFacing;

public enum AEPartLocation {
    UP,
    DOWN,
    NORTH,
    SOUTH,
    EAST,
    WEST,
    INTERNAL;

    public static AEPartLocation fromFacing(final EnumFacing side) {
        return INTERNAL;
    }
}
