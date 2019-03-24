package cali;

import cali.object.CALiRawModel;

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

        //CALiObject2 object = new CALiObject2("models/model.dae");
        CALiRawModel object = new CALiRawModel("models/human.dae", true);
        System.out.println(object.toString());
    }
}
