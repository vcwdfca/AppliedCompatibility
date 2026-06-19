package appeng.api.networking;

import net.minecraft.nbt.NBTTagCompound;

public interface IGridNode {

    IGrid getGrid();

    default void loadFromNBT(final String name, final NBTTagCompound data) {
        throw new UnsupportedOperationException("Legacy AE grid node NBT load is not implemented");
    }

    default void saveToNBT(final String name, final NBTTagCompound data) {
        throw new UnsupportedOperationException("Legacy AE grid node NBT save is not implemented");
    }

    default void setPlayerID(final int playerId) {
        throw new UnsupportedOperationException("Legacy AE grid node player ownership is not implemented");
    }

    default void updateState() {
        throw new UnsupportedOperationException("Legacy AE grid node state update is not implemented");
    }

    default void destroy() {
        throw new UnsupportedOperationException("Legacy AE grid node destroy is not implemented");
    }

    default boolean isActive() {
        throw new UnsupportedOperationException("Legacy AE grid node active state is not implemented");
    }

    default boolean isPowered() {
        throw new UnsupportedOperationException("Legacy AE grid node powered state is not implemented");
    }

    default IGridBlock getGridBlock() {
        throw new UnsupportedOperationException("Legacy AE grid block access is not implemented");
    }
}
