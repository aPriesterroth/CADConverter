package cali.model;

import cali.animator.CALiAnimator;
import cali.commons.Input;
import cali.commons.source.FloatArray;
import cali.commons.source.NameArray;
import cali.commons.source.Source;
import cali.interfaces.Identifiable;
import cali.libraries.animations.AnimationsLibrary;
import cali.libraries.animations.animation.Animation;
import cali.libraries.controllers.ControllersLibrary;
import cali.libraries.geometries.GeometriesLibrary;
import cali.libraries.geometries.geometry.mesh.Mesh;
import cali.libraries.images.ImagesLibrary;
import cali.libraries.visualScenes.VisualScenesLibrary;
import cali.libraries.visualScenes.visualScene.node.Node;
import cali.maths.*;
import cali.animation.*;
import cali.object.CALiObject;
import cali.parser.CALiParser;
import cali.exceptions.CALiMissingDataException;

import java.util.*;

public class CALiRawModel extends CALiObject {

    /**
     * Idea to take everything from CALiObject2 and move it to here and make CALiObject2 the clean version of a model
     * where later all you have to do is extend your class CALiObject2, make a constructor with filepath and pass it
     * on and now you have all the methods required to nicely deal with the animated model, as in "all the meaty logic
     * is outside and you can just use the getters to supply your needs".
     *
     * CALiObject2 is a "trimmed" version of the CALiRawModel.
     *      -> CALiRawModel contains everything (libraries, data, joints, filepath, etc.)
     *      -> CALiObject2 contains only required information (data, joints, etc.)
     *
     * UPDATE:
     * CALiModel extends CALiRawModel but has a polish() option, where all memory intensive things are deleted.
     * Implemented in the constructor, based on another CALiModel.
     *
     * UPDATE 2:
     * For the animations, one can create a RawModel (or something else, not quite sure yet) and use the method
     * extractAnimations() to retrieve the list of animations for this model and keep it at one place, instead of for
     * every model itself. But maybe its not even problematic to keep it for every model (look into space complexity).
     * How ever, from this point on can call duplicate() on the raw model to derive the desired model from the blueprint,
     * while specifying different textures maybe? Also add functionality for derive() method which derives a model
     * instead of a raw model.
     *
     *
     * If you can only put 1 animation into a model, how about extracting the logic to parse an animation to have
     * access to the method were only an animation is loaded from the model file and just call the logic in the model
     * to extract the animation itself.
     */

    private static final CALiMatrix4f BLENDER_CORRECTION = new CALiMatrix4f().rotate((float) Math.toRadians(-90),
            new CALiVector3f(1, 0, 0));

    private static final int WEIGHTS_PER_VERTEX_CAP = 3;

    private static final String SEMANTIC_JOINT = "JOINT";
    private static final String SEMANTIC_WEIGHT = "WEIGHT";
    private static final String SEMANTIC_INPUT = "INPUT";
    private static final String SEMANTIC_OUTPUT = "OUTPUT";

    protected String modelName;
    protected String animationName;
    protected String textureFileName;

    protected float[] vertices;
    protected float[] textures;
    protected float[] normals;
    protected float[] weights;

    protected int[] indices;
    protected int[] jointIds;

    protected int jointCount;

    protected CALiJoint rootJoint;

    protected ArrayList<String> jointOrder;

    protected ArrayList<CALiVertexSkinData> skinningData;

    protected ArrayList<CALiVertex> verticesList;

    protected ArrayList<CALiVector2f> texturesList;
    protected ArrayList<CALiVector3f> normalsList;

    protected ArrayList<Integer> indicesList;

    protected CALiAnimator animator;

    /**
     * Creates a minimal version of a CALiRawModel based on a CALiRawModel instance by only copying relevant data
     * from the instance.
     *
     * @param rawModel - the specific CALiRawModel instance to derive a minimal version from
     */
    protected CALiRawModel(CALiRawModel rawModel) {
        super(null, rawModel.correctBlenderCoordinates);

        this.modelName = rawModel.modelName;
        this.animationName = rawModel.animationName;
        this.textureFileName = rawModel.textureFileName;

        this.vertices = rawModel.vertices;
        this.textures = rawModel.textures;
        this.normals = rawModel.normals;
        this.weights = rawModel.weights;

        this.indices = rawModel.indices;
        this.jointIds = rawModel.jointIds;

        this.jointCount = rawModel.jointCount;

        this.rootJoint = rawModel.rootJoint;

        this.jointOrder = null;

        this.skinningData = null;

        this.verticesList = null;

        this.texturesList = null;
        this.normalsList = null;

        this.indicesList = null;

        this.animator = rawModel.animator;
    }

    /**
     * Creates a CALiRawModel based on a filepath to a ".dae" file, initializes libraries and the parsing of data from
     * them. The boolean flag "correctBlenderCoordinates"indicates whether or not all vectors and matrices should be
     * manipulated to compensate for Blenders coordinate orientation (Blender - OpenGL).
     *
     * @param filepath - the specific filepath
     * @param correctBlenderCoordinates - the boolean flag indicating
     */
    public CALiRawModel(String filepath, String modelName, String animationName, boolean correctBlenderCoordinates) {
        super(filepath, correctBlenderCoordinates);

        this.modelName = modelName;
        this.animationName = animationName;

        initializeLibrariesParsing();

        this.animator = new CALiAnimator(rootJoint);
    }

    /**
     * Initializes the parsing of data from the libraries.
     */
    private void initializeLibrariesParsing() {
        parseDataFromControllersLibrary(controllersLibrary);

        try {
            parseDataFromVisualScenesLibrary(visualScenesLibrary);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rootJoint.calculateInverseBindTransform(new CALiMatrix4f());

        parseDataFromGeometriesLibrary(geometriesLibrary);

        parseDataFromImagesLibrary(imagesLibrary);

        if(!CALiAnimationManager.hasAnimation(modelName, animationName)) {
            parseDataFromAnimationsLibrary(animationsLibrary, animationName);
        }
    }

    /**
     * Adds a CALiAnimation object to the set of objects for the model name of this specific instance.
     *
     * @param path - the relative file to the ".dae" file
     * @param animationName - the name of the animation to parse
     */
    public void introduceAnimation(String path, String animationName) {
        if(!CALiAnimationManager.hasAnimation(modelName, animationName)) {
            parseDataFromAnimationsLibrary(new CALiParser(path).parseAnimationsLibrary(), animationName);
        }
    }

    /**
     * Parsing the a CALiAnimation from a specific AnimationsLibrary and introducing it to the
     * {@link CALiAnimationManager}.
     *
     * @param library - the specific library to parse the animation from
     * @param animationName - the name of the animation to parse
     */
    private void parseDataFromAnimationsLibrary(AnimationsLibrary library, String animationName) {

        try {
            Animation rootJointAnimation = Arrays.stream(library.getAnimations()).filter(
                    x -> x.getId().contains(rootJoint.getId())).findFirst().orElse(null);

            String inputSourceStr = ((Input) getDataStructureBySemantic(rootJointAnimation.getSampler().getInputs(),
                    SEMANTIC_INPUT)).getSource();

            Source inputSource = ((Source) getDataStructureById(rootJointAnimation.getSources(), inputSourceStr));

            float[] times = ((FloatArray) inputSource.getArray()).getFloats();
            float duration = times[times.length - 1];

            CALiKeyFrame[] keyFrames = new CALiKeyFrame[times.length];

            for(int i = 0; i < keyFrames.length; i++) {
                keyFrames[i] = new CALiKeyFrame(times[i]);
            }

            for(Animation animation : library.getAnimations()) {
                // Only get the first part of "Torso/transform"
                String jointId = animation.getChannel().getTarget().split("/")[0];

                String outputSourceStr = ((Input) getDataStructureBySemantic(animation.getSampler().getInputs(),
                        SEMANTIC_OUTPUT)).getSource();

                Source outputSource = ((Source) getDataStructureById(animation.getSources(), outputSourceStr));

                for(int i = 0; i < keyFrames.length; i++) {

                    int subFrom = CALiMatrix4f.getMatrixSize()*i;
                    int subTo = subFrom + CALiMatrix4f.getMatrixSize();

                    float[] sub = Arrays.copyOfRange(((FloatArray) outputSource.getArray()).getFloats(),
                            subFrom, subTo);

                    CALiMatrix4f transform = new CALiMatrix4f(sub).transpose();

                    if(correctBlenderCoordinates && jointId.equals(rootJoint.getId())) {
                        transform = BLENDER_CORRECTION.multiply(transform);
                    }
                    CALiVector3f position = new CALiVector3f(transform.m30, transform.m31, transform.m32);
                    CALiQuaternion rotation = new CALiQuaternion(transform);

                    keyFrames[i].getPoses().put(jointId, new CALiJointTransform(position, rotation));
                }
            }

            CALiAnimationManager.introduceAnimation(modelName, new CALiAnimation(animationName, duration, keyFrames));

        } catch (CALiMissingDataException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a Identifiable object (such as an Input, a Source, etc.) from an array of Identifiable
     * objects based on a specific id.
     *
     * @param dataStructures - the array of Identifiable objects to retrieve from
     * @param id - the id of the object to retrieve
     *
     * @return - the Identifiable object with the specific id
     *
     * @throws CALiMissingDataException - the array of Identifiable objects cannot be null and the
     * Identifiable object with the specific id MUST be present in the array of objects
     */
    private Identifiable getDataStructureById(Identifiable[] dataStructures, String id) throws CALiMissingDataException {
        if(dataStructures == null) {
            throw new IllegalArgumentException("Data structure array cannot be null");
        }
        return Arrays.stream(dataStructures).filter(x -> x.getId().equals(id)).findFirst().orElseThrow(
                () -> new CALiMissingDataException(dataStructures.getClass().getCanonicalName(), "id", id));
    }

    /**
     * Retrives a Identifiable object (such as an Input, a Source, etc.) from an array of Identifiable
     * objects based on a specific semantic.
     *
     * @param dataStructures - the array of Identifiable objects to retrieve from
     * @param semantic - the id of the object to retrieve
     *
     * @return - the Identifiable object with the specific id
     *
     * @throws CALiMissingDataException - the array of Identifiable objects cannot be null and the
     * Identifiable object with the specific semantic MUST be present in the array of objects
     */
    private Identifiable getDataStructureBySemantic(Identifiable[] dataStructures, String semantic) throws CALiMissingDataException {
        if(dataStructures == null) {
            throw new IllegalArgumentException("Data structure array cannot be null");
        }
        return Arrays.stream(dataStructures).filter(x -> x.getSemantic().equals(semantic)).findFirst().orElseThrow(
                () -> new CALiMissingDataException(dataStructures.getClass().getName(), "semantic", semantic));
    }

    /**
     * Parsing the textureFileName from a specific ImagesLibrary.
     *
     * @param library - the specific ImagesLibrary to parse data from
     */
    private void parseDataFromImagesLibrary(ImagesLibrary library) {
        textureFileName = library.getInitFrom();
    }

    /**
     * Parsing the joint order and "CALiVertexSkinData" skinning data from a specific ControllersLibrary.
     *
     * @param library - the specific ControllersLibrary to parse data from
     */
    private void parseDataFromControllersLibrary(ControllersLibrary library) {

        try {
            String jointsSourceStr = ((Input) getDataStructureBySemantic(library.getVertexWeights().getInputs(),
                    SEMANTIC_JOINT)).getSource();

            Source currentSource = ((Source) getDataStructureById(library.getSources(), jointsSourceStr));

            ArrayList<String> jointOrder = new ArrayList<>(Arrays.asList(((NameArray) currentSource.getArray()).getNames()));

            String weightsSourceStr = ((Input) getDataStructureBySemantic(library.getVertexWeights().getInputs(),
                    SEMANTIC_WEIGHT)).getSource();

            currentSource = ((Source) getDataStructureById(library.getSources(), weightsSourceStr));

            float[] weights = ((FloatArray) currentSource.getArray()).getFloats();

            ArrayList<CALiVertexSkinData> skinningData = new ArrayList<>();

            int index = 0;
            int[] v = library.getVertexWeights().getV();

            // Looping over effector joint counts
            for(int count : library.getVertexWeights().getVCount()) {
                CALiVertexSkinData skinData = new CALiVertexSkinData(WEIGHTS_PER_VERTEX_CAP);

                for(int i = 0; i < count; i++) {
                    int jointId = v[index++];
                    int weightId = v[index++];
                    skinData.addWeight(weights[weightId], jointId);
                }
                skinningData.add(skinData.pack());
            }
            this.jointOrder = jointOrder;
            this.skinningData = skinningData;

        } catch (CALiMissingDataException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parsing the "CALiJoint" rootJoint from a specific VisualScenesLibrary.
     *
     * When parsing the local bind matrix, if {@link #correctBlenderCoordinates()} is specified, the y of every texture
     * coordinate is inverted.
     *
     * @param library - the specific VisualScenesLibrary to parse data from
     */
    private void parseDataFromVisualScenesLibrary(VisualScenesLibrary library) throws Exception {

        String rootJointName = "";

        for(Node node : library.getNodes()) {
            if(node.getInstanceController() == null || node.getInstanceController().getSkeletons() == null) {
                continue;
            }

            if(node.getInstanceController().getSkeletons().length == 1) {
                rootJointName = node.getInstanceController().getSkeletons()[0];
            } else {
                throw new Exception("Not all bones have been added to the skin. Multiple root joints detected!");
            }
        }

        for(Node parent : library.getNodes()) {
            for(Node child : parent.getChildren()) {

                if(child.getName().equals(rootJointName)) {

                    CALiJoint rootJoint;

                    String id = child.getId();
                    String name = child.getName();

                    CALiMatrix4f localBindMatrix = new CALiMatrix4f(child.getMatrix()).transpose();

                    if(correctBlenderCoordinates()) {
                        localBindMatrix = BLENDER_CORRECTION.multiply(localBindMatrix); //Blender: z -> up, openGL: y -> up
                    }

                    if(child.getType().equals("JOINT")) {
                        rootJoint = new CALiJoint(id, name, child.getSid(), localBindMatrix, null);
                    } else {
                        rootJoint = new CALiJoint(id, name, localBindMatrix, null);
                    }
                    rootJoint.setChildren(parseJointChildren(rootJoint, child));

                    this.rootJoint = rootJoint;
                    this.jointCount++;
                }

            }
        }
    }

    /**
     * Parses an array of "CALiJoint" children from a specific node based on a specific "CALiJoint" parent and returns it.
     *
     * @param parent - the parent specified for the parsed children
     * @param node - the specific node to parse the children from
     *
     * @return - the array of "CALiJoint" children
     */
    private CALiJoint[] parseJointChildren(CALiJoint parent, Node node) {

        if(node.getChildren() == null || node.getChildren().length == 0) {
            return null;
        }

        CALiJoint[] children = new CALiJoint[node.getChildren().length];

        for(int i = 0; i < node.getChildren().length; i++) {

            Node child = node.getChildren()[i];

            String id = child.getId();
            String name = child.getName();

            CALiMatrix4f localBindMatrix = new CALiMatrix4f(child.getMatrix()).transpose();

            if(child.getType().equals("JOINT")) {
                children[i] = new CALiJoint(id, name, child.getSid(), localBindMatrix, parent);
            } else {
                children[i] = new CALiJoint(id, name, localBindMatrix, parent);
            }

            jointCount += 1;
            children[i].setChildren(parseJointChildren(children[i], node.getChildren()[i]));

        }
        return children;
    }

    /**
     * Parsing vertices, textures, normals and indices from a specific GeometriesLibrary and initializing
     * the corresponding arrays with the retrieved data.
     * The GeometriesLibrary must be based on the the MeshType.POLYLISTS.
     *
     * @param library - the specific GeometriesLibrary to parse data from
     */
    private void parseDataFromGeometriesLibrary(GeometriesLibrary library) {

        if(!library.getMeshType().equals(Mesh.MeshType.POLYLIST)) {
            System.err.println("GeometriesLibrary is of invalid type: " + library.getMeshType());
            return;
        }

        verticesList = new ArrayList<>();
        texturesList = new ArrayList<>();
        normalsList = new ArrayList<>();
        indicesList = new ArrayList<>();

        String verticesSourceId = library.getVerticesInputSource();

        try {
            String texturesSourceStr = ((Input) getDataStructureBySemantic(library.getPolylist().getInputs(),
                    "TEXCOORD")).getSource();
            String normalsSourceStr = ((Input) getDataStructureBySemantic(library.getPolylist().getInputs(),
            "NORMAL")).getSource();

            Source currentSource = ((Source) getDataStructureById(library.getSources(), verticesSourceId));
            parseVerticesList(((FloatArray) currentSource.getArray()).getFloats());

            currentSource = ((Source) getDataStructureById(library.getSources(), texturesSourceStr));
            parseTexturesList(((FloatArray) currentSource.getArray()).getFloats());

            currentSource = ((Source) getDataStructureById(library.getSources(), normalsSourceStr));
            parseNormalsList(((FloatArray) currentSource.getArray()).getFloats());

            parseIndicesList(library.getPolylist().getParagraph(), library.getPolylist().getInputs().length);

            initializeDataArrays();

        } catch (CALiMissingDataException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses a specific array of floats into the ArrayList verticesList.
     *
     * When parsing the textures, if {@link #correctBlenderCoordinates()} is specified, the position vectors are
     * transformed to the correct orientation (from Blender to OpenGL).
     *
     * @param positions - the specific array of floats
     */
    private void parseVerticesList(float[] positions) {
        for(int i = 0; i < positions.length / 3; i++) {

            float x = positions[i * 3];
            float y = positions[i * 3 + 1];
            float z = positions[i * 3 + 2];

            CALiVector4f posVector = new CALiVector4f(x, y, z, 1);

            if(correctBlenderCoordinates()) {
                posVector = posVector.transform(BLENDER_CORRECTION);
            }

            verticesList.add(new CALiVertex(verticesList.size(), new CALiVector3f(posVector.x, posVector.y, posVector.z),
                    skinningData.get(verticesList.size())));
        }
    }

    /**
     * Parses a specific array of floats into the ArrayList texturesList.
     *
     * @param textures - the specific array of floats
     */
    private void parseTexturesList(float[] textures) {
        for(int i = 0; i < textures.length / 2; i++) {

            float s = textures[i * 2];
            float t = textures[i * 2 + 1];

            texturesList.add(new CALiVector2f(s, t));
        }
    }

    /**
     * Parses a specific array of floats into the ArrayList normalsList.
     *
     * @param normals - the specific array of floats
     */
    private void parseNormalsList(float[] normals) {
        for(int i = 0; i < normals.length / 3; i++) {

            float x = normals[i * 3];
            float y = normals[i * 3 + 1];
            float z = normals[i * 3 + 2];

            CALiVector4f normal = new CALiVector4f(x, y, z, 0.0f).transform(BLENDER_CORRECTION);

            normalsList.add(new CALiVector3f(normal.x, normal.y, normal.z));
        }
    }

    /**
     * Parses a specific array of integers into the ArrayList indicesList with the library
     * specific "stride" of inputs.
     * Also trims the ArrayList verticesList after parsing the indices.
     *
     * @param indices - the specific array of integers
     * @param typeCount - the specific "stride" of inputs
     */
    private void parseIndicesList(int[] indices, int typeCount) {
        for(int i = 0; i < indices.length / typeCount; i++) {

            int positionIndex = indices[i * typeCount];
            int normalIndex = indices[i * typeCount + 1];
            int textureIndex = indices[i * typeCount + 2];

            setVertexIndices(positionIndex, textureIndex, normalIndex);
        }
        trimVerticesList();
    }

    /**
     * Sets the textureIndex and normalIndex of a specific "CALiVertex" vertex specified by a specific positionIndex
     * inside the ArrayList verticesList.
     * The vertex is passed to {@link #setVertexIndicesDuplicate(CALiVertex, int, int)} if a
     * textureIndex and normalIndex is already set for the vertex.
     *
     * @param positionIndex - the specific positionIndex to identify the vertex
     * @param textureIndex - the textureIndex
     * @param normalIndex - the normalIndex
     */
    private void setVertexIndices(int positionIndex, int textureIndex, int normalIndex) {
        CALiVertex vertex = verticesList.get(positionIndex);

        if(!vertex.hasIndicesSet()) {
            vertex.setTextureIndex(textureIndex);
            vertex.setNormalIndex(normalIndex);
            indicesList.add(positionIndex);
        } else {
            setVertexIndicesDuplicate(vertex, textureIndex, normalIndex);
        }
    }

    /**
     * Adds the index of a specific vertex to a specific ArrayList of "Integers", if specific texture and
     * normal indices are equivalent to those of the vertex. Else, it retrieves the duplicate vertex  and
     * propagates the method downwards in the hierarchy.
     *
     * If no duplicate vertex exist for the specific vertex, a duplicate is created, initialized with the
     * specific texture and normal indices and added to the ArrayLists verticesList and its index to
     * the ArrayList indicesList.
     *
     * @param vertex - the specific vertex
     * @param newTextureIndex - the specific texture index
     * @param newNormalIndex - the specific normal index
     */
    private void setVertexIndicesDuplicate(CALiVertex vertex, int newTextureIndex, int newNormalIndex) {
        if(vertex.indicesEquals(newTextureIndex, newNormalIndex)) {
            indicesList.add(vertex.getIndex());
        } else {
            CALiVertex duplicate = vertex.getDuplicate();

            if(duplicate == null) {

                duplicate = new CALiVertex(verticesList.size(), vertex.getPosition(), vertex.getWeightsData());
                duplicate.setTextureIndex(newTextureIndex);
                duplicate.setNormalIndex(newNormalIndex);

                vertex.setDuplicate(duplicate);

                verticesList.add(duplicate);
                indicesList.add(duplicate.getIndex());

            } else {
                setVertexIndicesDuplicate(duplicate, newTextureIndex, newNormalIndex);
            }
        }
    }

    /**
     * Sets the texture and normal indices of every "CALiVertex" vertex contained the ArrayList verticesList, if the
     * vertex indices have not been set yet.
     */
    private void trimVerticesList() {
        for(CALiVertex vertex : verticesList) {
            if(!vertex.hasIndicesSet()) {
                vertex.setTextureIndex(0);
                vertex.setNormalIndex(0);
            }
        }
    }

    /**
     * Creates the arrays containing the geometry data and initializes them based on ArrayLists verticesList,
     * texturesList, normalsList and indicesList.
     *
     * When parsing the textures, if {@link #correctBlenderCoordinates()} is specified, the y of every texture coordinate
     * is inverted.
     */
    private void initializeDataArrays() {
        vertices = new float[verticesList.size() * 3];
        textures = new float[verticesList.size() * 2];
        normals = new float[verticesList.size() * 3];
        weights = new float[verticesList.size() * 3];

        jointIds = new int[verticesList.size() * 3];

        for(int i = 0; i < verticesList.size(); i++) {
            CALiVertex vertex = verticesList.get(i);

            CALiVector3f position = vertex.getPosition();
            CALiVector2f texture = texturesList.get(vertex.getTextureIndex());
            CALiVector3f normal = normalsList.get(vertex.getNormalIndex());

            vertices[i * 3] = position.x;
            vertices[i * 3 + 1] = position.y;
            vertices[i * 3 + 2] = position.z;

            textures[i * 2] = texture.x;
            textures[i * 2 + 1] = correctBlenderCoordinates() ? 1.0f - texture.y : texture.y; // Converting blender (u,v) to openGL (s,t).

            normals[i * 3] = normal.x;
            normals[i * 3 + 1] = normal.y;
            normals[i * 3 + 2] = normal.z;

            CALiVertexSkinData skinData = vertex.getWeightsData();

            jointIds[i * 3] = skinData.getJointIds().get(0);
            jointIds[i * 3 + 1] = skinData.getJointIds().get(1);
            jointIds[i * 3 + 2] = skinData.getJointIds().get(2);

            weights[i * 3] = skinData.getWeights().get(0);
            weights[i * 3 + 1] = skinData.getWeights().get(1);
            weights[i * 3 + 2] = skinData.getWeights().get(2);
        }

        indices = indicesList.stream().mapToInt(i -> i).toArray();
    }
}
