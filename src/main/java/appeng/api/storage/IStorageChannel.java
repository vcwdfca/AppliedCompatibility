package appeng.api.storage;

import appeng.api.storage.data.IAEStack;

public interface IStorageChannel<T extends IAEStack<T>> {

    T createStack(Object input);
}
