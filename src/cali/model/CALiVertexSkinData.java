package cali.model;

import java.util.ArrayList;

public class CALiVertexSkinData {

    private final int weightsCap;

    private final ArrayList<Float> weights;
    private final ArrayList<Integer> jointIds;

    public CALiVertexSkinData(int weightsCap) {
        this.weightsCap = weightsCap;

        jointIds = new ArrayList<>();
        weights = new ArrayList<>();
    }

    public ArrayList<Float> getWeights() {
        return weights;
    }

    public ArrayList<Integer> getJointIds() {
        return jointIds;
    }

    public void addWeight(float weight, int jointId) {
        for(int i=0;i<weights.size();i++){
            if(weight > weights.get(i)){
                weights.add(i, weight);
                jointIds.add(i, jointId);
                return;
            }
        }
        weights.add(weight);
        jointIds.add(jointId);
    }

    public CALiVertexSkinData pack() {
        if(weights.size() > weightsCap) {
            float[] highestWeights = new float[weightsCap];
            float total = saveTopWeights(highestWeights);
            refillWeightList(highestWeights, total);
            removeExcessJointIds();
        } else if(weights.size() < weightsCap){
            fill();
        }
        return this;
    }

    private void removeExcessJointIds() {
        while(jointIds.size() > weightsCap){
            jointIds.remove(jointIds.size()-1);
        }
    }

    private void refillWeightList(float[] highestWeights, float total) {
        weights.clear();
        for(int i = 0; i < highestWeights.length; i++) {
            weights.add(Math.min(highestWeights[i]/total, 1));
        }
    }

    private float saveTopWeights(float[] highestWeights) {
        float total = 0;
        for(int i = 0; i < highestWeights.length; i++) {
            float weight = weights.get(i);
            highestWeights[i] = weight;
            total += weight;
        }
        return  total;
    }

    private void fill() {
        for(int i = weights.size(); i < weightsCap; i++) {
            weights.add(0.0f);
            jointIds.add(0);
        }
    }
}
