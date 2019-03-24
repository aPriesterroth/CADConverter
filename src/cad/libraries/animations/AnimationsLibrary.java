package cad.libraries.animations;

import cad.libraries.Library;
import cad.libraries.animations.animation.Animation;

/**
 * Created by Aaron on 17.11.17.
 */
public class AnimationsLibrary implements Library {

    private Animation[] animations;

    public AnimationsLibrary(Animation[] animations){
        this.animations = animations;
    }

    public Animation[] getAnimations() {
        return animations;
    }

    @Override
    public boolean isDataMissing() {
        return animations ==  null;
    }
}