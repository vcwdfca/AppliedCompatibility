package github.formlessdragon.appcompat.common.container.mmce;

import ae2.api.inventories.PlatformInventoryWrapper;
import ae2.api.stacks.AEItemKey;
import ae2.api.stacks.GenericStack;
import ae2.container.AEBaseContainer;
import ae2.container.SlotSemantics;
import ae2.container.slot.FakeSlot;
import ae2.container.slot.OutputSlot;
import github.kasuminova.mmce.common.tile.MEItemInputBus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ContainerMEItemInputBus extends AEBaseContainer {

    private static final NumberFormat US_NUMBER = NumberFormat.getIntegerInstance(Locale.US);

    private final MEItemInputBus owner;

    public ContainerMEItemInputBus(final MEItemInputBus owner, final EntityPlayer player) {
        super(player.inventory, owner);
        this.owner = owner;

        final PlatformInventoryWrapper config = new PlatformInventoryWrapper(owner.getConfigInventory());
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                this.addSlot(new ItemConfigSlot(config, y * 4 + x, 8 + 18 * x, 35 + 18 * y), SlotSemantics.CONFIG);
            }
        }

        final PlatformInventoryWrapper internal = new PlatformInventoryWrapper(owner.getInternalInventory());
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                this.addSlot(new CachedStorageSlot(internal, y * 4 + x, 98 + 18 * x, 35 + 18 * y), SlotSemantics.STORAGE);
            }
        }

        this.addPlayerInventorySlots(8, 195 - 72);

        this.registerClientAction("setConfigCount", String.class, this::setConfigCount);
        this.registerClientAction("setConfigStack", String.class, this::setConfigStack);
    }

    private static ItemStack appcompat$parseItem(final String spec) {
        final int hash = spec.indexOf('#');
        final int meta = hash >= 0 ? safeInt(spec.substring(hash + 1)) : 0;
        final String id = hash >= 0 ? spec.substring(0, hash) : spec;
        final Item item = Item.getByNameOrId(id);
        if (item == null) {
            return ItemStack.EMPTY;
        }
        return new ItemStack(item, 1, meta);
    }

    private static int safeInt(final String s) {
        try {
            return Integer.parseInt(s);
        } catch (final NumberFormatException e) {
            return 0;
        }
    }

    private void setConfigStack(final String payload) {
        final int sep = payload.indexOf(':');
        if (sep <= 0) {
            return;
        }
        final int slotNumber;
        try {
            slotNumber = Integer.parseInt(payload.substring(0, sep));
        } catch (final NumberFormatException e) {
            return;
        }
        final String itemId = payload.substring(sep + 1);
        if (slotNumber < 0 || slotNumber >= this.inventorySlots.size()) {
            return;
        }
        final Slot slot = this.inventorySlots.get(slotNumber);
        if (!(slot instanceof ItemConfigSlot)) {
            return;
        }
        final ItemStack stack = appcompat$parseItem(itemId);
        slot.putStack(stack);
    }

    public void fillRecipe(final List<ItemStack> inputs) {
        int cursor = 0;
        for (final Slot slot : this.inventorySlots) {
            if (cursor >= inputs.size()) {
                break;
            }
            if (slot instanceof ItemConfigSlot && slot.getStack().isEmpty()) {
                final ItemStack input = inputs.get(cursor++);
                if (input != null && !input.isEmpty()) {
                    sendSetConfigStack(slot.slotNumber, input);
                }
            }
        }
    }

    public void setConfigStackClient(final int slotNumber, final ItemStack stack) {
        sendSetConfigStack(slotNumber, stack);
    }

    private void sendSetConfigStack(final int slotNumber, final ItemStack stack) {
        if (stack.isEmpty()) {
            sendClientAction("setConfigStack", slotNumber + ":");
            return;
        }
        final ResourceLocation id = stack.getItem().getRegistryName();
        if (id == null) {
            return;
        }
        sendClientAction("setConfigStack", slotNumber + ":" + id + "#" + stack.getMetadata());
    }

    private void setConfigCount(final String payload) {
        final int sep = payload.indexOf(':');
        if (sep <= 0) {
            return;
        }
        final int slotNumber;
        final int amount;
        try {
            slotNumber = Integer.parseInt(payload.substring(0, sep));
            amount = Integer.parseInt(payload.substring(sep + 1));
        } catch (final NumberFormatException e) {
            return;
        }
        if (amount <= 0 || slotNumber < 0 || slotNumber >= this.inventorySlots.size()) {
            return;
        }
        final Slot slot = this.inventorySlots.get(slotNumber);
        if (!(slot instanceof ItemConfigSlot)) {
            return;
        }
        final ItemStack stack = slot.getStack();
        if (stack.isEmpty()) {
            return;
        }
        final ItemStack updated = stack.copy();
        updated.setCount(amount);
        slot.putStack(updated);
    }

    public void sendSetConfigCount(final int slotNumber, final int amount) {
        sendClientAction("setConfigCount", slotNumber + ":" + amount);
    }

    public MEItemInputBus getOwner() {
        return this.owner;
    }

    public static final class ItemConfigSlot extends FakeSlot {

        private ItemConfigSlot(final PlatformInventoryWrapper inventory, final int slotIndex, final int x, final int y) {
            super(inventory, slotIndex, x, y);
            setNotDraggable();
        }

        public static boolean isItemFilterStack(final ItemStack stack) {
            return toItemFilterStack(stack).isEmpty() == stack.isEmpty();
        }

        public static ItemStack getItemFilterStack(final ItemStack stack) {
            final ItemStack filterStack = toItemFilterStack(stack);
            return filterStack.isEmpty() ? ItemStack.EMPTY : filterStack.copy();
        }

        private static int saturatingAmount(final long amount) {
            if (amount < 1) {
                return 1;
            }
            if (amount > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }
            return (int) amount;
        }

        private static ItemStack toItemFilterStack(final ItemStack stack) {
            if (stack.isEmpty()) {
                return ItemStack.EMPTY;
            }
            final GenericStack genericStack = GenericStack.unwrapItemStack(stack);
            if (genericStack != null) {
                if (genericStack.what() instanceof AEItemKey itemKey) {
                    return itemKey.toStack(saturatingAmount(genericStack.amount()));
                }
                return ItemStack.EMPTY;
            }
            return AEItemKey.of(stack) == null ? ItemStack.EMPTY : stack;
        }

        @Override
        public boolean canSetFilterTo(final ItemStack stack) {
            if (stack.isEmpty()) {
                return super.canSetFilterTo(stack);
            }
            final ItemStack filterStack = toItemFilterStack(stack);
            return !filterStack.isEmpty() && super.canSetFilterTo(filterStack);
        }

        @Override
        public void putStack(final ItemStack stack) {
            if (stack.isEmpty()) {
                super.putStack(ItemStack.EMPTY);
                return;
            }
            final ItemStack filterStack = toItemFilterStack(stack);
            if (!filterStack.isEmpty()) {
                super.putStack(filterStack.copy());
            }
        }

        @Override
        public void increase(final ItemStack stack) {
            putStack(stack);
        }

        @Override
        public void decrease(final ItemStack stack) {
            if (stack.isEmpty()) {
                final ItemStack current = getStack();
                if (current.isEmpty()) {
                    return;
                }
                final ItemStack updated = current.copy();
                updated.shrink(1);
                putStack(updated);
                return;
            }
            final ItemStack filterStack = toItemFilterStack(stack);
            if (!filterStack.isEmpty()) {
                final ItemStack current = getStack();
                if (ItemStack.areItemsEqual(current, filterStack) && ItemStack.areItemStackTagsEqual(current, filterStack)) {
                    final ItemStack updated = current.copy();
                    updated.grow(1);
                    putStack(updated);
                } else {
                    filterStack.setCount(1);
                    putStack(filterStack);
                }
            }
        }

        @Override
        public void setGenericFilter(final GenericStack stack) {
            if (stack != null && stack.what() instanceof AEItemKey itemKey) {
                putStack(itemKey.toStack(saturatingAmount(stack.amount())));
            }
        }
    }

    private static final class CachedStorageSlot extends OutputSlot {

        private CachedStorageSlot(final PlatformInventoryWrapper inventory, final int slotIndex, final int x, final int y) {
            super(inventory, slotIndex, x, y, null);
        }

        @Override
        public ItemStack getRawStack() {
            return super.getRawStack();
        }

        @Override
        public List<ITextComponent> getCustomTooltip(final ItemStack carriedItem) {
            final ItemStack stack = super.getRawStack();
            if (stack.isEmpty()) {
                return super.getCustomTooltip(carriedItem);
            }
            return Collections.singletonList(new TextComponentTranslation("gui.meitembus.item_cached", US_NUMBER.format(stack.getCount())));
        }
    }
}
