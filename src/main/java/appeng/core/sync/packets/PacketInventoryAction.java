package appeng.core.sync.packets;

import appeng.api.storage.data.IAEItemStack;
import appeng.container.slot.IJEITargetSlot;
import appeng.core.sync.AppEngPacket;
import appeng.helpers.InventoryAction;

public class PacketInventoryAction extends AppEngPacket {

    public PacketInventoryAction(final InventoryAction action, final IJEITargetSlot slot, final IAEItemStack slotItem) {
    }
}
