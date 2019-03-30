package cali.animation;

import java.util.HashMap;

public class CALiAnimationManager {

    private static HashMap<String, HashMap<String, CALiAnimation>> animations = new HashMap<>();

    /**
     * example
     *
     * @param modelName -
     * @param animation -
     */
    public static void introduceAnimation(String modelName, CALiAnimation animation) {
        if(hasAnimations(modelName)) {
            animations.get(modelName).put(animation.getName(), animation);
        } else {
            animations.put(modelName, new HashMap<>());
            introduceAnimation(modelName, animation);
        }
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
