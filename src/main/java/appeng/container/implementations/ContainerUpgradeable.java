package appeng.container.implementations;

import appeng.api.implementations.IUpgradeableHost;
import appeng.container.AEBaseContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;

public class ContainerUpgradeable extends AEBaseContainer {

    public ContainerUpgradeable(final InventoryPlayer ip, final IUpgradeableHost te) {
        super(ip, te);
    }

    @Override
    public void addListener(final IContainerListener listener) {
    }

    @Override
    public void detectAndSendChanges() {
    }

    public void onUpdate(final String field, final Object oldValue, final Object newValue) {
    }
}
