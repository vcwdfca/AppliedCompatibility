package appeng.api;

import appeng.api.config.Actionable;
import appeng.api.networking.energy.IEnergySource;
import appeng.api.networking.security.IActionSource;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.IStorageHelper;
import appeng.api.storage.channels.IFluidStorageChannel;
import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEStack;
import com.mekeng.github.common.me.storage.IGasStorageChannel;

public class AppCompatStorageHelper implements IStorageHelper {

    private static final IItemStorageChannel ITEM_CHANNEL = new IItemStorageChannel() {
    };

    private static final IFluidStorageChannel FLUID_CHANNEL = new IFluidStorageChannel() {
    };

    private static final IGasStorageChannel GAS_CHANNEL = new IGasStorageChannel() {
    };

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IAEStack<T>> IStorageChannel<T> getStorageChannel(final Class<?> channel) {
        if (channel == IFluidStorageChannel.class) {
            return (IStorageChannel<T>) FLUID_CHANNEL;
        }
        if (channel == IGasStorageChannel.class) {
            return (IStorageChannel<T>) GAS_CHANNEL;
        }
        return (IStorageChannel<T>) ITEM_CHANNEL;
    }

    @Override
    public <T extends IAEStack<T>> T poweredInsert(final IEnergySource energy,
                                                   final IMEInventory<T> cell,
                                                   final T input,
                                                   final IActionSource src,
                                                   final Actionable mode) {
        return cell.injectItems(input, mode, src);
    }

    @Override
    public <T extends IAEStack<T>> T poweredExtraction(final IEnergySource energy,
                                                       final IMEInventory<T> cell,
                                                       final T request,
                                                       final IActionSource src,
                                                       final Actionable mode) {
        return cell.extractItems(request, mode, src);
    }
}
