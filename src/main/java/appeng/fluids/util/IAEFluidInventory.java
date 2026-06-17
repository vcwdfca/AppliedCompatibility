package appeng.fluids.util;

import appeng.util.inv.InvOperation;
import net.minecraftforge.fluids.FluidStack;

public interface IAEFluidInventory {

    void onFluidInventoryChanged(IAEFluidTank inv, int slot);

    void onFluidInventoryChanged(IAEFluidTank inv, int slot, InvOperation operation, FluidStack added, FluidStack removed);
}
