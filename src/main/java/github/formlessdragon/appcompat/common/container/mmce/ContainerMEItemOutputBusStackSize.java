package github.formlessdragon.appcompat.common.container.mmce;

import ae2.container.AEBaseContainer;
import ae2.container.guisync.GuiSync;
import github.kasuminova.mmce.common.tile.MEItemOutputBus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerMEItemOutputBusStackSize extends AEBaseContainer {

    private final MEItemOutputBus outputBus;

    @GuiSync(0)
    public int stackSize;

    public ContainerMEItemOutputBusStackSize(final InventoryPlayer inventoryPlayer, final MEItemOutputBus outputBus) {
        super(inventoryPlayer, outputBus);
        this.outputBus = outputBus;
        this.stackSize = outputBus.getConfiguredStackSize();
        this.registerClientAction("setStackSize", Integer.class, this::setStackSize);
    }

    public int getStackSize() {
        return this.stackSize;
    }

    public void setStackSize(final int size) {
        final int clamped = Math.max(1, size);
        if (isClientSide()) {
            this.stackSize = clamped;
            sendClientAction("setStackSize", clamped);
            return;
        }
        this.stackSize = clamped;
        this.outputBus.setConfiguredStackSize(clamped);
    }

    public MEItemOutputBus getOwner() {
        return this.outputBus;
    }

    @Override
    public void broadcastChanges() {
        if (isServerSide()) {
            this.stackSize = this.outputBus.getConfiguredStackSize();
        }
        super.broadcastChanges();
    }

    @Override
    public void onContainerClosed(final EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if (!playerIn.world.isRemote) {
            this.outputBus.setConfiguredStackSize(this.stackSize);
        }
    }
}
