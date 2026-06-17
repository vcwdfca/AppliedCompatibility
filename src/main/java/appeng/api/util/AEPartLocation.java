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
        if (side == null) {
            return INTERNAL;
        }
        return switch (side) {
            case DOWN -> DOWN;
            case UP -> UP;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
        };
    }

    public EnumFacing getFacing() {
        return switch (this) {
            case DOWN -> EnumFacing.DOWN;
            case UP -> EnumFacing.UP;
            case NORTH -> EnumFacing.NORTH;
            case SOUTH -> EnumFacing.SOUTH;
            case WEST -> EnumFacing.WEST;
            case EAST -> EnumFacing.EAST;
            case INTERNAL -> null;
        };
    }
}
