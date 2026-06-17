package appeng.api.storage.channels;

import appeng.api.storage.IStorageChannel;
import appeng.api.storage.data.IAEStack;
import appeng.fluids.util.AEFluidStack;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidStorageChannel extends IStorageChannel {

    default IAEStack createStack(final Object input) {
        if (input instanceof FluidStack stack) {
            return AEFluidStack.fromFluidStack(stack);
        }
        return null;
    }
}
