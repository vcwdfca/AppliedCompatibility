package appeng.api.config;

@SuppressWarnings("ALL")
public enum PowerMultiplier {
    ONE,
    CONFIG;

    public final double multiplier = 1.0D;

    public double multiply(final double value) {
        return value * this.multiplier;
    }

    public double divide(final double value) {
        return value / this.multiplier;
    }
}
