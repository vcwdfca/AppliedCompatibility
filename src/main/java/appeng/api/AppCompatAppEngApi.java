package appeng.api;

import appeng.api.definitions.IDefinitions;
import appeng.api.storage.IStorageHelper;
import appeng.core.ApiDefinitions;

public class AppCompatAppEngApi implements IAppEngApi {

    private final IStorageHelper storageHelper = new AppCompatStorageHelper();
    private final IDefinitions definitions = new ApiDefinitions();

    @Override
    public IStorageHelper storage() {
        return this.storageHelper;
    }

    @Override
    public IDefinitions definitions() {
        return this.definitions;
    }
}
