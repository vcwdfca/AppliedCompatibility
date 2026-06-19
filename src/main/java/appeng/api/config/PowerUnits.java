package appeng.api.config;

@SuppressWarnings("ALL")
public enum PowerUnits {
    AE("gui.appliedenergistics2.units.appliedenergstics"),
    EU("gui.appliedenergistics2.units.ic2"),
    RF("gui.appliedenergistics2.units.rf");

    public final String unlocalizedName;
    public final double conversionRatio = 1.0D;

    PowerUnits(final String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public double convertTo(final PowerUnits target, final double amount) {
        return amount * this.conversionRatio / target.conversionRatio;
    }
}
