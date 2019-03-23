package cad.object;

import cad.exceptions.ParsingException;
import cad.libraries.animations.AnimationsLibrary;
import cad.libraries.animations.animation.Animation;
import cad.libraries.controllers.ControllersLibrary;
import cad.libraries.geometries.GeometriesLibrary;
import cad.libraries.geometries.geometry.mesh.Polylist;
import cad.libraries.images.ImagesLibrary;
import cad.libraries.visualScenes.VisualScenesLibrary;
import cad.libraries.visualScenes.visualScene.node.Node;

import cad.math.Matrix4f;
import cad.math.Quaternion;
import cad.math.Vector3f;
import cad.object.animation.CADAnimation;
import cad.object.animation.CADJointTransform;
import cad.object.animation.CADKeyFrame;
import cad.parser.CADParser;
import cad.commons.Input;
import cad.commons.source.BasicArray;
import cad.commons.source.FloatArray;
import cad.commons.source.NameArray;
import cad.commons.source.Source;

import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of an CADObject consisting of basic geometry data (vertices, normals, textureCoords and all
 * corresponding indices) and animation data (weights, joints and corresponding indices) loaded from a ".dae" (Collada)
 * file. The ".dae" file must be exported using Polylists (Triangles support is deprecated).
 *
 * Created by Aaron Priesterroth on 18.11.17.
 */
public class CADObject {

    // --- For the VAO ---
    // indexBuffer -> sort all vertices, normals, textureCoords into one index list.
    // attribute0 -> vertices
    // attribute1 -> textureCoords
    // attribute2 -> normals
    // (INT)attribute3 -> jointIDs
    // attribute4 vertexWeights

    private int[] vertexIndices;
    private int[] textureCoordIndices;
    private int[] normalIndices;
    private int[] weightIndices;

    private int[] jointIds;

    private float[] vertices;
    private float[] textureCoords;
    private float[] normals;
    private float[] weights;

    private String textureFileName;

    private CADJoint rootJoint;
    private int jointCount;

    private ArrayList<CADAnimation> animations;

    /**
     * example
     *
     * @param path -
     */
    public CADObject(String path){

        try {
            CADParser parser = new CADParser(path);

            parseGeometryData(parser.parseGeometriesLibrary_polylist());

            parseTextureFileName(parser.parseImagesLibrary());

            parseVisualSceneData(parser.parseVisualScenesLibrary());

            parseControllersData(parser.parseControllersLibrary());

            parseAnimationsData(parser.parseAnimationsLibrary());

            //rootJoint.calculateInverseBindTransform(new Matrix4f());

        } catch (ParsingException e){
            e.printStackTrace();
        }

        System.out.println("Finished loading the CADObject (" + this + ") from path: " + path + "");
    }

    public ArrayList<CADAnimation> getAnimations() {
        return animations;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getNormals() {
        return normals;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public int[] getVertexIndices() {
        return vertexIndices;
    }

    public int[] getNormalIndices() {
        return normalIndices;
    }

    public int[] getTextureCoordIndices() {
        return textureCoordIndices;
    }

    public float[] getWeights() {
        return weights;
    }

    public int[] getJointIds() {
        return jointIds;
    }

    public int[] getWeightIndices() {
        return weightIndices;
    }

    public String getTextureFileName() {
        return textureFileName;
    }

    public CADJoint getRootJoint() {
        return rootJoint;
    }

    public int getJointCount() {
        return jointCount;
    }

    /**
     * Maps a specific array of float values to a specific array of integer indices.
     *
     * @param source - the specific array of floats that are mapped to the array of integer indices
     * @param stride - the amount of values in relation (e.g., vertices => 3 values, textureCoords => 2 values)
     * @param indices - the specific array of integer indices array of floats is being mapped to
     *
     * @return - the float array of values mapped to the specific int[] of indices
     */
    public static float[] mapToIndices(float[] source, int stride, int[] indices) {

        int offset = 0;
        float[] mSource = new float[indices.length * stride];

        for (int index : indices) {

            int loopOff = 0;

            for (int j = 0; j < stride; j++) {
                mSource[offset++] = source[index * stride + loopOff++];
            }
        }
        return mSource;
    }

    /**
     * Parses vertices, normals, textureCoords and all their corresponding indices from a specific
     * GeometriesLibrary to the CADObject instance.
     * Also converts the textureCoords UV's from Blenders top left to OpenGLs lower left origin.
     *
     * @param library - the specific GeometriesLibrary
     */
    private void parseGeometryData(GeometriesLibrary library) throws ParsingException {

        // Parsing vertex, normals, and textureCoord indices
        List<Integer> vIndices = new ArrayList<>();
        List<Integer> nIndices = new ArrayList<>();
        List<Integer> tIndices = new ArrayList<>();

        for(Polylist polylist : library.getGeometry().getMesh().getPolylists()){

            int vOff = -1, nOff = -1, tOff = -1;

            for(Input input : polylist.getInputs()){
                switch (input.getSemantic()) {
                    case "VERTEX":
                        vOff = input.getOffset();
                        break;
                    case "NORMAL":
                        nOff = input.getOffset();
                        break;
                    case "TEXCOORD":
                        tOff = input.getOffset();
                        break;
                }
            }

            if(vOff == -1 || nOff == -1 || tOff == -1)
                throw new ParsingException("Unable to retrieve vertices, normals and textureCoords offset!");

            int paragraphOffset = 0, inputCount = polylist.getInputs().length;

            for(int i = 0; i < polylist.getvCount().length; i++)
                for(int j = 0; j < polylist.getvCount()[i]; j++){
                    vIndices.add(polylist.getParagraph()[vOff + paragraphOffset]);
                    if(inputCount == 1){
                        paragraphOffset += inputCount;
                        continue;
                    }
                    nIndices.add(polylist.getParagraph()[nOff + paragraphOffset]);
                    if(inputCount == 2){
                        paragraphOffset += inputCount;
                        continue;
                    }
                    tIndices.add(polylist.getParagraph()[tOff + paragraphOffset]);
                    paragraphOffset += inputCount;
                }
        }

        vertexIndices = vIndices.stream().mapToInt(i -> i).toArray();
        normalIndices = nIndices.stream().mapToInt(i -> i).toArray();
        textureCoordIndices = tIndices.stream().mapToInt(i -> i).toArray();

        // Parsing vertices, normals and textureCoords
        for(Source source : library.getGeometry().getMesh().getSources())
            if(source.getArray().getType() == BasicArray.BasicArrayType.FLOAT_ARRAY){
                if(source.getId().contains("positions"))
                    vertices = ((FloatArray) source.getArray()).getFloats();

                if(source.getId().contains("normals"))
                    normals = ((FloatArray) source.getArray()).getFloats();

                if(source.getId().contains("map"))
                    textureCoords = ((FloatArray) source.getArray()).getFloats();
            }

        // Switching UV's from Blender-Format (0/0 top left) to OpenGL-Format (0/0 lower left)
        for(int i = 1; i < textureCoords.length; i += 2){
            textureCoords[i] = 1 - textureCoords[i];
        }
    }

    /**
     * Parses the texture file name from a specific ImagesLibrary to the CADObject instance.
     *
     * @param library - the specific ImagesLibrary
     */
    private void parseTextureFileName(ImagesLibrary library) throws ParsingException {

        int extension = 4;

        String fileName = library.getImage().getInitFrom();

        if(fileName != null && fileName.length() > extension)
            textureFileName = fileName.substring(0, library.getImage().getInitFrom().length() - extension);

        else
            throw new ParsingException("Texture file name is null or in an illegal format!");
    }

    /**
     * Parses the CADJoint root and creates the joints construct from a specific VisualScenesLibrary
     * to the CADObject instance.
     *
     * @param library - the specific VisualScenesLibrary
     */
    private void parseVisualSceneData(VisualScenesLibrary library) throws ParsingException {

        String rootName = "";

        for(Node node : library.getVisualScene().getNodes())
            if(node.getInstanceController() != null)
                if(node.getInstanceController().getSkeletons() != null){

                    if(node.getInstanceController().getSkeletons().length == 1)
                        rootName = node.getInstanceController().getSkeletons()[0];

                    else
                        System.out.println("Not all bones have been added to the skin. Multiple root joints detected!");
                }

        if(!rootName.equals("")){

            for(Node node : library.getVisualScene().getNodes())
                for(Node node2 : node.getChildren())
                    if(node2.getName().equals(rootName)){

                        CADJoint root;

                        if(node2.getType().equals("JOINT")){
                            root = new CADJoint(node2.getId(), node2.getName(), node2.getSid(), new Matrix4f(node2.getMatrix()),
                                    null, null);
                            root.setChildren(parseJointChildren(root, node2));

                        } else {
                            root = new CADJoint(node2.getId(), node2.getName(), new Matrix4f(node2.getMatrix()),
                                    null, null);
                            root.setChildren(parseJointChildren(root, node2));
                        }

                        rootJoint = root;
                        jointCount += 1;
                    }
        } else
            throw new ParsingException("Unable to find the roots name!");
    }

    /**
     * Recursively creates an array of all children of a specific node and sets the parent to a specific joint.
     *
     * @param parent - the specific joint specified as parent
     * @param node - the specific node containing the children
     *
     * @return - a CADJoint[] of children, null if the node has no children
     */
    private CADJoint[] parseJointChildren(CADJoint parent, Node node) {

        if(node.getChildren().length == 0)
            return null;

        CADJoint[] children = new CADJoint[node.getChildren().length];

        for(int i = 0; i < node.getChildren().length; i++){

            if(node.getChildren()[i].getType().equals("JOINT")){

                children[i] = new CADJoint(node.getChildren()[i].getId(), node.getChildren()[i].getName(), node.getChildren()[i].getSid(),
                        new Matrix4f(node.getChildren()[i].getMatrix()), parent, null);
            } else {

                children[i] = new CADJoint(node.getId(), node.getName(), new Matrix4f(node.getMatrix()),
                        parent, null);
            }

            jointCount += 1;

            children[i].setChildren(parseJointChildren(children[i], node.getChildren()[i]));
        }
        return children;
    }

    /**
     * Parses weights, weight indices, inverseBindTransforms for all joints and joint ids from a specific
     * Controllers library to the CADObject instance.
     *
     * @param library - the specific ControllersLibrary
     */
    private void parseControllersData(ControllersLibrary library) throws ParsingException {

        String weightsSourceStr = "";

        for(Input input : library.getController().getSkin().getVertexWeights().getInputs())
            if(input.getSemantic().equals("WEIGHT"))
                weightsSourceStr = input.getSource();

        if(!weightsSourceStr.equals("")){

            for(Source source : library.getController().getSkin().getSources())
                if(source.getId().equals(weightsSourceStr))
                    weights = ((FloatArray) source.getArray()).getFloats();

        } else {
            throw new ParsingException("Unable to find the weights source string!");
        }

        String jointsSourceStr = "";

        for(Input input : library.getController().getSkin().getVertexWeights().getInputs())
            if(input.getSemantic().equals("JOINT"))
                jointsSourceStr = input.getSource();

        String invBindSourceStr = "";

        for(Input input : library.getController().getSkin().getJoints().getInputs())
            if(input.getSemantic().equals("INV_BIND_MATRIX"))
                invBindSourceStr = input.getSource();

        Source invBindSource = null;

        for(Source source : library.getController().getSkin().getSources()){
            if(source.getId().equals(invBindSourceStr)){
                invBindSource = source;
            }
        }

        if(invBindSource == null){
            throw new ParsingException("Unable to locate the invBindTransform source!");
        }

        for(Source source : library.getController().getSkin().getSources()){
            if(source.getId().equals(jointsSourceStr)){

                int stride = 16;

                String[] names = ((NameArray) source.getArray()).getNames();
                float[] floats = ((FloatArray) invBindSource.getArray()).getFloats();

                for(int i = 0; i < names.length; i++){

                    float[] components = new float[stride];
                    System.arraycopy(floats, i * stride, components, 0, stride);

                    CADJoint curJoint = getJointById(names[i]);
                    curJoint.setInverseBindTransform(new Matrix4f(components));
                    curJoint.setIndex(i);
                }
            }

        }

        ArrayList<Integer> jIndices = new ArrayList<>();
        ArrayList<Integer> wIndices = new ArrayList<>();

        int jOff = 0, wOff = 0;

        for(Input input : library.getController().getSkin().getVertexWeights().getInputs()){
            if(input.getSemantic().equals("WEIGHT"))
                wOff = input.getOffset();

            else if(input.getSemantic().equals("JOINT"))
                jOff = input.getOffset();
        }

        int inputCount = library.getController().getSkin().getVertexWeights().getInputs().length;

        int[] vCount = library.getController().getSkin().getVertexWeights().getVCount();
        int[] v = library.getController().getSkin().getVertexWeights().getV();

        int vOff = 0;

        for (int aVCount : vCount) {
            for (int j = 0; j < aVCount; j++) {
                jIndices.add(v[jOff + vOff]);
                wIndices.add(v[wOff + vOff]);
                vOff += inputCount;
            }
        }

        jointIds = jIndices.stream().mapToInt(i -> i).toArray();
        weightIndices = wIndices.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Returns a specific joint from the CADJoint constructed from {#link parseVisualScenes()} a specific id.
     *
     * @param id - the specific id
     *
     * @return the joint associated with the id, null if no joint with the specific id is found
     */
    private CADJoint getJointById(String id){
        return getJointById(rootJoint, id);
    }

    /**
     * Returns a specific joint from specific parent joint and all its children from a specific id.
     *
     * @param parent - the specific parent joint
     * @param id - the specific id
     *
     * @return the joint associated with the id, null if no joint with the specific id is found
     */
    private CADJoint getJointById(CADJoint parent, String id){
        if(parent.getId().equals(id))
            return parent;

        CADJoint temp;

        if(parent.getChildren() != null){
            for(CADJoint child : parent.getChildren()){
                temp = getJointById(child, id);

                if(temp != null)
                    return temp;
            }
        }
        return null;
    }

    /**
     * example
     *
     * @param library -
     *
     * @throws ParsingException -
     */
    private void parseAnimationsData(AnimationsLibrary library) throws ParsingException {

        ArrayList<CADAnimation> animations = new ArrayList<>();

        CADAnimation animation;

        float length = -1.0f;
        CADKeyFrame[] keyFrames;

        float[] timeStamps = null;

        for(Animation a : library.getAnimations())
            if(timeStamps == null) {
                for(Source source : a.getSources())
                    if(source.getId().contains("input"))
                        timeStamps = ((FloatArray) source.getArray()).getFloats();
            }

        if(timeStamps != null){

            length = timeStamps[timeStamps.length - 1];

            keyFrames = new CADKeyFrame[timeStamps.length];

            for(int i = 0; i < timeStamps.length; i++){

                int stride = 16;

                keyFrames[i] = new CADKeyFrame(timeStamps[i]);

                for(Animation a : library.getAnimations()) {

                    String jointId = a.getChannel().getTarget().split("/")[0];

                    for(Source s : a.getSources())
                        if(s.getId().contains("output")) {

                            float[] components = new float[stride];

                            System.arraycopy(((FloatArray) s.getArray()).getFloats(), i * stride, components,
                                    0, stride);

                            Matrix4f matrix = new Matrix4f(components);

                            Vector3f position = new Vector3f(matrix.m30, matrix.m31, matrix.m32);
                            Quaternion rotation = new Quaternion(matrix);

                            keyFrames[i].getPoses().put(jointId, new CADJointTransform(position, rotation));
                        }
                }
            }

            /* THE SAME AS BELOW, CONFIRM THIS POSITION
            */
            animations.add(new CADAnimation(length, keyFrames));

            this.animations = animations;

        } else {
            throw new ParsingException("Unable to locate the timestamps!");
        }

        /* THE SAME AS ABOVE, CONFIRM NEW POSITION FOR THIS
        animation = new CADAnimation(length, keyFrames);
        animations.add(animation);

        this.animations = animations;
        */
    }

    /**
     * example
     *
     * @return -
     */
    public Matrix4f[] getJointTransforms() {
        Matrix4f[] jointMatrices = new Matrix4f[jointCount];
        addJointsToArray(rootJoint, jointMatrices);
        return jointMatrices;
    }

    /**
     * example
     *
     * @param root -
     * @param jointMatrices -
     */
    private void addJointsToArray(CADJoint root, Matrix4f[] jointMatrices) {
        jointMatrices[root.getIndex()] = root.getAnimatedTransform();

        for(CADJoint child : root.getChildren()) {
            addJointsToArray(child, jointMatrices);
        }
    }
}