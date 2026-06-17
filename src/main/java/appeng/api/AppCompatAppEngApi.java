package appeng.api;

import appeng.api.storage.IStorageHelper;

public class AppCompatAppEngApi implements IAppEngApi {

    private final IStorageHelper storageHelper = new AppCompatStorageHelper();

    @Override
    public IStorageHelper storage() {
        return this.storageHelper;
    }
}
