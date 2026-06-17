package appeng.api.storage.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IAEFluidStack extends IAEStack {

    @Override
    IAEFluidStack copy();

    Fluid getFluid();

    FluidStack getFluidStack();

    long getStackSize();

    IAEStack setStackSize(long size);

    void writeToNBT(NBTTagCompound data);
}
