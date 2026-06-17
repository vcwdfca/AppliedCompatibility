package appeng.api;

import appeng.api.storage.IStorageChannel;
import appeng.api.storage.IStorageHelper;
import appeng.api.storage.channels.IFluidStorageChannel;
import appeng.api.storage.channels.IItemStorageChannel;
import com.mekeng.github.common.me.storage.IGasStorageChannel;

public class AppCompatStorageHelper implements IStorageHelper {

    private static final IItemStorageChannel ITEM_CHANNEL = new IItemStorageChannel() {
    };

    private static final IFluidStorageChannel FLUID_CHANNEL = new IFluidStorageChannel() {
    };

    private static final IGasStorageChannel GAS_CHANNEL = new IGasStorageChannel() {
    };

    @Override
    public IStorageChannel getStorageChannel(final Class<?> channel) {
        if (channel == IFluidStorageChannel.class) {
            return FLUID_CHANNEL;
        }
        if (channel == IGasStorageChannel.class) {
            return GAS_CHANNEL;
        }
        return ITEM_CHANNEL;
    }
}
