package com.mekeng.github.common.me.inventory.impl;

import com.mekeng.github.common.me.inventory.IGasInventory;
import com.mekeng.github.common.me.inventory.IGasInventoryHost;
import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GasInventoryTest {

    private static final Gas HYDROGEN = registerTestGas("appcompat_hydrogen", 0xFFFFFF);
    private static final Gas OXYGEN = registerTestGas("appcompat_oxygen", 0xCCFFFF);

    private static Gas registerTestGas(final String name, final int tint) {
        final Gas existing = GasRegistry.getGas(name);
        if (existing != null) {
            return existing;
        }
        return GasRegistry.register(new Gas(name, tint));
    }

    @Test
    void storesGasCopiesNotifiesHostAndPersistsToNbt() {
        final RecordingHost host = new RecordingHost();
        final GasInventory inventory = new GasInventory(2, 1000, host);
        final GasStack gas = new GasStack(HYDROGEN, 750);

        inventory.setGas(0, gas);
        gas.amount = 1;

        assertEquals(750, inventory.getGasStack(0).amount);
        assertEquals(1000, inventory.getTanks()[0].getMaxGas());
        assertEquals(List.of(0), host.changedSlots);

        final NBTTagCompound saved = inventory.save();
        final GasInventory loaded = new GasInventory(2, 1000, null);
        loaded.load(saved);

        assertEquals(750, loaded.getGasStack(0).amount);
        assertTrue(loaded.getGasStack(0).isGasEqual(new GasStack(HYDROGEN, 1)));
    }

    @Test
    void receiveAndDrawGasRespectCapacityAndGasType() {
        final GasInventory inventory = new GasInventory(1, 1000, null);
        final GasInvHandler handler = new GasInvHandler(inventory);

        assertEquals(1000, handler.receiveGas(EnumFacing.NORTH, new GasStack(HYDROGEN, 1500), true));
        assertEquals(1000, inventory.getGasStack(0).amount);
        assertEquals(0, handler.receiveGas(EnumFacing.NORTH, new GasStack(OXYGEN, 1), true));
        assertFalse(handler.canReceiveGas(EnumFacing.NORTH, OXYGEN));
        assertFalse(handler.canReceiveGas(EnumFacing.NORTH, HYDROGEN));

        final GasStack simulated = handler.drawGas(EnumFacing.SOUTH, 300, false);
        assertEquals(300, simulated.amount);
        assertEquals(1000, inventory.getGasStack(0).amount);

        final GasStack drawn = handler.drawGas(EnumFacing.SOUTH, 600, true);
        assertEquals(600, drawn.amount);
        assertEquals(400, inventory.getGasStack(0).amount);
        assertTrue(handler.canReceiveGas(EnumFacing.NORTH, HYDROGEN));

        final GasStack byTemplate = handler.drawGas(EnumFacing.SOUTH, new GasStack(HYDROGEN, 1000), true);
        assertEquals(400, byTemplate.amount);
        assertNull(inventory.getGasStack(0));
    }

    private static final class RecordingHost implements IGasInventoryHost {

        private final List<Integer> changedSlots = new ArrayList<>();

        @Override
        public void onGasInventoryChanged(final IGasInventory inventory, final int slot) {
            this.changedSlots.add(slot);
        }
    }
}
