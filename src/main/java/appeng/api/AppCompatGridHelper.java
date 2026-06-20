package appeng.api;

import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHelper;
import appeng.api.networking.IGridNode;
import github.formlessdragon.appcompat.bridge.packagedauto.PackagedAutoLegacyGridNode;
import github.formlessdragon.appcompat.bridge.packagedauto.PackagedAutoNodeAccess;

public final class AppCompatGridHelper implements IGridHelper {

    @Override
    public IGridNode createGridNode(final IGridBlock gridBlock) {
        if (gridBlock.getMachine() instanceof PackagedAutoNodeAccess access) {
            final IGridNode node = new PackagedAutoLegacyGridNode(gridBlock, access);
            access.appcompat$setLegacyNode(node);
            return node;
        }
        throw new IllegalStateException("Legacy AE grid node creation is not registered for " + gridBlock.getMachine().getClass().getName());
    }
}
