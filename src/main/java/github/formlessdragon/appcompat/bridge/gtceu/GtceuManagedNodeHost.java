package github.formlessdragon.appcompat.bridge.gtceu;

import ae2.api.networking.GridHelper;
import ae2.api.networking.IGridNode;
import ae2.api.networking.IGridNodeListener;
import ae2.api.networking.IManagedGridNode;
import appeng.me.helpers.IGridProxyable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class GtceuManagedNodeHost {

    private static final IGridNodeListener<GtceuManagedNodeHost> NODE_LISTENER = new NodeListener();

    private final IGridProxyable host;
    private final IManagedGridNode node;
    private boolean destroyed;

    public GtceuManagedNodeHost(final IGridProxyable host, final String tagName) {
        this.host = host;
        this.node = GridHelper.createManagedNode(this, NODE_LISTENER).setTagName(tagName).setInWorldNode(true);
    }

    public IManagedGridNode node() {
        return this.node;
    }

    public void create() {
        if (this.destroyed || this.node.isReady()) {
            return;
        }
        final World world = world();
        final BlockPos pos = pos();
        if (world == null || pos == null) {
            throw new IllegalStateException("GTCEu ME host has no world or position");
        }
        this.node.create(world, pos);
    }

    public void destroy() {
        if (!this.destroyed) {
            this.destroyed = true;
            this.node.destroy();
        }
    }

    public void load(final NBTTagCompound data) {
        this.node.loadFromNBT(data);
    }

    public void save(final NBTTagCompound data) {
        this.node.saveToNBT(data);
    }

    public IGridNode newNode() {
        return this.node.getNode();
    }

    public World world() {
        return this.host.getLocation().getWorld();
    }

    public BlockPos pos() {
        return this.host.getLocation().getPos();
    }

    private static final class NodeListener implements IGridNodeListener<GtceuManagedNodeHost> {

        @Override
        public void onSaveChanges(final GtceuManagedNodeHost nodeOwner, final IGridNode node) {
            final World world = nodeOwner.world();
            final BlockPos pos = nodeOwner.pos();
            if (world == null || pos == null) {
                throw new IllegalStateException("GTCEu ME host cannot save without world or position");
            }
            final TileEntity tile = world.getTileEntity(pos);
            if (tile == null) {
                throw new IllegalStateException("GTCEu ME host tile is missing at " + pos);
            }
            tile.markDirty();
        }

        @Override
        public void onStateChanged(final GtceuManagedNodeHost nodeOwner, final IGridNode node, final State state) {
            nodeOwner.host.gridChanged();
        }

        @Override
        public void onGridChanged(final GtceuManagedNodeHost nodeOwner, final IGridNode node) {
            nodeOwner.host.gridChanged();
        }
    }
}
