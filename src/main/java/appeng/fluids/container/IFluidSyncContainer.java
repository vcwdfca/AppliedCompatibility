package appeng.fluids.container;

import appeng.api.storage.data.IAEFluidStack;

import java.util.Map;

public interface IFluidSyncContainer {

    void receiveFluidSlots(Map<Integer, IAEFluidStack> fluids);
}
