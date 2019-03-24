package cad.libraries.effects;

import cad.libraries.Library;
import cad.libraries.effects.effect.Effect;

/**
 * Created by Aaron on 17.11.17.
 */
public class EffectsLibrary implements Library {

    private Effect[] effects;

    public EffectsLibrary(Effect[] effects) {
        this.effects = effects;
    }

    public Effect[] getEffects() {
        return effects;
    }

    @Override
    public boolean isDataMissing() {
        return effects == null;
    }
}
