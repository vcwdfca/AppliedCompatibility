package appeng.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerNull extends Container {

    @Override
    public boolean canInteractWith(final EntityPlayer playerIn) {
        return false;
    }
}
