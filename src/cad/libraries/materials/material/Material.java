package cad.libraries.materials.material;

/**
 * Created by Aaron on 17.11.17.
 */
public class Material {

    private String id;
    private String name;

    private InstanceEffect instanceEffect;

    public Material(String id, String name, InstanceEffect instanceEffect){
        this.id = id;
        this.name = name;
        this.instanceEffect = instanceEffect;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public InstanceEffect getInstanceEffect() {
        return instanceEffect;
    }
}

