	package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolBox.Maths;

public class TerrainShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.txt";
	
	private int locationTransformationMatrix;
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	private int locationLightPos;
	private int locationLightColor;
	private int locationShineDamper;
	private int locationReflectivity;
	private int locationSkyColor;
	private int locationBackgroundTexture;
	private int locationRTexture;
	private int locationGTexture;
	private int locationBTexture;
	private int locationBlendMap;
	
	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
		locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
		locationViewMatrix = super.getUniformLocation("viewMatrix");
		locationLightPos = super.getUniformLocation("lightPos");
		locationLightColor = super.getUniformLocation("lightColor");
		locationShineDamper = super.getUniformLocation("shineDamper");
		locationReflectivity = super.getUniformLocation("reflectivity");
		locationSkyColor = super.getUniformLocation("skyColor");
		locationBackgroundTexture = super.getUniformLocation("backgorundTexture");
		locationRTexture = super.getUniformLocation("rTexture");
		locationGTexture = super.getUniformLocation("gTexture");
		locationBTexture = super.getUniformLocation("bTexture");
		locationBlendMap = super.getUniformLocation("blendMap");
	}
	
	public void connectTextureUnits() {
		super.loadInt(locationBackgroundTexture, 0);
		super.loadInt(locationRTexture, 1);
		super.loadInt(locationGTexture, 2);
		super.loadInt(locationBTexture, 3);
		super.loadInt(locationBlendMap, 4);
	}
	
	public void loadShineVariables(float shineDamper, float reflectivity) {
		super.loadFloat(locationShineDamper, shineDamper);
		super.loadFloat(locationReflectivity, reflectivity);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(locationTransformationMatrix, matrix);
	}
	
	public void loadLight(Light light) {
		super.loadVector(locationLightPos, light.getPosition());
		super.loadVector(locationLightColor, light.getColor());
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(locationProjectionMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		super.loadMatrix(locationViewMatrix, Maths.createViewMatrix(camera));
	}
	
	public void loadSkyColor(float r, float g, float b) {
		super.loadVector(locationSkyColor, new Vector3f(r, g, b));
	}

}
