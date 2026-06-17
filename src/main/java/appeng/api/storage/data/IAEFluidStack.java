package appeng.api.storage.data;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IAEFluidStack extends IAEStack<IAEFluidStack> {

    @Override
    void add(IAEFluidStack option);

    @Override
    IAEFluidStack copy();

    @Override
    IAEFluidStack setStackSize(long size);

    @Override
    IAEFluidStack setCountRequestable(long countRequestable);

    @Override
    IAEFluidStack setCraftable(boolean craftable);

    @Override
    IAEFluidStack reset();

    @Override
    IAEFluidStack empty();

    Fluid getFluid();

    FluidStack getFluidStack();

    long getStackSize();

    void writeToNBT(NBTTagCompound data);
}
