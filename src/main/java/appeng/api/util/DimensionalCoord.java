package appeng.api.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DimensionalCoord {

    private final World world;
    private final BlockPos pos;

    public DimensionalCoord(final TileEntity tile) {
        this.world = tile.getWorld();
        this.pos = tile.getPos();
    }

    public World getWorld() {
        return this.world;
    }

    public BlockPos getPos() {
        return this.pos;
    }
}
