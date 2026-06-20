package github.formlessdragon.appcompat.bridge.packagedauto;

import ae2.api.networking.IGridNode;
import ae2.api.networking.IGridNodeListener;

public final class PackagedAutoNodeListener implements IGridNodeListener<PackagedAutoNodeOwner> {

    public static final PackagedAutoNodeListener INSTANCE = new PackagedAutoNodeListener();

    private PackagedAutoNodeListener() {
    }

    @Override
    public void onSaveChanges(final PackagedAutoNodeOwner nodeOwner, final IGridNode node) {
        nodeOwner.appcompat$nodeSaveChanges();
    }

    @Override
    public void onStateChanged(final PackagedAutoNodeOwner nodeOwner, final IGridNode node, final State state) {
        nodeOwner.appcompat$nodeChanged();
    }

    @Override
    public void onGridChanged(final PackagedAutoNodeOwner nodeOwner, final IGridNode node) {
        nodeOwner.appcompat$nodeChanged();
    }
}
