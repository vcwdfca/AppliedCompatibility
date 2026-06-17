package appeng.api.networking.storage;

import appeng.api.storage.IMEMonitor;
import appeng.api.storage.IStorageChannel;

public interface IStorageGrid {

    IMEMonitor getInventory(IStorageChannel channel);
}
