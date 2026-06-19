package appeng.me.helpers;

import ae2.api.networking.GridHelper;
import ae2.api.networking.IGridNode;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGrid;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.networking.ticking.ITickManager;
import github.formlessdragon.appcompat.bridge.gtceu.GtceuEnergyGridAdapter;
import github.formlessdragon.appcompat.bridge.gtceu.GtceuGridAdapter;
import github.formlessdragon.appcompat.bridge.gtceu.GtceuGridNodeAdapter;
import github.formlessdragon.appcompat.bridge.gtceu.GtceuManagedNodeHost;
import github.formlessdragon.appcompat.bridge.gtceu.GtceuStorageGridAdapter;
import github.formlessdragon.appcompat.bridge.gtceu.GtceuTickManagerAdapter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;
import java.util.EnumSet;

public class AENetworkProxy {

    private final IGridProxyable host;
    private final ItemStack visual;
    private final GtceuManagedNodeHost managedHost;

    public AENetworkProxy(final IGridProxyable te, final String nbtName, final ItemStack visual, final boolean inWorld) {
        this.host = te;
        this.visual = visual == null ? ItemStack.EMPTY : visual.copy();
        this.managedHost = new GtceuManagedNodeHost(te, nbtName);
        this.managedHost.node().setVisualRepresentation(this.visual).setInWorldNode(inWorld);
    }

    public void setIdlePowerUsage(final double idle) {
        this.managedHost.node().setIdlePowerUsage(idle);
    }

    public void setFlags(final GridFlags... flags) {
        this.managedHost.node().setFlags(Arrays.stream(flags).map(this::toNewFlag).toArray(ae2.api.networking.GridFlags[]::new));
    }

    public void setValidSides(final EnumSet<EnumFacing> sides) {
        this.managedHost.node().setExposedOnSides(sides);
    }

    public void setOwner(final EntityPlayer player) {
        this.managedHost.node().setOwningPlayer(player);
    }

    public ItemStack getMachineRepresentation() {
        return this.visual.copy();
    }

    public boolean isReady() {
        return this.managedHost.node().isReady();
    }

    public boolean isActive() {
        return this.managedHost.node().isActive();
    }

    public boolean isPowered() {
        return this.managedHost.node().isPowered();
    }

    public appeng.api.networking.IGridNode getNode() {
        final IGridNode node = this.managedHost.newNode();
        return node == null ? null : new GtceuGridNodeAdapter(node);
    }

    public IGrid getGrid() {
        final ae2.api.networking.IGrid grid = this.managedHost.node().getGrid();
        return grid == null ? null : new GtceuGridAdapter(grid);
    }

    public IStorageGrid getStorage() {
        final ae2.api.networking.IGrid grid = this.managedHost.node().getGrid();
        if (grid == null) {
            return null;
        }
        return new GtceuStorageGridAdapter(grid.getStorageService(), grid.getEnergyService());
    }

    public IEnergyGrid getEnergy() {
        final ae2.api.networking.IGrid grid = this.managedHost.node().getGrid();
        if (grid == null) {
            return null;
        }
        return new GtceuEnergyGridAdapter(grid.getEnergyService());
    }

    public ITickManager getTick() {
        final ae2.api.networking.IGrid grid = this.managedHost.node().getGrid();
        if (grid == null) {
            return null;
        }
        return new GtceuTickManagerAdapter(grid.getTickManager());
    }

    public void onReady() {
        final TileEntity tile = tile();
        if (tile == null) {
            this.managedHost.create();
            return;
        }
        GridHelper.onFirstTick(tile, ignored -> this.managedHost.create());
    }

    public void onChunkUnload() {
        this.managedHost.destroy();
    }

    public void invalidate() {
        this.managedHost.destroy();
    }

    public void readFromNBT(final NBTTagCompound data) {
        this.managedHost.load(data);
    }

    public void writeToNBT(final NBTTagCompound data) {
        this.managedHost.save(data);
    }

    public GtceuManagedNodeHost getManagedHost() {
        return this.managedHost;
    }

    private TileEntity tile() {
        if (this.host.getLocation().getWorld() == null || this.host.getLocation().getPos() == null) {
            return null;
        }
        return this.host.getLocation().getWorld().getTileEntity(this.host.getLocation().getPos());
    }

    private ae2.api.networking.GridFlags toNewFlag(final GridFlags flag) {
        return switch (flag) {
            case REQUIRE_CHANNEL, REQUIRE_CHANNEL_POWER -> ae2.api.networking.GridFlags.REQUIRE_CHANNEL;
            case COMPRESSED_CHANNEL -> ae2.api.networking.GridFlags.COMPRESSED_CHANNEL;
            case CANNOT_CARRY -> ae2.api.networking.GridFlags.CANNOT_CARRY;
            case DENSE_CAPACITY -> ae2.api.networking.GridFlags.DENSE_CAPACITY;
            case MULTIBLOCK -> ae2.api.networking.GridFlags.MULTIBLOCK;
        };
    }
}
