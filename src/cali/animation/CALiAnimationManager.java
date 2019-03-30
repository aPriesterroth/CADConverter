package cali.animation;

import java.util.HashMap;

public class CALiAnimationManager {

    private static HashMap<String, HashMap<String, CALiAnimation>> animations = new HashMap<>();

    /**
     * example
     *
     * @param modelName -
     * @param animationName -
     * @param animation -
     */
    public static void addAnimation(String modelName, String animationName, CALiAnimation animation) {

        HashMap<String, CALiAnimation> modelAnimations;

        if(animations.containsKey(modelName)) {
            modelAnimations = animations.get(modelName);
        } else {
            modelAnimations = new HashMap<>();
        }

        modelAnimations.put(animationName, animation);
    }

    /**
     * example
     *
     * @param modelName -
     * @param animations -
     */
    public static void addAnimations(String modelName, HashMap<String, CALiAnimation> animations) {
        CALiAnimationManager.animations.put(modelName, animations);
    }

    /**
     * example
     *
     * @param modelName -
     *
     * @return -
     */
    public static HashMap<String, CALiAnimation> getAnimations(String modelName) {
        return animations.get(modelName);
    }

    /**
     * example
     *
     * @param modelName -
     * @param animationName -
     *
     * @return -
     */
    public static CALiAnimation getAnimation(String modelName, String animationName) {
        return getAnimations(modelName).get(animationName);
    }

    /**
     * example
     *
     * @param modelName -
     *
     * @return -
     */
    public static boolean hasAnimations(String modelName) {
        return animations.containsKey(modelName);
    }

    /**
     * example
     *
     * @param modelName -
     * @param animationName -
     *
     * @return -
     */
    public static boolean hasAnimation(String modelName, String animationName) {
        if(hasAnimations(modelName)) {
            return animations.get(modelName).containsKey(animationName);
        }
        return false;
    }
}
