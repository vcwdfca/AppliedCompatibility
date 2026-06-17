package github.formlessdragon.appcompat.common.container.mmce;

import ae2.api.inventories.InternalInventory;
import ae2.api.upgrades.IUpgradeInventory;
import ae2.api.upgrades.UpgradeInventories;
import github.formlessdragon.appcompat.bridge.mmce.AppCompatMMCEHooks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

public class MirroredMachineUpgradeInventory implements IUpgradeInventory {

    private final InternalInventory backing;
    private final IUpgradeInventory standard;
    private boolean syncing;

    public MirroredMachineUpgradeInventory(final Item machineItem, final int slots, final InternalInventory backing) {
        AppCompatMMCEHooks.init();
        this.backing = backing;
        this.standard = UpgradeInventories.forMachine(machineItem, slots, this::pushToBacking);
        this.pullFromBacking();
    }

    public void pullFromBacking() {
        if (this.syncing) {
            return;
        }
        this.syncing = true;
        try {
            final int size = Math.min(this.standard.size(), this.backing.size());
            for (int i = 0; i < size; i++) {
                final ItemStack source = this.backing.getStackInSlot(i);
                final ItemStack current = this.standard.getStackInSlot(i);
                if (!ItemStack.areItemStacksEqual(current, source)) {
                    this.standard.setItemDirect(i, source.copy());
                }
            }
            for (int i = size; i < this.standard.size(); i++) {
                if (!this.standard.getStackInSlot(i).isEmpty()) {
                    this.standard.setItemDirect(i, ItemStack.EMPTY);
                }
            }
        } finally {
            this.syncing = false;
        }
    }

    public void pushToBacking() {
        if (this.syncing) {
            return;
        }
        this.syncing = true;
        try {
            final int size = Math.min(this.standard.size(), this.backing.size());
            for (int i = 0; i < size; i++) {
                final ItemStack source = this.standard.getStackInSlot(i);
                final ItemStack current = this.backing.getStackInSlot(i);
                if (!ItemStack.areItemStacksEqual(current, source)) {
                    this.backing.setItemDirect(i, source.copy());
                }
            }
            for (int i = size; i < this.backing.size(); i++) {
                if (!this.backing.getStackInSlot(i).isEmpty()) {
                    this.backing.setItemDirect(i, ItemStack.EMPTY);
                }
            }
        } finally {
            this.syncing = false;
        }
    }

    @Override
    public Item getUpgradableItem() {
        return this.standard.getUpgradableItem();
    }

    @Override
    public int getInstalledUpgrades(final Item upgradeCard) {
        return this.standard.getInstalledUpgrades(upgradeCard);
    }

    @Override
    public int getMaxInstalled(final Item upgradeCard) {
        return this.standard.getMaxInstalled(upgradeCard);
    }

    @Override
    public void readFromNBT(final NBTTagCompound data, final String subtag) {
        throw new UnsupportedOperationException("Mirrored machine upgrades are persisted by the MMCE host");
    }

    @Override
    public void writeToNBT(final NBTTagCompound data, final String subtag) {
        throw new UnsupportedOperationException("Mirrored machine upgrades are persisted by the MMCE host");
    }

    @Override
    public int size() {
        return this.standard.size();
    }

    @Override
    public boolean isEmpty() {
        return this.standard.isEmpty();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(final int slot) {
        return this.standard.getStackInSlot(slot);
    }

    @Override
    public void setItemDirect(final int slot, @NotNull final ItemStack stack) {
        this.standard.setItemDirect(slot, stack);
        this.pushToBacking();
    }

    @Override
    public int getSlotLimit(final int slot) {
        return this.standard.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(final int slot, final ItemStack stack) {
        return this.standard.isItemValid(slot, stack);
    }

    @Override
    public @NotNull ItemStack insertItem(final int slot, @NotNull final ItemStack stack, final boolean simulate) {
        final ItemStack result = this.standard.insertItem(slot, stack, simulate);
        if (!simulate) {
            this.pushToBacking();
        }
        return result;
    }

    @Override
    public @NotNull ItemStack extractItem(final int slot, final int amount, final boolean simulate) {
        final ItemStack result = this.standard.extractItem(slot, amount, simulate);
        if (!simulate) {
            this.pushToBacking();
        }
        return result;
    }

    @Override
    public InternalInventory getSlotInv(final int slot) {
        return this.standard.getSlotInv(slot);
    }
}
