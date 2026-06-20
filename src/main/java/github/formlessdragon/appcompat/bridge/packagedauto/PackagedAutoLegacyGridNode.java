package github.formlessdragon.appcompat.bridge.packagedauto;

import ae2.api.networking.IManagedGridNode;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridNode;
import net.minecraft.nbt.NBTTagCompound;

public final class PackagedAutoLegacyGridNode implements IGridNode {

    private final IGridBlock gridBlock;
    private final PackagedAutoNodeAccess access;
    private final PackagedAutoLegacyGrid grid;

    public PackagedAutoLegacyGridNode(final IGridBlock gridBlock, final PackagedAutoNodeAccess access) {
        this.gridBlock = gridBlock;
        this.access = access;
        this.grid = new PackagedAutoLegacyGrid(access);
    }

    @Override
    public IGrid getGrid() {
        return this.grid;
    }

    @Override
    public void loadFromNBT(final String name, final NBTTagCompound data) {
        if (data.hasKey(name)) {
            this.access.appcompat$loadLegacyNode(data.getCompoundTag(name));
        }
    }

    @Override
    public void saveToNBT(final String name, final NBTTagCompound data) {
        final NBTTagCompound nodeTag = new NBTTagCompound();
        this.access.appcompat$saveLegacyNode(nodeTag);
        data.setTag(name, nodeTag);
    }

    @Override
    public void setPlayerID(final int playerId) {
    }

    @Override
    public void updateState() {
        this.access.appcompat$createLegacyNode();
        this.access.appcompat$requestCraftingUpdate();
    }

    @Override
    public void destroy() {
        this.access.appcompat$destroyLegacyNode();
    }

    @Override
    public boolean isActive() {
        final IManagedGridNode node = this.access.appcompat$mainNode();
        return node != null && node.isActive();
    }

    @Override
    public boolean isPowered() {
        final IManagedGridNode node = this.access.appcompat$mainNode();
        return node != null && node.isPowered();
    }

    @Override
    public IGridBlock getGridBlock() {
        return this.gridBlock;
    }
}
