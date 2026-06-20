package com.mekeng.github.common.me.storage;

import appeng.api.storage.IStorageChannel;
import com.mekeng.github.common.me.data.IAEGasStack;
import com.mekeng.github.common.me.data.impl.AEGasStack;
import mekanism.api.gas.GasStack;

public interface IGasStorageChannel extends IStorageChannel<IAEGasStack> {

    @Override
    default IAEGasStack createStack(final Object input) {
        if (input instanceof GasStack stack) {
            return AEGasStack.of(stack);
        }
        return null;
    }
}
