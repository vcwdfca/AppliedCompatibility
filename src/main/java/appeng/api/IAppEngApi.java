package appeng.api;

import appeng.api.definitions.IDefinitions;
import appeng.api.features.IRegistryContainer;
import appeng.api.networking.IGridHelper;
import appeng.api.storage.IStorageHelper;

public interface IAppEngApi {

    IGridHelper grid();

    IStorageHelper storage();

    IDefinitions definitions();

    IRegistryContainer registries();
}
