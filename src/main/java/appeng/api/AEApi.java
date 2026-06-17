package appeng.api;

public final class AEApi {

    private static final IAppEngApi INSTANCE = new AppCompatAppEngApi();

    private AEApi() {
    }

    public static IAppEngApi instance() {
        return INSTANCE;
    }
}
