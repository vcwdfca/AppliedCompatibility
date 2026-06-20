package github.formlessdragon.appcompat.bridge.packagedauto;

/**
 * Exposes the active JEI ghost drag state owned by PackagedAuto's ghost ingredient handler.
 *
 * <p>The handler owns this state for the same reason AE's HEI handler owns it: JEI reports ghost drag lifecycle events
 * to the handler, while the GUI only needs to query the active ingredient for tooltip rendering.</p>
 */
public interface PackagedEncoderGhostState {

    /**
     * Records a new JEI ghost ingredient when JEI starts a drag.
     *
     * @param ingredient current JEI ingredient
     */
    void appcompat$setCurrentGhostIngredient(Object ingredient);

    /**
     * Updates the active mouse button from the current LWJGL mouse state.
     */
    void appcompat$updateCurrentGhostMouseButton();

    /**
     * Clears the current JEI ghost drag state.
     */
    void appcompat$clearCurrentGhostIngredient();

    /**
     * Returns the active JEI ghost ingredient while the recorded drag is active.
     *
     * @return active ingredient, or {@code null} after completion or release
     */
    Object appcompat$getCurrentGhostIngredient();

    /**
     * Returns the recorded mouse button for the current drag.
     *
     * @return mouse button id, or {@code -1} when unknown
     */
    int appcompat$getCurrentGhostMouseButton();
}
