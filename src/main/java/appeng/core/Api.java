package appeng.core;

import appeng.api.AppCompatStorageHelper;
import appeng.api.AppCompatGridHelper;
import appeng.api.AppCompatRegistryContainer;
import appeng.api.IAppEngApi;
import appeng.api.definitions.IDefinitions;
import appeng.api.features.IRegistryContainer;
import appeng.api.networking.IGridHelper;
import appeng.api.storage.IStorageHelper;

public final class Api implements IAppEngApi {

    public static final Api INSTANCE = new Api();

    private final IGridHelper grid = new AppCompatGridHelper();
    private final IRegistryContainer registries = new AppCompatRegistryContainer();
    private final IStorageHelper storage = new AppCompatStorageHelper();
    private final IDefinitions definitions = new ApiDefinitions();

    private Api() {
    }

    @Override
    public IGridHelper grid() {
        return this.grid;
    }

    @Override
    public IRegistryContainer registries() {
        return this.registries;
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
