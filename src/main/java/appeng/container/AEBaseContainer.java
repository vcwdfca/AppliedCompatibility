package appeng.container;

import appeng.helpers.InventoryAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;

public class AEBaseContainer extends Container {

    public AEBaseContainer(final InventoryPlayer ip, final Object anchor) {
    }

    public void doAction(final EntityPlayerMP player, final InventoryAction action, final int slot, final long id) {
    }

    @Override
    public void addListener(final IContainerListener listener) {
    }

    @Override
    public void onContainerClosed(final EntityPlayer playerIn) {
    }

    @Override
    public void detectAndSendChanges() {
    }

    @Override
    public boolean canInteractWith(final EntityPlayer playerIn) {
        return true;
    }
}
