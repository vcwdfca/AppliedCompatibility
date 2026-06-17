package appeng.fluids.util;

import appeng.api.storage.data.IAEFluidStack;

public interface IAEFluidTank {

    int getSlots();

    IAEFluidStack getFluidInSlot(int slot);

    void setFluidInSlot(int slot, IAEFluidStack fluid);
}
