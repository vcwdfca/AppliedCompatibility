package appeng.api.storage;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.storage.data.IAEStack;

public interface IMEInventory<T extends IAEStack<T>> {

    T injectItems(T input, Actionable mode, IActionSource source);

    T extractItems(T request, Actionable mode, IActionSource source);

    appeng.api.storage.data.IItemList<T> getStorageList();
}
