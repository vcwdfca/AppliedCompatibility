package appeng.core;

import appeng.api.AppCompatStorageHelper;
import appeng.api.IAppEngApi;
import appeng.api.definitions.IDefinitions;
import appeng.api.storage.IStorageHelper;

public final class Api implements IAppEngApi {

    public static final Api INSTANCE = new Api();

    private final IStorageHelper storage = new AppCompatStorageHelper();
    private final IDefinitions definitions = new ApiDefinitions();

    private Api() {
    }

    @Override
    public IStorageHelper storage() {
        return this.storage;
    }

    @Override
    public IDefinitions definitions() {
        return this.definitions;
    }
}
