package appeng.fluids.util;

import appeng.api.storage.data.IAEFluidStack;
import net.minecraft.nbt.NBTTagCompound;

public class AEFluidInventory implements IAEFluidTank {

    private final IAEFluidInventory host;
    private final IAEFluidStack[] fluids;

    public AEFluidInventory(final IAEFluidInventory host, final int slots) {
        this.host = host;
        this.fluids = new IAEFluidStack[slots];
    }

    @Override
    public int getSlots() {
        return this.fluids.length;
    }

    @Override
    public IAEFluidStack getFluidInSlot(final int slot) {
        if (slot < 0 || slot >= this.fluids.length) {
            return null;
        }
        return this.fluids[slot];
    }

    @Override
    public void setFluidInSlot(final int slot, final IAEFluidStack fluid) {
        if (slot < 0 || slot >= this.fluids.length) {
            return;
        }
        this.fluids[slot] = fluid == null ? null : fluid.copy();
        if (this.host != null) {
            this.host.onFluidInventoryChanged(this, slot);
        }
    }

    public void readFromNBT(final NBTTagCompound data, final String name) {
        final NBTTagCompound c = data.getCompoundTag(name);
        for (int i = 0; i < this.fluids.length; i++) {
            final NBTTagCompound slotTag = c.getCompoundTag("#" + i);
            this.fluids[i] = slotTag.isEmpty() ? null : AEFluidStack.fromNBT(slotTag);
        }
    }

    public void writeToNBT(final NBTTagCompound data, final String name) {
        final NBTTagCompound c = new NBTTagCompound();
        for (int i = 0; i < this.fluids.length; i++) {
            final NBTTagCompound slotTag = new NBTTagCompound();
            if (this.fluids[i] != null) {
                this.fluids[i].writeToNBT(slotTag);
            }
            c.setTag("#" + i, slotTag);
        }
        data.setTag(name, c);
    }
}
