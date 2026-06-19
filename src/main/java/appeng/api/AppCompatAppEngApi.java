package appeng.api;

import appeng.api.definitions.IDefinitions;
import appeng.api.features.IRegistryContainer;
import appeng.api.networking.IGridHelper;
import appeng.api.storage.IStorageHelper;
import appeng.core.ApiDefinitions;

public class AppCompatAppEngApi implements IAppEngApi {

    private final IGridHelper gridHelper = new AppCompatGridHelper();
    private final IStorageHelper storageHelper = new AppCompatStorageHelper();
    private final IDefinitions definitions = new ApiDefinitions();
    private final IRegistryContainer registries = new AppCompatRegistryContainer();

    @Override
    public IGridHelper grid() {
        return this.gridHelper;
    }

    @Override
    public IStorageHelper storage() {
        return this.storageHelper;
    }

    @Override
    public IDefinitions definitions() {
        return this.definitions;
    }

    @Override
    public IRegistryContainer registries() {
        return this.registries;
    }
}
