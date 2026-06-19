package appeng.api;

import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHelper;
import appeng.api.networking.IGridNode;
import github.formlessdragon.appcompat.bridge.packagedauto.PackagedAutoManagedNodeHost;

public final class AppCompatGridHelper implements IGridHelper {

    @Override
    public IGridNode createGridNode(final IGridBlock gridBlock) {
        return new PackagedAutoManagedNodeHost(gridBlock).oldNode();
    }
}
