package github.formlessdragon.appcompat.bridge.gtceu;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import net.minecraft.nbt.NBTTagCompound;

public final class GtceuGridNodeAdapter implements IGridNode {

    private final ae2.api.networking.IGridNode node;

    public GtceuGridNodeAdapter(final ae2.api.networking.IGridNode node) {
        this.node = node;
    }

    @Override
    public IGrid getGrid() {
        return new GtceuGridAdapter(this.node.grid());
    }

    @Override
    public void loadFromNBT(final String name, final NBTTagCompound data) {
        throw new UnsupportedOperationException("GTCEu grid node adapter does not own node NBT loading");
    }

    @Override
    public void saveToNBT(final String name, final NBTTagCompound data) {
        throw new UnsupportedOperationException("GTCEu grid node adapter does not own node NBT saving");
    }

    @Override
    public void setPlayerID(final int playerId) {
        throw new UnsupportedOperationException("GTCEu grid node owner is managed by AENetworkProxy");
    }

    @Override
    public void updateState() {
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("GTCEu grid node lifecycle is managed by AENetworkProxy");
    }

    @Override
    public boolean isActive() {
        return this.node.isActive();
    }

    @Override
    public boolean isPowered() {
        return this.node.isPowered();
    }

    public ae2.api.networking.IGridNode unwrap() {
        return this.node;
    }
}
