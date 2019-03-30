package cali;

import cali.animation.CALiAnimationManager;
import cali.model.CALiModel;

/**
 * Created by Aaron on 18.11.17.
 */
public class Main {

    public static void main(String[] args){

        //CALiObject2[] objects = new CALiObject2[100];

        //long start = System.currentTimeMillis();

        //for(int i = 0; i < objects.length; i++)
        //    objects[i] = new CALiObject2("models/model.dae");

        //System.out.println();
        //System.out.println("Finished 1000 CADObjects in: " + ((double) (System.currentTimeMillis() - start) / 1000.0) + "s.");

        String filepath = "models/model.dae";

        String modelName = "model";
        String animationName1 = "animation1";
        String animationName2 = "animation2";
        String animationName3 = "animation3";

        boolean correctBlenderCoordinates = true;

        CALiModel object = new CALiModel(filepath, modelName, animationName1, correctBlenderCoordinates);
        object.introduceAnimation(filepath, animationName2);
        object.introduceAnimation(filepath, animationName3);

        CALiAnimationManager caLiAnimationManager = new CALiAnimationManager();

        System.out.println(object.toString());
    }
}
