package appeng.api.config;

public enum FuzzyMode {
    IGNORE_ALL(-1),
    PERCENT_99(0),
    PERCENT_75(25),
    PERCENT_50(50),
    PERCENT_25(75);

    public final float breakPoint;
    public final float percentage;

    FuzzyMode(final float percentage) {
        this.percentage = percentage;
        this.breakPoint = percentage / 100.0f;
    }

    public int calculateBreakPoint(final int maxDamage) {
        return (int) (this.percentage * maxDamage / 100.0f);
    }

    public ae2.api.config.FuzzyMode toNewMode() {
        return switch (this) {
            case IGNORE_ALL -> ae2.api.config.FuzzyMode.IGNORE_ALL;
            case PERCENT_99 -> ae2.api.config.FuzzyMode.PERCENT_99;
            case PERCENT_75 -> ae2.api.config.FuzzyMode.PERCENT_75;
            case PERCENT_50 -> ae2.api.config.FuzzyMode.PERCENT_50;
            case PERCENT_25 -> ae2.api.config.FuzzyMode.PERCENT_25;
        };
    }
}
