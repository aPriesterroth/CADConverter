package cad;

import cad.object.CADObject;

/**
 * Created by Aaron on 18.11.17.
 */
public class Main {

    public static void main(String[] args){

        //CADObject[] objects = new CADObject[100];

        //long start = System.currentTimeMillis();

        //for(int i = 0; i < objects.length; i++)
        //    objects[i] = new CADObject("models/model.dae");

        //System.out.println();
        //System.out.println("Finished 1000 CADObjects in: " + ((double) (System.currentTimeMillis() - start) / 1000.0) + "s.");

        CADObject object = new CADObject("models/model.dae");
        System.out.println(object.toString());
    }
}
