package cad.libraries.animations;

import cad.libraries.animations.animation.Animation;

/**
 * Created by Aaron on 17.11.17.
 */
public class AnimationsLibrary {

    private Animation[] animations;

    public AnimationsLibrary(Animation[] animations){
        this.animations = animations;
    }

    public Animation[] getAnimations() {
        return animations;
    }
}