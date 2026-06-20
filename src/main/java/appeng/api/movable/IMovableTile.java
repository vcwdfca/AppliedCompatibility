package appeng.api.movable;

import ae2.api.movable.IBlockEntityMoveStrategy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMovableTile extends IBlockEntityMoveStrategy {

    boolean prepareToMove();

    void doneMoving();

    @Override
    default boolean canHandle(final Class<? extends TileEntity> blockEntityClass) {
        return IMovableTile.class.isAssignableFrom(blockEntityClass);
    }

    @Override
    default NBTTagCompound beginMove(final TileEntity blockEntity) {
        if (!(blockEntity instanceof IMovableTile movable)) {
            throw new IllegalArgumentException("Legacy movable tile strategy received non-IMovableTile " + blockEntity.getClass().getName());
        }
        if (!movable.prepareToMove()) {
            return null;
        }
        return blockEntity.writeToNBT(new NBTTagCompound());
    }

    @Override
    default boolean completeMove(final TileEntity blockEntity, final IBlockState state, final NBTTagCompound data,
                                 final World world, final BlockPos pos) {
        if (!(blockEntity instanceof IMovableTile)) {
            throw new IllegalArgumentException("Legacy movable tile strategy received non-IMovableTile " + blockEntity.getClass().getName());
        }
        data.setInteger("x", pos.getX());
        data.setInteger("y", pos.getY());
        data.setInteger("z", pos.getZ());
        final TileEntity movedBlockEntity = TileEntity.create(world, data);
        if (movedBlockEntity == null) {
            return false;
        }
        world.setTileEntity(pos, movedBlockEntity);
        if (movedBlockEntity instanceof IMovableTile m) {
            m.doneMoving();
        }
        return true;
    }
}
