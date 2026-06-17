package github.formlessdragon.appcompat.common.container.mmce;

import ae2.api.inventories.PlatformInventoryWrapper;
import ae2.container.AEBaseContainer;
import ae2.container.SlotSemantics;
import ae2.container.slot.OutputSlot;
import github.kasuminova.mmce.common.tile.MEItemOutputBus;
import hellfirepvp.modularmachinery.ModularMachinery;
import hellfirepvp.modularmachinery.common.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ContainerMEItemOutputBus extends AEBaseContainer {

    private static final NumberFormat US_NUMBER = NumberFormat.getIntegerInstance(Locale.US);

    private final MEItemOutputBus owner;

    public ContainerMEItemOutputBus(final MEItemOutputBus owner, final EntityPlayer player) {
        super(player.inventory, owner);
        this.owner = owner;

        final PlatformInventoryWrapper internal = new PlatformInventoryWrapper(owner.getInternalInventory());
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlot(new CachedOutputSlot(internal, y * 9 + x, 0, 0), SlotSemantics.STORAGE);
            }
        }

        this.addPlayerInventorySlots(0, 0);

        this.registerClientAction("openStackSize", this::openStackSize);
    }

    private void openStackSize() {
        final EntityPlayer player = getPlayerInventory().player;
        if (player.world.isRemote) {
            return;
        }
        player.openGui(
            ModularMachinery.MODID,
            CommonProxy.GuiType.ME_ITEM_OUTPUT_BUS_STACK_SIZE.ordinal(),
            player.world,
            this.owner.getPos().getX(),
            this.owner.getPos().getY(),
            this.owner.getPos().getZ());
    }

    public void requestOpenStackSize() {
        sendClientAction("openStackSize");
    }

    public MEItemOutputBus getOwner() {
        return this.owner;
    }

    private static final class CachedOutputSlot extends OutputSlot {

        private CachedOutputSlot(final PlatformInventoryWrapper inventory, final int slotIndex, final int x, final int y) {
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
