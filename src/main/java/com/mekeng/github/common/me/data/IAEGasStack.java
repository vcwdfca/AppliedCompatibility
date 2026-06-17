package com.mekeng.github.common.me.data;

import ae2.api.config.FuzzyMode;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.data.IAEStack;
import io.netty.buffer.ByteBuf;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;

public interface IAEGasStack extends IAEStack {

    @Override
    IAEGasStack copy();

    GasStack getGasStack();

    Gas getGas();

    long getStackSize();

    IAEGasStack setStackSize(long size);

    long getCountRequestable();

    IAEGasStack setCountRequestable(long countRequestable);

    boolean isCraftable();

    IAEGasStack setCraftable(boolean isCraftable);

    IAEGasStack reset();

    boolean isMeaningful();

    void incStackSize(long amount);

    void decStackSize(long amount);

    void incCountRequestable(long amount);

    void decCountRequestable(long amount);

    void writeToNBT(NBTTagCompound data);

    boolean fuzzyComparison(IAEGasStack other, FuzzyMode mode);

    void writeToPacket(ByteBuf data) throws IOException;

    IAEGasStack empty();

    void add(IAEGasStack stack);

    boolean isItem();

    boolean isFluid();

    IStorageChannel getChannel();

    ItemStack asItemStackRepresentation();
}
