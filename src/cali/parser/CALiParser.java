package cali.parser;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import cali.libraries.animations.AnimationsLibrary;
import cali.libraries.animations.animation.Animation;
import cali.libraries.animations.animation.Channel;
import cali.libraries.animations.animation.Sampler;
import cali.libraries.controllers.ControllersLibrary;
import cali.libraries.controllers.controller.Controller;
import cali.libraries.controllers.controller.skin.Joints;
import cali.libraries.controllers.controller.skin.Skin;
import cali.libraries.controllers.controller.skin.VertexWeights;
import cali.libraries.effects.EffectsLibrary;
import cali.libraries.effects.effect.Effect;
import cali.libraries.effects.effect.profileCommon.ProfileCOMMON;
import cali.libraries.effects.effect.profileCommon.technique.Phong;
import cali.libraries.effects.effect.profileCommon.technique.Technique;
import cali.libraries.geometries.GeometriesLibrary;
import cali.libraries.geometries.geometry.Geometry;
import cali.libraries.geometries.geometry.mesh.Mesh;
import cali.libraries.geometries.geometry.mesh.Polylist;
import cali.libraries.geometries.geometry.mesh.Triangles;
import cali.libraries.geometries.geometry.mesh.Vertices;
import cali.libraries.images.ImagesLibrary;
import cali.libraries.images.image.Image;
import cali.libraries.materials.MaterialsLibrary;
import cali.libraries.materials.material.InstanceEffect;
import cali.libraries.materials.material.Material;
import cali.libraries.visualScenes.VisualScenesLibrary;
import cali.libraries.visualScenes.visualScene.VisualScene;
import cali.libraries.visualScenes.visualScene.node.instanceController.InstanceController;
import cali.commons.Input;
import cali.commons.source.BasicArray;
import cali.commons.source.FloatArray;
import cali.commons.source.NameArray;
import cali.commons.source.Source;
import cali.commons.source.techniqueCommon.TechniqueCommon;
import cali.commons.source.techniqueCommon.accessor.Accessor;
import cali.commons.source.techniqueCommon.accessor.Param;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by Aaron on 17.11.17.
 */
public class CALiParser {

    private DocumentBuilder builder;
    private Document document;

    /**
     * The constructor of the CALiParser.
     *
     * @param path - the relative/absolute file path of the ".dae" file to parse.
     */
    public CALiParser(String path){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            System.err.print("CMDParser caused a ParserConfigurationException.");
            e.printStackTrace();
        }
        createDocument(path);
    }

    /**
     * Creates an xml document based on the relative/absolute file path of a file path of a ".dae" file.
     *
     * @param path - the relative/absolute file path of the ".dae" file to parse.
     */
    private void createDocument(String path){
        try {
            //document = builder.parse(path);
            document = builder.parse(ClassLoader.getSystemResourceAsStream(path));
        } catch (SAXException e) {
            System.err.print("CMDParser caused a SAXException.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.print("CMDParser caused a IOException.");
            e.printStackTrace();
        }
    }

    /**
     * Used to retrieve an array list of xml elements based on a node type (i.e., the name) and a parent node.
     *
     * @param type - i.e., the name of the node within the xml document structure
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - an array list containing all nodes of the specified type found within the parent node
     */
    private ArrayList<Element> getChildrenByNameAndParent(String type, Element parent){
        ArrayList<Element> childElements = new ArrayList<>();

        NodeList childNodes = parent.getChildNodes();

        for(int i = 0; i < childNodes.getLength(); i++){
            Node childNode = childNodes.item(i);

            if(childNode.getNodeType() == Node.ELEMENT_NODE && childNode.getNodeName().equals(type)){
                Element childElement = (Element) childNode;
                childElements.add(childElement);
            }
        }
        return childElements;
    }

    //************************************************************************************
    //**							GEOMETRIES_LIBRARY									**
    //************************************************************************************

    /**
     * Creates a "GeometriesLibrary" object and returns it. Based on the Mesh.MeshType.POLYLISTS.
     *
     * @return - the created "GeometriesLibrary" object
     */
    public GeometriesLibrary parseGeometriesLibrary_polylist() {
        GeometriesLibrary geometriesLibrary = null;

        NodeList geometriesLibraryNodes = document.getElementsByTagName("library_geometries");

        for(int i = 0; i < geometriesLibraryNodes.getLength(); i++) {

            Node geometriesLibraryNode = geometriesLibraryNodes.item(i);

            if(geometriesLibraryNode.getNodeType() == Node.ELEMENT_NODE) {
                geometriesLibrary = new GeometriesLibrary(parseGeometriesLibraryGeometry_polylist(
                        (Element) geometriesLibraryNode));
                break;
            }
        }
        return geometriesLibrary;
    }

    /**
     * @deprecated use {@link #parseGeometriesLibrary_polylist()} instead.
     *
     * Creates a "GeometriesLibrary" object and returns it. Based on the Mesh.MeshType.POLYLISTS and Mesh.MeshType.TRIANGLES.
     *
     * @return - the created "GeometriesLibrary" object
     */
    @Deprecated
    public GeometriesLibrary parseGeometriesLibrary() {
        GeometriesLibrary geometriesLibrary = null;

        NodeList geometriesLibraryNodes = document.getElementsByTagName("library_geometries");

        for(int i = 0; i < geometriesLibraryNodes.getLength(); i++) {

            Node geometriesLibraryNode = geometriesLibraryNodes.item(i);

            if(geometriesLibraryNode.getNodeType() == Node.ELEMENT_NODE) {
                geometriesLibrary = new GeometriesLibrary(parseGeometriesLibraryGeometry((Element) geometriesLibraryNode));
                break;
            }
        }
        return geometriesLibrary;
    }

    /**
     * Creates a "Geometry" object based on a specific parent and returns it. Based on the Mesh.MeshType.POLYLISTS.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Geometry" object
     */
    private Geometry parseGeometriesLibraryGeometry_polylist(Element parent) {
        Geometry geometry = null;

        for(Element geometryElement : getChildrenByNameAndParent("geometry", parent)) {
            geometry = new Geometry(
                    geometryElement.getAttribute("id"),
                    geometryElement.getAttribute("name"),
                    parseGeometriesLibraryGeometryMesh_polylist(geometryElement));
        }
        return geometry;
    }

    /**
     * @deprecated use {@link #parseGeometriesLibraryGeometry_polylist(Element parent)} instead.
     *
     * Creates a "Geometry" object based on a specific parent and returns it. Based on the Mesh.MeshType.POLYLISTS and Mesh.MeshType.TRIANGLES.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Geometry" object
     */
    @Deprecated
    private Geometry parseGeometriesLibraryGeometry(Element parent) {
        Geometry geometry = null;

        for(Element geometryElement : getChildrenByNameAndParent("geometry", parent)) {
            geometry = new Geometry(
                    geometryElement.getAttribute("id"),
                    geometryElement.getAttribute("name"),
                    parseGeometriesLibraryGeometryMesh(geometryElement));
        }
        return geometry;
    }

    /**
     * Creates a "Mesh" object based on a specific parent and returns it. Based on the Mesh.MeshType.POLYLISTS.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Mesh" object
     */
    private Mesh parseGeometriesLibraryGeometryMesh_polylist(Element parent) {
        Mesh mesh = null;

        for(Element meshElement : getChildrenByNameAndParent("mesh", parent)) {
            if(getChildrenByNameAndParent("polylist", meshElement).size() > 0) {
                mesh = new Mesh(
                        parseSourcesFromParent(meshElement),
                        parseGeometriesLibraryGeometryMeshVertices(meshElement),
                        parseGeometriesLibraryGeometryMeshPolylist(meshElement));
                break;
            }
        }
        return mesh;
    }

    /**
     * @deprecated use {@link #parseGeometriesLibraryGeometryMesh_polylist(Element)} instead.
     *
     * Creates a "Mesh" object based on a specific parent and returns it. Based on the Mesh.MeshType.POLYLISTS and Mesh.MeshType.TRIANGLES.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Mesh" object
     */
    @Deprecated
    private Mesh parseGeometriesLibraryGeometryMesh(Element parent) {
        Mesh mesh = null;

        for(Element meshElement : getChildrenByNameAndParent("mesh", parent)) {
            if(getChildrenByNameAndParent("polylist", meshElement).size() > 0) {
                mesh = new Mesh(
                        parseSourcesFromParent(meshElement),
                        parseGeometriesLibraryGeometryMeshVertices(meshElement),
                        parseGeometriesLibraryGeometryMeshPolylist(meshElement));
                break;
            } else if(getChildrenByNameAndParent("triangle", meshElement).size() > 0) {
                mesh = new Mesh(
                        parseSourcesFromParent(meshElement),
                        parseGeometriesLibraryGeometryMeshVertices(meshElement),
                        parseGeometriesLibraryGeometryMeshTriangles(meshElement));
                break;
            }
        }
        return mesh;
    }

    /**
     * Creates a "Vertices" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Vertices" object
     */
    private Vertices parseGeometriesLibraryGeometryMeshVertices(Element parent) {
        Vertices vertices = null;

        for(Element verticesElement : getChildrenByNameAndParent("vertices", parent)) {
            String inputSemantic = "";
            String inputSource = "";

            for(Element input : getChildrenByNameAndParent("input", verticesElement)) {
                inputSemantic = input.getAttribute("semantic");
                inputSource = input.getAttribute("source").split("#")[1];
            }

            vertices = new Vertices(
                    verticesElement.getAttribute("id"),
                    inputSemantic,
                    inputSource);
        }
        return vertices;
    }

    /**
     * Creates an array of "Polylist" object based on a specific parent s and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created array of "Polylist" objects
     */
    private Polylist parseGeometriesLibraryGeometryMeshPolylist(Element parent) {
        Polylist polylist = null;

        for(Element polylistElement : getChildrenByNameAndParent("polylist", parent)) {
            polylist = new Polylist(
                    Integer.parseInt(polylistElement.getAttribute("count")),
                    polylistElement.getAttribute("material"),
                    parseInputsFromParent(polylistElement),
                    parseIntArrayFromParent(polylistElement, "vcount"),
                    parseIntArrayFromParent(polylistElement, "p"));
        }

        return polylist;
    }

    /**
     * @deprecated use {@link #parseGeometriesLibraryGeometryMeshPolylist(Element parent)} instead.
     *
     * Created an array of "Triangles" object based on a specific parent s and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created array of "Triangles" objects
     */
    @Deprecated
    private Triangles parseGeometriesLibraryGeometryMeshTriangles(Element parent) {
        Triangles triangles = null;

        for(Element trianglesElement : getChildrenByNameAndParent("triangle", parent)) {
            triangles = new Triangles(
                    Integer.parseInt(trianglesElement.getAttribute("count")),
                    trianglesElement.getAttribute("material"),
                    parseGeometriesLibraryGeometryMeshTrianglesInputs(trianglesElement),
                    parseGeometriesLibraryGeometryMeshTrianglesParagraphs(trianglesElement));
        }
        return triangles;
    }

    /**
     * Creates an array of "Input" object based on a specific parent s and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created array of "Input" objects
     */
    private Input[] parseGeometriesLibraryGeometryMeshTrianglesInputs(Element parent) {
        ArrayList<Input> trianglesInputs = new ArrayList<>();

        for(Element input : getChildrenByNameAndParent("input", parent)) {
            trianglesInputs.add(new Input(
                    input.getAttribute("semantic"),
                    input.getAttribute("source").split("#")[1],
                    Integer.parseInt(input.getAttribute("offset"))));
        }
        return trianglesInputs.toArray(new Input[0]);
    }

    /**
     * Creates an array of integers representing the "Triangles" "Paragraph" objects based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created array of integers
     */
    private int[] parseGeometriesLibraryGeometryMeshTrianglesParagraphs(Element parent) {
        int[] paragraph = null;

        for(Element trianglesParagraphElement : getChildrenByNameAndParent("p", parent)) {
            String[] splitContent = trianglesParagraphElement.getTextContent().split(" ");

            paragraph = new int[splitContent.length];

            for(int i = 0; i < splitContent.length; i++) {
                paragraph[i] = Integer.parseInt(splitContent[i]);
            }
        }
        return paragraph;
    }

    //************************************************************************************
    //** 							IMAGES_LIBRARY 	  									**
    //************************************************************************************

    /**
     * Creates a "ImagesLibrary" object and returns it.
     *
     * @return - the created "ImagesLibrary" object
     */
    public ImagesLibrary parseImagesLibrary() {
        ImagesLibrary imagesLibrary = null;

        NodeList imagesLibraryNodes = document.getElementsByTagName("library_images");

        for (int i = 0; i < imagesLibraryNodes.getLength(); i++) {
            Node imagesLibraryNode = imagesLibraryNodes.item(i);

            if (imagesLibraryNode.getNodeType() == Node.ELEMENT_NODE) {
                imagesLibrary = new ImagesLibrary(parseImagesLibraryImage((Element) imagesLibraryNode));
            }
        }
        return imagesLibrary;
    }

    /**
     * Creates a "Image" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Image" object
     */
    private Image parseImagesLibraryImage(Element parent) {
        Image image = null;

        for (Element imageElement : getChildrenByNameAndParent("image", parent)) {
            image = new Image(
                    imageElement.getAttribute("id"),
                    imageElement.getAttribute("name"),
                    parseImagesLibraryImagesInitFrom(imageElement));
        }
        return image;
    }

    /**
     * Retrieves an "InitFrom" string and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return -  the retrieved "InitFrom" string
     */
    private String parseImagesLibraryImagesInitFrom(Element parent) {
        String initFrom = "";

        for (Element initFromElement : getChildrenByNameAndParent("init_from", parent)) {
            String[] splitContent = initFromElement.getTextContent().split("/");
            initFrom = splitContent[splitContent.length - 1];
        }
        return initFrom;
    }

    //************************************************************************************
    //**							MATERIALS_LIBRARY									**
    //************************************************************************************

    /**
     * Creates a "MaterialsLibrary" object and returns it.
     *
     * @return - the created "MaterialsLibrary" object
     */
    @SuppressWarnings("unused")
    public MaterialsLibrary parseMaterialsLibrary() {
        MaterialsLibrary materialsLibrary = null;

        NodeList materialsLibraryNodes = document.getElementsByTagName("library_materials");

        for (int i = 0; i < materialsLibraryNodes.getLength(); i++) {
            Node materialsLibraryNode = materialsLibraryNodes.item(i);

            if (materialsLibraryNode.getNodeType() == Node.ELEMENT_NODE) {
                materialsLibrary = new MaterialsLibrary(parseMaterialsLibraryMaterials((Element) materialsLibraryNode));
            }
        }
        return materialsLibrary;
    }

    /**
     *Creates an array of "Material" objects based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created array of "Material" objects
     */
    private Material[] parseMaterialsLibraryMaterials(Element parent) {
        ArrayList<Material> materials = new ArrayList<>();

        for (Element material : getChildrenByNameAndParent("material", parent)) {
            Material m = new Material(
                    material.getAttribute("id"),
                    material.getAttribute("name"),
                    parseMaterialsLibrary_Materials_InstanceEffect(material));
            materials.add(m);
        }
        return materials.toArray(new Material[0]);
    }

    /**
     * Creates an "InstanceEffect" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "InstanceEffect" object
     */
    private InstanceEffect parseMaterialsLibrary_Materials_InstanceEffect(Element parent) {
        InstanceEffect instanceEffect = null;

        for (Element instanceEffectElement : getChildrenByNameAndParent("instance_effect", parent)) {
            instanceEffect = new InstanceEffect(instanceEffectElement.getAttribute("url").split("#")[1]);
        }
        return instanceEffect;
    }

    //************************************************************************************
    //**								EFFECTS_LIBRARY									**
    //************************************************************************************

    /**
     * Creates an "EffectsLibrary" object and returns it.
     *
     * @return - the created "EffectLibrary" object
     */
    @SuppressWarnings("unused")
    public EffectsLibrary parseEffectsLibrary() {
        EffectsLibrary effectsLibrary = null;

        NodeList effectsLibraryNodes = document.getElementsByTagName("library_effects");

        for(int i = 0; i < effectsLibraryNodes.getLength(); i++) {
            Node effectsLibraryNode = effectsLibraryNodes.item(i);

            if(effectsLibraryNode.getNodeType() == Node.ELEMENT_NODE) {
                effectsLibrary = new EffectsLibrary(parseEffectsLibraryEffects((Element) effectsLibraryNode));
            }
        }
        return effectsLibrary;
    }

    /**
     * Creates an array of "Effect" objects based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the array of "Effect" objects
     */
    private Effect[] parseEffectsLibraryEffects(Element parent) {
        ArrayList<Effect> effects = new ArrayList<>();

        for(Element effect : getChildrenByNameAndParent("effect", parent)) {
            effects.add(new Effect(
                    effect.getAttribute("id"),
                    effect.getAttribute("name"),
                    parseEffectsLibraryEffectsProfileCOMMON(effect)));
        }
        return effects.toArray(new Effect[0]);
    }

    /**
     * Creates a "ProfileCOMMON" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "ProfileCOMMON" object
     */
    private ProfileCOMMON parseEffectsLibraryEffectsProfileCOMMON(Element parent) {
        ProfileCOMMON profileCOMMON = null;

        for(Element profileCOMMONElement : getChildrenByNameAndParent("profile_COMMON", parent)) {
            profileCOMMON = new ProfileCOMMON(parseEffectsLibraryEffectsProfileCOMMONTechnique(profileCOMMONElement));
        }
        return profileCOMMON;
    }

    /**
     * Creates a "Technique" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Technique" object
     */
    private Technique parseEffectsLibraryEffectsProfileCOMMONTechnique(Element parent) {
        Technique technique = null;

        for(Element techniqueElement : getChildrenByNameAndParent("technique", parent)) {
            technique = new Technique(
                    techniqueElement.getAttribute("sid"),
                    parseEffectsLibraryEffectsProfileCOMMONTechniquePhong(techniqueElement));
        }
        return technique;
    }

    /**
     * Creates a "Phong" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Phong" object
     */
    private Phong parseEffectsLibraryEffectsProfileCOMMONTechniquePhong(Element parent) {
        Phong phong = null;

        for(Element phongElement : getChildrenByNameAndParent("phong", parent)) {
            phong = new Phong(
                    parseEffectsLibraryEffectsProfileCOMMONTechniquePhongColor("emission", phongElement),
                    parseEffectsLibraryEffectsProfileCOMMONTechniquePhongColor("ambient", phongElement),
                    parseEffectsLibraryEffectsProfileCOMMONTechniquePhongColor("specular", phongElement),
                    parseEffectsLibraryEffectsProfileCOMMONTechniquePhongFloat("shininess", phongElement),
                    parseEffectsLibraryEffectsProfileCOMMONTechniquePhongFloat("transparency", phongElement));
        }
        return phong;
    }

    /**
     * Creates an array of floats representing the "Phong" "Color" objects based on a specific parent and name
     * and returns it.
     *
     * @param name - the name (i.e., type) of the desired "Color"
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created array of floats
     */
    private float[] parseEffectsLibraryEffectsProfileCOMMONTechniquePhongColor(String name, Element parent) {
        float[] color = null;

        for(Element par : getChildrenByNameAndParent(name, parent)) {
            for(Element colorElement : getChildrenByNameAndParent("color", par)) {
                String[] rgba = colorElement.getTextContent().split(" ");

                float r, g, b, a;
                r = Float.parseFloat(rgba[0]);
                g = Float.parseFloat(rgba[1]);
                b = Float.parseFloat(rgba[2]);
                a = Float.parseFloat(rgba[3]);

                color = new float[]{r, g, b, a};
            }
        }
        return color;
    }

    /**
     * Retrieves a float value for a "Phong" and returns it.
     *
     * @param name - the name (i.e., the type) of the desired value
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the retrieved float value
     */
    private float parseEffectsLibraryEffectsProfileCOMMONTechniquePhongFloat(String name, Element parent) {
        float value = 0f;

        for(Element valueElement : getChildrenByNameAndParent(name, parent)) {
            for(Element floatElement : getChildrenByNameAndParent("float", valueElement)) {
                value = Float.parseFloat(floatElement.getTextContent());
            }
        }
        return value;
    }

    //************************************************************************************
    //**						VISUAL_SCENES_LIBRARY									**
    //************************************************************************************

    /**
     * Creates a "VisualScenesLibrary" object and returns it.
     *
     * @return - the created "VisualScenesLibrary" object
     */
    public VisualScenesLibrary parseVisualScenesLibrary() {
        VisualScenesLibrary visualScenesLibrary = null;

        NodeList visualScenesLibraryNodes = document.getElementsByTagName("library_visual_scenes");

        for(int i = 0; i < visualScenesLibraryNodes.getLength(); i++) {
            Node visualScenesLibraryNode = visualScenesLibraryNodes.item(i);

            if(visualScenesLibraryNode.getNodeType() == Node.ELEMENT_NODE) {
                visualScenesLibrary = new VisualScenesLibrary(parseVisualScenesLibrary_VisualScene(
                        (Element) visualScenesLibraryNode));
            }
        }
        return visualScenesLibrary;
    }

    /**
     * Creates a "VisualScene" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "VisualScene" object
     */
    private VisualScene parseVisualScenesLibrary_VisualScene(Element parent){
        VisualScene visualScene = null;

        for(Element visualSceneElement : getChildrenByNameAndParent("visual_scene", parent)) {
            visualScene = new VisualScene(parseVisualScenesLibrary_VisualScenes_Nodes(visualSceneElement));
        }
        return visualScene;
    }

    /**
     * Creates an array of "Node" objects based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created array of "Node" objects
     */
    private cali.libraries.visualScenes.visualScene.node.Node[] parseVisualScenesLibrary_VisualScenes_Nodes(Element parent) {
        ArrayList<cali.libraries.visualScenes.visualScene.node.Node> nodes = new ArrayList<>();

        for(Element node : getChildrenByNameAndParent("node", parent)) {
            InstanceController instanceController = parseVisualScenesLibrary_VisualScenes_Nodes_InstanceController(node);

            cali.libraries.visualScenes.visualScene.node.Node[] nn = parseVisualScenesLibrary_VisualScenes_Nodes(node);

            if(instanceController == null){
                cali.libraries.visualScenes.visualScene.node.Node n = new cali.libraries.visualScenes.visualScene.node.Node(
                        node.getAttribute("id"),
                        node.getAttribute("name"),
                        node.getAttribute("type"),
                        node.getAttribute("sid"),
                        parseFloatArrayFromParent(node, "matrix"),
                        null,
                        nn);
                for(cali.libraries.visualScenes.visualScene.node.Node nnn : nn){
                    nnn.setParent(n);
                }
                nodes.add(n);
            } else {
                cali.libraries.visualScenes.visualScene.node.Node n = new cali.libraries.visualScenes.visualScene.node.Node(
                        node.getAttribute("id"),
                        node.getAttribute("name"),
                        node.getAttribute("type"),
                        node.getAttribute("sid"),
                        parseFloatArrayFromParent(node, "matrix"),
                        null,
                        parseVisualScenesLibrary_VisualScenes_Nodes(node),
                        instanceController);
                for(cali.libraries.visualScenes.visualScene.node.Node nnn : nn){
                    nnn.setParent(n);
                }
                nodes.add(n);
            }
        }
        return nodes.toArray(new cali.libraries.visualScenes.visualScene.node.Node[0]);
    }

    /**
     * Creates an "InstanceController" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "InstanceController" object
     */
    private InstanceController parseVisualScenesLibrary_VisualScenes_Nodes_InstanceController(Element parent) {
        InstanceController instanceController = null;

        for(Element instanceControllerElement : getChildrenByNameAndParent("instance_controller", parent)) {
            instanceController = new InstanceController(
                    instanceControllerElement.getAttribute("url").split("#")[1],
                    parseVisualScenesLibrary_VisualScenes_Nodes_InstanceController_Skeletons(instanceControllerElement));
        }
        return instanceController;
    }

    /**
     * Retrieves an array of strings representing the "InstanceController" "Skeleton" and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the retrieved array of strings
     */
    private String[] parseVisualScenesLibrary_VisualScenes_Nodes_InstanceController_Skeletons(Element parent) {
        ArrayList<String> skeletons = new ArrayList<>();

        for(Element skeleton : getChildrenByNameAndParent("skeleton", parent)) {
            skeletons.add(skeleton.getTextContent().split("#")[1]);
        }
        return skeletons.toArray(new String[0]);
    }

    //************************************************************************************
    //**							CONTROLLERS_LIBRARY									**
    //************************************************************************************

    /**
     * Creates a "ControllersLibrary" object and returns it.
     *
     * @return - the created "ControllersLibrary" object
     */
    public ControllersLibrary parseControllersLibrary() {
        ControllersLibrary controllersLibrary = null;

        NodeList controllersLibraryNodes = document.getElementsByTagName("library_controllers");

        for(int i = 0; i < controllersLibraryNodes.getLength(); i++) {
            Node controllersLibraryNode = controllersLibraryNodes.item(i);

            if(controllersLibraryNode.getNodeType() == Node.ELEMENT_NODE) {
                controllersLibrary = new ControllersLibrary(parseControllersLibraryController(
                        (Element) controllersLibraryNode));
            }
        }
        return controllersLibrary;
    }

    /**
     * Creates a "Controller" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Controller" object
     */
    private Controller parseControllersLibraryController(Element parent) {
        Controller controllers = null;

        for(Element controllerElement : getChildrenByNameAndParent("controller", parent)) {
            controllers = new Controller(parseControllersLibraryControllerSkin(controllerElement));
        }
        return controllers;
    }

    /**
     * Creates a "Skin" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Skin" object
     */
    private Skin parseControllersLibraryControllerSkin(Element parent) {
        Skin skin = null;

        for(Element skinElement : getChildrenByNameAndParent("skin", parent))
            skin = new Skin(
                    parseFloatArrayFromParent(skinElement, "bind_shape_matrix"),
                    parseSourcesFromParent(skinElement),
                    parseControllersLibraryControllerSkinJoints(skinElement),
                    parseControllersLibraryControllerSkinVertexWeights(skinElement));
        return skin;
    }

    /**
     * Creates a "Joints" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Joints" object
     */
    private Joints parseControllersLibraryControllerSkinJoints(Element parent) {
        Joints joints = null;

        for(Element jointsElement : getChildrenByNameAndParent("joints", parent)) {
            joints = new Joints(
                    parseInputsFromParent(jointsElement));
        }
        return joints;
    }

    /**
     * Creates a "VertexWeights" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "VertexWeights" object
     */
    private VertexWeights parseControllersLibraryControllerSkinVertexWeights(Element parent) {
        VertexWeights vertexWeights = null;

        for(Element vertexWeightsElement : getChildrenByNameAndParent("vertex_weights", parent)) {
            vertexWeights = new VertexWeights(
                    parseInputsFromParent(vertexWeightsElement),
                    parseIntArrayFromParent(vertexWeightsElement, "vcount"),
                    parseIntArrayFromParent(vertexWeightsElement, "v"));
        }
        return vertexWeights;
    }

    //************************************************************************************
    //**							ANIMATIONS_LIBRARY									**
    //************************************************************************************

    /**
     * Creates an "AnimationsLibrary" object and returns it.
     *
     * @return - the created "AnimationsLibrary" object
     */
    public AnimationsLibrary parseAnimationsLibrary() {
        AnimationsLibrary animationsLibrary = null;

        NodeList animationsLibraryNodes = document.getElementsByTagName("library_animations");

        for(int i = 0; i < animationsLibraryNodes.getLength(); i++) {
            Node animationsLibraryNode = animationsLibraryNodes.item(i);

            if(animationsLibraryNode.getNodeType() == Node.ELEMENT_NODE) {
                animationsLibrary = new AnimationsLibrary(parseAnimationsLibraryAnimations((Element) animationsLibraryNode));
            }
        }
        return animationsLibrary;
    }

    /**
     * Creates an array of "Animation" objects based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created array of "Animation" objects
     */
    private Animation[] parseAnimationsLibraryAnimations(Element parent) {
        ArrayList<Animation> animations = new ArrayList<>();

        for(Element animation : getChildrenByNameAndParent("animation", parent)) {
            animations.add(new Animation(
                    animation.getAttribute("id"),
                    parseSourcesFromParent(animation),
                    parseAnimationsLibraryAnimationsSampler(animation),
                    parseAnimationsLibraryAnimationsChannel(animation)));
        }
        return animations.toArray(new Animation[0]);
    }

    /**
     * Creates an array of "Sampler" objects based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created array of "Sampler" objects
     */
    private Sampler parseAnimationsLibraryAnimationsSampler(Element parent) {
        Sampler sampler = null;

        for(Element samplerElement : getChildrenByNameAndParent("sampler", parent)) {
            sampler = new Sampler(samplerElement.getAttribute("id"),
                    parseInputsFromParent(samplerElement));
        }
        return sampler;
    }

    /**
     * Creates a "Channel" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Channel" object
     */
    private Channel parseAnimationsLibraryAnimationsChannel(Element parent) {
        Channel channel = null;

        for(Element c : getChildrenByNameAndParent("channel", parent)) {
            channel = new Channel(
                    c.getAttribute("source").split("#")[1],
                    c.getAttribute("target"));
        }
        return channel;
    }

    //************************************************************************************
    //**								COMMONS 										**
    //************************************************************************************

    /**
     * Creates an array of "Input" objects based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created array of "Input" objects
     */
    private Input[] parseInputsFromParent(Element parent) {
        ArrayList<Input> inputs = new ArrayList<>();

        for(Element element : getChildrenByNameAndParent("input", parent)) {

            String semantic;
            semantic = element.getAttribute("semantic");
            String source;
            source = element.getAttribute("source").split("#")[1];

            String offset = element.getAttribute("offset");
            String set = element.getAttribute("set");

            if(!offset.equals("") && !set.equals("")) {
                inputs.add(new Input(semantic, source, Integer.parseInt(offset), Integer.parseInt(set)));
            } else if(!offset.equals("")) {
                inputs.add(new Input(semantic, source, Integer.parseInt(offset)));
            } else {
                inputs.add(new Input(semantic, source));
            }
        }
        return inputs.toArray(new Input[0]);
    }

    /**
     * Creates an array of "Source" objects based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created array of "Source" objects
     */
    private Source[] parseSourcesFromParent(Element parent) {
        ArrayList<Source> sources = new ArrayList<>();

        for(Element source : getChildrenByNameAndParent("source", parent)) {
            sources.add(new Source(
                    source.getAttribute("id"),
                    parseBasicArrayFromParent(source),
                    parseTechniqueCommonFromParent(source)));
        }
        return sources.toArray(new Source[0]);
    }

    /**
     * Creates a "BasicArray" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "BasicArray" object
     */
    private BasicArray parseBasicArrayFromParent(Element parent) {
        BasicArray basicArray = null;

        for(Element floatArray : getChildrenByNameAndParent("float_array", parent)) {
            String[] splitContent = floatArray.getTextContent().split(" ");

            float[] floats = new float[splitContent.length];

            for(int j = 0; j < splitContent.length; j++) {
                floats[j] = Float.parseFloat(splitContent[j]);
            }
            basicArray = new FloatArray(
                    floatArray.getAttribute("id"),
                    Integer.parseInt(floatArray.getAttribute("count")),
                    floats);
        }

        for(Element nameArray : getChildrenByNameAndParent("Name_array", parent)) {
            String[] names = nameArray.getTextContent().split(" ");
            basicArray = new NameArray(
                    nameArray.getAttribute("id"),
                    Integer.parseInt(nameArray.getAttribute("count")),
                    names);
        }
        return basicArray;
    }

    /**
     * Creates a "TechniqueCommon" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "TechniqueCommon" object
     */
    private TechniqueCommon parseTechniqueCommonFromParent(Element parent) {
        TechniqueCommon techniqueCommon = null;

        for(Element techniqueCommonElement : getChildrenByNameAndParent("technique_common", parent)) {
            techniqueCommon = new TechniqueCommon(parseAccessorFromParent(techniqueCommonElement));
        }
        return techniqueCommon;
    }

    /**
     * Creates an "Accessor" object based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created "Accessor" object
     */
    private Accessor parseAccessorFromParent(Element parent) {
        Accessor accessor = null;

        for(Element accessorElement : getChildrenByNameAndParent("accessor", parent)) {
            accessor = new Accessor(
                    accessorElement.getAttribute("source"),
                    Integer.parseInt(accessorElement.getAttribute("count")),
                    Integer.parseInt(accessorElement.getAttribute("stride")),
                    parseParamsFromParent(accessorElement));
        }
        return accessor;
    }

    /**
     * Creates an array of "Param" objects based on a specific parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     *
     * @return - the created array of "Param" objects
     */
    private Param[] parseParamsFromParent(Element parent) {
        ArrayList<Param> params = new ArrayList<>();

        for(Element param : getChildrenByNameAndParent("param", parent)) {
            params.add(new Param(
                    param.getAttribute("name"),
                    param.getAttribute("type")));
        }
        return params.toArray(new Param[0]);
    }

    /**
     * Retrieves an array of integers based on a specific tag and parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     * @param tag - the tag (i.e., the name) of the desired node
     *
     * @return - the retrieved array of integers
     */
    private int[] parseIntArrayFromParent(Element parent, String tag) {
        int[] array = null;

        for(Element element : getChildrenByNameAndParent(tag, parent)) {
            String[] splitContent = element.getTextContent().split(" ");

            array = new int[splitContent.length];

            for(int i = 0; i < splitContent.length; i++) {
                array[i] = Integer.parseInt(splitContent[i]);
            }
        }
        return array;
    }

    /**
     * Retrieves an array of float based on a specific tag and parent and returns it.
     *
     * @param parent - the parent node (i.e., the node above) within the xml document structure of the desired component
     * @param tag - the (i.e., the name) of the desired node
     *
     * @return - the retrieved array of floats
     */
    private float[] parseFloatArrayFromParent(Element parent, String tag) {
        float[] array = null;

        for(Element matrix : getChildrenByNameAndParent(tag, parent)) {
            String[] splitContent = matrix.getTextContent().split(" ");

            if(splitContent.length > 0){
                array = new float[splitContent.length];

                for(int i = 0; i < splitContent.length; i++) {
                    array[i] = Float.parseFloat(splitContent[i]);
                }
            }
        }
        return array;
    }
}
