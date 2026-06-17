package com.mekeng.github.common.me.inventory;

public interface IGasInventoryHost {

    static IGasInventoryHost empty() {
        return EmptyHost.INSTANCE;
    }

    void onGasInventoryChanged(IGasInventory inventory, int slot);

    final class EmptyHost implements IGasInventoryHost {

        private static final EmptyHost INSTANCE = new EmptyHost();

        private EmptyHost() {
        }

        @Override
        public void onGasInventoryChanged(final IGasInventory inventory, final int slot) {
        }
    }
}
