package appeng.api;

import appeng.api.definitions.IDefinitions;
import appeng.api.storage.IStorageHelper;

public interface IAppEngApi {

    IStorageHelper storage();

    IDefinitions definitions();
}
