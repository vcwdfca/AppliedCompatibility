package appeng.core.features;

public enum AEFeature {
    CORE,
    CHANNELS,
    FLUIX,
    DUSTS,
    DECORATIVE_BLOCKS,
    IN_WORLD_SINGULARITY,
    IN_WORLD_FLUIX,
    IN_WORLD_PURIFICATION,
    PATTERNS;

    public boolean isVisible() {
        return true;
    }

    public String key() {
        return this.name();
    }

    public String category() {
        return "general";
    }

    public boolean isEnabled() {
        return true;
    }

    public String comment() {
        return "";
    }
}
