package appeng.api.storage;

public interface IStorageHelper {

    <T extends appeng.api.storage.data.IAEStack<T>> IStorageChannel<T> getStorageChannel(Class<?> channel);
}
