package appeng.core.localization;

public enum GuiText {
    Config,
    StoredItems,
    StoredFluids,
    inventory;

    public String getLocal() {
        return name();
    }
}
