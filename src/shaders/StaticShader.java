package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolBox.Maths;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int locationTransformationMatrix;
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	private int locationLightPos;
	private int locationLightColor;
	private int locationShineDamper;
	private int locationReflectivity;
	private int locationUseFakeLighting;
	private int loactionSkyColor;
	
	public StaticShader() {
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
		locationUseFakeLighting = super.getUniformLocation("useFakeLighting");
		loactionSkyColor = super.getUniformLocation("skyColor");
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
	
	public void loadUseFakeLighting(boolean useFakeLighting) {
		super.loadBoolean(locationUseFakeLighting, useFakeLighting);
	}
	
	public void loadSkyColor(float r, float g, float b) {
		super.loadVector(loactionSkyColor, new Vector3f(r, g, b));
	}

}
