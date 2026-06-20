package github.formlessdragon.appcompat.bridge.ae;

import ae2.api.movable.DefaultBlockEntityMoveStrategy;
import appeng.api.movable.IMovableTile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class LegacyMovableTileMoveStrategy extends DefaultBlockEntityMoveStrategy {

    @Override
    public boolean canHandle(final Class<? extends TileEntity> blockEntityClass) {
        return IMovableTile.class.isAssignableFrom(blockEntityClass);
    }

    @Override
    public NBTTagCompound beginMove(final TileEntity blockEntity) {
        if (!(blockEntity instanceof IMovableTile)) {
            throw new IllegalArgumentException("Legacy movable tile strategy received non-IMovableTile " + blockEntity.getClass().getName());
        }
        if (!((IMovableTile) blockEntity).prepareToMove()) {
            return null;
        }
        return super.beginMove(blockEntity);
    }

    @Override
    public boolean completeMove(final TileEntity blockEntity, final IBlockState state, final NBTTagCompound data,
                                final World world, final BlockPos pos) {
        if (!(blockEntity instanceof IMovableTile)) {
            throw new IllegalArgumentException("Legacy movable tile strategy received non-IMovableTile " + blockEntity.getClass().getName());
        }
        if (!super.completeMove(blockEntity, state, data, world, pos)) {
            return false;
        }
        final TileEntity movedBlockEntity = world.getTileEntity(pos);
        if (movedBlockEntity instanceof IMovableTile) {
            ((IMovableTile) movedBlockEntity).doneMoving();
        }
        return true;
    }
}
