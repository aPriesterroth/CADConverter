package cali.animation;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of a simple manager of animations for a multitude of models. Within the manager, animations are
 * identified by the name of the corresponding model and the name of the animation itself.
 *
 * This class acts as a solution to the problem with the "Collada-Exporter" of Blender where a model can have no more
 * than one animation at a time. By simply introducing the animation of the same model within a new file to the
 * CALiAnimationManager it is possible for one model to have multiple animations and for multiple models to share
 * common animations.
 *
 * @author Aaron Priesterroth
 *
 */
public class CALiAnimationManager {

    /**
     * Contains a map of animations for every unique model name
     */
    private static HashMap<String, HashMap<String, CALiAnimation>> animations = new HashMap<>();

    /**
     * Introduces an animation to the CALiAnimationManager. The animation is only added to the set of animations
     * if the animation is not yet known to the CALiAnimationManager for the specified modelName.
     * If the specified animation is the first animation for the specified modelName, the map of animations
     * for this modelName is initialized.
     *
     * @param modelName the name of the model the animation is stored for
     * @param animation the animation that is being stored
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
     *
     * @param modelName the name of the model to retrieve the map of animations for
     *
     * @return the map of animations if the modelName is contained within the map of animations and models
     * {@link #animations}, null if not
     */
    private static HashMap<String, CALiAnimation> getAnimations(String modelName) {
        return animations.get(modelName);
    }

    /**
     *
     * @param modelName the name of the model to retrieve the map of animations for
     * @param animationName the name of the animation to retrieve from the map of animations for the model specified by
     *                      the modelName
     *
     * @return the CALiAnimation specified by the animationName from the map of animations for the model specified by
     * the modelName, null if either the map of animations for the specified modelName does not exist or the animation
     * specified by the animationName is not present in the map of animations.
     */
    public static CALiAnimation getAnimation(String modelName, String animationName) {
        return getAnimations(modelName).get(animationName);
    }

    /**
     * Indicates whether or not the CALiAnimationManager has animations for the model specified by the modelName.
     *
     * @param modelName the name of the model to check for animations within the {@link #animations} map
     *
     * @return true, if the map contains a map for the modelName, else false
     */
    private static boolean hasAnimations(String modelName) {
        return animations.containsKey(modelName);
    }

    /**
     * Indicates whether or not the CALiAnimationManager the animation specified by animationName for the model
     * specified by the modelName.
     *
     * @param modelName the name of the model to check for the animation within the {@link #animations} map
     * @param animationName the name of the animation to check
     *
     * @return true, if the map contains a map for the modelName and the map contains an animation with the specified
     * name, else false
     */
    public static boolean hasAnimation(String modelName, String animationName) {
        if(hasAnimations(modelName)) {
            return animations.get(modelName).containsKey(animationName);
        }
        return false;
    }

    /**
     *
     * @return the list of animations for every model contained in the {@link #animations} map in a human-readable
     * format for debugging purposes
     */
    public static String asString() {
        StringBuilder builder = new StringBuilder();

        for(Map.Entry<String, HashMap<String, CALiAnimation>> entry : CALiAnimationManager.animations.entrySet()) {
            String key = entry.getKey();
            HashMap<String, CALiAnimation> animations = entry.getValue();

            builder.append("Model name: ").append(key).append("\n");

            for(Map.Entry<String, CALiAnimation> entry2 : animations.entrySet()) {
                builder.append(entry2.getValue().toString());
            }
        }
        return builder.toString();
    }
}
