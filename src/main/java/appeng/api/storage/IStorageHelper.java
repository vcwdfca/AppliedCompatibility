package appeng.api.storage;

import appeng.api.config.Actionable;
import appeng.api.networking.energy.IEnergySource;
import appeng.api.networking.security.IActionSource;
import appeng.api.storage.data.IAEStack;

public interface IStorageHelper {

    <T extends IAEStack<T>> IStorageChannel<T> getStorageChannel(Class<?> channel);

    <T extends IAEStack<T>> T poweredInsert(IEnergySource energy, IMEInventory<T> cell, T input, IActionSource src, Actionable mode);

    <T extends IAEStack<T>> T poweredExtraction(IEnergySource energy, IMEInventory<T> cell, T request, IActionSource src, Actionable mode);
}
