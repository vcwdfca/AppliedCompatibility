package appeng.me.helpers;

import appeng.api.networking.GridFlags;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.networking.ticking.ITickManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AENetworkProxy {

    public AENetworkProxy(final IGridProxyable te, final String nbtName, final ItemStack visual, final boolean inWorld) {
    }

    public void setIdlePowerUsage(final double idle) {
    }

    public void setFlags(final GridFlags... requireChannel) {
    }

    public void setOwner(final EntityPlayer player) {
    }

    public ItemStack getMachineRepresentation() {
        return ItemStack.EMPTY;
    }

    public boolean isActive() {
        return false;
    }

    public boolean isPowered() {
        return false;
    }

    public IGridNode getNode() {
        return null;
    }

    public IGrid getGrid() {
        return null;
    }

    public IStorageGrid getStorage() {
        return null;
    }

    public IEnergyGrid getEnergy() {
        return null;
    }

    public ITickManager getTick() {
        return null;
    }

    public void onReady() {
    }

    public void onChunkUnload() {
    }

    public void invalidate() {
    }

    public void readFromNBT(final NBTTagCompound data) {
    }

    public void writeToNBT(final NBTTagCompound data) {
    }
}
