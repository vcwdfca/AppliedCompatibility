package appeng.api;

import appeng.api.storage.IStorageChannel;
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
}
