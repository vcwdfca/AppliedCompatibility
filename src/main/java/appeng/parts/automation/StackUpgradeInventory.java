package appeng.parts.automation;

import appeng.util.inv.IAEAppEngInventory;
import net.minecraft.item.ItemStack;

public class StackUpgradeInventory extends UpgradeInventory {

    public StackUpgradeInventory(final ItemStack is, final IAEAppEngInventory parent, final int s) {
        super(s);
    }
}
