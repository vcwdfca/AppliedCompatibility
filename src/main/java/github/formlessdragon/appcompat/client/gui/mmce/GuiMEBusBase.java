package github.formlessdragon.appcompat.client.gui.mmce;

import ae2.api.stacks.AEItemKey;
import ae2.api.stacks.AmountFormat;
import ae2.api.stacks.GenericStack;
import ae2.client.gui.AEBaseGui;
import ae2.client.gui.me.common.StackSizeRenderer;
import ae2.client.gui.style.GuiStyle;
import ae2.container.AEBaseContainer;
import ae2.container.slot.AppEngSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class GuiMEBusBase<T extends AEBaseContainer> extends AEBaseGui<T> {

    protected GuiMEBusBase(final T container, final InventoryPlayer playerInventory, final GuiStyle style) {
        super(container, playerInventory, style);
    }

    private static ItemStack singleCount(final ItemStack stack) {
        final ItemStack copy = stack.copy();
        copy.setCount(1);
        return copy;
    }

    @Override
    protected boolean shouldAddToolbar() {
        return false;
    }

    @Override
    public void drawBG(final int offsetX, final int offsetY, final int mouseX, final int mouseY, final float partialTicks) {
        super.drawBG(offsetX, offsetY, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawSlot(final Slot slot) {
        if (slot instanceof AppEngSlot appEngSlot && shouldRenderSingleCountSlot(slot)) {
            final ItemStack rawStack = appEngSlot.getRawStack();
            if (rawStack.isEmpty()) {
                super.drawSlot(slot);
            } else {
                super.drawSlot(new SingleCountSlot(slot));
                final String amountText = getSlotAmountText(slot, appEngSlot, rawStack, singleCount(rawStack));
                if (amountText != null) {
                    StackSizeRenderer.renderSizeLabel(this.fontRenderer, slot.xPos, slot.yPos, amountText, false);
                }
            }
        } else {
            super.drawSlot(slot);
        }
    }

    protected boolean shouldRenderSingleCountSlot(final Slot slot) {
        return false;
    }

    @Override
    protected @Nullable String getSlotAmountText(final Slot slot, final AppEngSlot appEngSlot, final ItemStack rawStack, final ItemStack displayStack) {
        final GenericStack genericStack = GenericStack.unwrapItemStack(rawStack);
        if (genericStack != null) {
            if (genericStack.amount() <= 1) {
                return null;
            }
            return genericStack.what().formatAmount(genericStack.amount(), AmountFormat.SLOT);
        }
        if (rawStack.isEmpty() || rawStack.getCount() <= 1) {
            return null;
        }
        final AEItemKey itemKey = AEItemKey.of(rawStack);
        if (itemKey == null) {
            return Integer.toString(rawStack.getCount());
        }
        return itemKey.formatAmount(rawStack.getCount(), AmountFormat.SLOT);
    }

    private static final class SingleCountSlot extends Slot {

        private final Slot delegate;

        private SingleCountSlot(final Slot delegate) {
            super(delegate.inventory, delegate.getSlotIndex(), delegate.xPos, delegate.yPos);
            this.delegate = delegate;
            this.slotNumber = delegate.slotNumber;
        }

        @Override
        public boolean isItemValid(final ItemStack stack) {
            return this.delegate.isItemValid(stack);
        }

        @Override
        public ItemStack getStack() {
            final ItemStack stack = this.delegate.getStack();
            if (stack.isEmpty()) {
                return ItemStack.EMPTY;
            }
            return singleCount(stack);
        }

        @Override
        public boolean getHasStack() {
            return this.delegate.getHasStack();
        }

        @Override
        public void putStack(final ItemStack stack) {
            this.delegate.putStack(stack);
        }

        @Override
        public void onSlotChanged() {
            this.delegate.onSlotChanged();
        }

        @Override
        public int getSlotStackLimit() {
            return this.delegate.getSlotStackLimit();
        }

        @Override
        public int getItemStackLimit(final ItemStack stack) {
            return this.delegate.getItemStackLimit(stack);
        }

        @Override
        public ItemStack decrStackSize(final int amount) {
            return this.delegate.decrStackSize(amount);
        }

        @Override
        public boolean canTakeStack(final EntityPlayer playerIn) {
            return this.delegate.canTakeStack(playerIn);
        }

        @Override
        public boolean isHere(final IInventory inv, final int slotIn) {
            return this.delegate.isHere(inv, slotIn);
        }

        @Override
        public boolean isEnabled() {
            return this.delegate.isEnabled();
        }
    }
}
