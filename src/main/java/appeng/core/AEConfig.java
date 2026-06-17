package appeng.core;

import appeng.core.features.AEFeature;

import java.util.Collection;
import java.util.Objects;

public final class AEConfig {

    private static final AEConfig INSTANCE = new AEConfig();

    private AEConfig() {
    }

    public static AEConfig instance() {
        return INSTANCE;
    }

    public boolean isFeatureEnabled(final AEFeature feature) {
        return Objects.requireNonNull(feature, "feature").isEnabled();
    }

    public boolean areFeaturesEnabled(final Collection<AEFeature> features) {
        for (final AEFeature feature : Objects.requireNonNull(features, "features")) {
            if (!this.isFeatureEnabled(feature)) {
                return false;
            }
        }
        return true;
    }
}
