package cad;

import cad.object.CADRawObject;

/**
 * Created by Aaron on 18.11.17.
 */
public class Main {

    public static void main(String[] args){

        //CADObject2[] objects = new CADObject2[100];

        //long start = System.currentTimeMillis();

        //for(int i = 0; i < objects.length; i++)
        //    objects[i] = new CADObject2("models/model.dae");

        //System.out.println();
        //System.out.println("Finished 1000 CADObjects in: " + ((double) (System.currentTimeMillis() - start) / 1000.0) + "s.");

        //CADObject2 object = new CADObject2("models/model.dae");
        CADRawObject object = new CADRawObject("models/human.dae", true);
        System.out.println(object.toString());
    }
}
